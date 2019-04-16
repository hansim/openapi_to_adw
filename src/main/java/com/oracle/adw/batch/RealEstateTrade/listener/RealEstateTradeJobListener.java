package com.oracle.adw.batch.RealEstateTrade.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class RealEstateTradeJobListener implements JobExecutionListener {
    Logger logger = LoggerFactory.getLogger(RealEstateTradeJobListener.class);

    public void beforeJob(JobExecution jobExecution) {
        String param1 = jobExecution.getJobParameters().getString("month");
        logger.info(param1 + " - 국토부 전국 부동산 실거래가 데이터 수집 Job 시작");
    }
 
    public void afterJob(JobExecution jobExecution) {

        
        String exitCode = jobExecution.getExitStatus().getExitCode();
        
        if (exitCode.equals(ExitStatus.COMPLETED.getExitCode())) {
            logger.info("JobID [" + jobExecution.getJobId() + "] 국토부 전국 부동산 실거래가 데이터 수집 Job 처리 완료.");
        } else if (exitCode.equals(ExitStatus.FAILED.getExitCode())) {
            logger.info("JobID [" + jobExecution.getJobId() + "] 국토부 전국 부동산 실거래가 데이터 수집 Job 처리 오류 발생.");
        } else if (exitCode.equals(ExitStatus.STOPPED.getExitCode())) {
            logger.info("JobID [" + jobExecution.getJobId() + "] 국토부 전국 부동산 실거래가 데이터 수집 Job 정지.");
        } else {
            logger.info("JobID [" + jobExecution.getJobId() + "] 국토부 전국 부동산 실거래가 데이터 수집 Job 처리가 정상적으로 완료되지 않음.");
        }
    }
}