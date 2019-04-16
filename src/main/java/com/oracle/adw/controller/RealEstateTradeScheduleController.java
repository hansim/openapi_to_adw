package com.oracle.adw.controller;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

import com.oracle.adw.scheduler.SchedulerConfig;
import com.oracle.adw.service.jpa.RealEstateTradeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class RealEstateTradeScheduleController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    JobExplorer jobExplorer;

    @Autowired
    JobOperator jobOperator;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    @Qualifier("realEstateTradeJob")
    Job realEstateTradeJob;

    @Autowired
    SchedulerConfig schedulerConfig;

    @Autowired
    RealEstateTradeService realEstateTradeService;

    Logger logger = LoggerFactory.getLogger(RealEstateTradeScheduleController.class);

    @RequestMapping(value = "/adw/1.0/aptTrade/{operation}", method = RequestMethod.GET)
    // @Scheduled(cron = "${batch.cron}")
    public String performRealEstateTradeJob(@PathVariable("operation") String operation,
            @RequestParam(value = "month", required = false, defaultValue = "") String month,
            @RequestParam(value = "everysec", required = false, defaultValue = "0") String everysec,
            @RequestParam(value = "minute", required = false, defaultValue = "0") String minute,
            @RequestParam(value = "hour", required = false, defaultValue = "0") String hour,
            @RequestParam(value = "day", required = false, defaultValue = "0") String day) throws Exception {
        String result = "";
        String cronExpression;

        if (!everysec.equals("0")) {
            cronExpression = "*/" + everysec + " * * * * *";
        } else {
            cronExpression = "* " + minute + " " + hour + " " + day + " * *";
        }

        logger.info("cronExpression : " + cronExpression);
        logger.debug("operation is " + operation);

        if (month.equals("")) {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyyMM");
            Date time = new Date();
            month = format1.format(time);
        }

        switch (operation) {
            case "start": {
                // 실행중인게 있으면 멈춘 후 다시 시작한다. 스프링 스케쥴러의 경우는 돌고 있는 스케쥴러의 정보를 알기 어렵다 (Quartz는 가능할텐데..)
                schedulerConfig.stopScheduler();
                schedulerConfig.startScheduler(cronExpression, startRealEstateTradeJob(month));

                result = "[정상] 국토부 전국 부동산 실거래가 데이터 수집 배치작업 스케쥴러가 실행되었습니다.";
                break;
            }
            case "stop": {
                schedulerConfig.stopScheduler();
                result = "[정상] 국토부 전국 부동산 실거래가 데이터 수집 배치작업 스케쥴러가 종료되었습니다.";
                break;
            }
            case "startjob": {
                Long jobId;
                //BatchStatus jobStatus;
                Set<JobExecution> jobExecutionsSet = jobExplorer.findRunningJobExecutions("국토부 전국 부동산 실거래가 데이터 수집");

                if (jobExecutionsSet.size() > 0) {
                    for (JobExecution jobExecution : jobExecutionsSet) {
                        jobId = jobExecution.getId();
                        //jobStatus = jobExecution.getStatus();

                        result = "[오류][jobId:" + Long.toString(jobId)
                                + "] 국토부 전국 부동산 실거래가 데이터 수집 배치작업이 진행중입니다. \n 종료 후 다시 시작하십시요.";
                    }
                } else {
                    startBatchJobs(month);
                    result = "[정상] 국토부 전국 부동산 실거래가 데이터 수집 배치작업이 시작되었습니다.";
                }
                break;
            }
            case "stopjob": {
                stopBatchJobs();
                result = "[정상] 국토부 전국 부동산 실거래가 데이터 수집 배치작업이 종료되었습니다.";
                break;
            }
            default: {
                result = "Path 파라미터를 확인해주세요. (start/stop/startjob/stopjob)";
            }
        }
        return result;
    }

    @Bean
    TaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @Bean
    public SimpleJobLauncher simpleJobLauncher() {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(simpleAsyncTaskExecutor());
        return jobLauncher;
    }

    public SimpleAsyncTaskExecutor simpleAsyncTaskExecutor() {
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
        simpleAsyncTaskExecutor.setConcurrencyLimit(10);
        return simpleAsyncTaskExecutor;
    }

    public Runnable startRealEstateTradeJob(String month) throws Exception {
        logger.debug("start startRealEstateTradeJob...");

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis())).addString("month", month)
                .toJobParameters();

        simpleJobLauncher().run(realEstateTradeJob, jobParameters);

        return () -> logger.debug("scheduler " + Instant.now().toEpochMilli());
    };

    public void stopBatchJobs() throws Exception {

        Set<JobExecution> jobExecutionsSet = jobExplorer.findRunningJobExecutions("국토부 전국 부동산 실거래가 데이터 수집");
        for (JobExecution jobExecution : jobExecutionsSet) {
            logger.debug(jobExecution.getStatus() + "ID :" + jobExecution.getId());
            if (jobExecution.getStatus() == BatchStatus.STARTED || jobExecution.getStatus() == BatchStatus.STARTING) {
                jobOperator.stop(jobExecution.getId());

                logger.debug("###########Stopped#########");
                logger.debug(jobExecution.getStatus() + "ID :" + jobExecution.getId());
                logger.debug("###########Stopped#########");
            }
        }
        return;
    }

    public void startBatchJobs(String month) throws Exception {

        logger.debug("start startRealEstateTradeJob...");
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis())).addString("month", month)
                .toJobParameters();

        simpleJobLauncher().run(realEstateTradeJob, jobParameters);
    }

    public static void main(String[] args) {
        SpringApplication.run(SampleController.class, args);
    }

}