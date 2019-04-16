package com.oracle.adw.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class SchedulerConfig {
    private final int POOL_SIZE = 1;

    private ThreadPoolTaskScheduler scheduler;
    
    Logger logger = LoggerFactory.getLogger(RunScheduler.class);
    
    public boolean stopScheduler() {
        boolean result = true;
        try {
            scheduler.shutdown();
        } catch (Exception e) {
            logger.info("Scheduler is not running..");
            result = false;
        }
        
        return result;
        //scheduler.destroy();
        //scheduler.getScheduledThreadPoolExecutor().getActiveCount();
    }
    
    public void startScheduler(String cronExpression, Runnable startRealEstateTradeJob) {
        scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("schduler-task-pool-");
        scheduler.setPoolSize(POOL_SIZE);
        scheduler.initialize();

        // 스케쥴러가 시작되는 부분 
        scheduler.schedule(startRealEstateTradeJob, getTrigger(cronExpression));
    }
 
    private Trigger getTrigger(String cronExpression) {
        // 작업 주기 설정 
        return new CronTrigger(cronExpression);
    }

    public long getActiveCount() {
        return scheduler.getActiveCount();
    }
}