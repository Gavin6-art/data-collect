package com.iwhale.congestion.index.job;

import com.iwhale.congestion.index.service.AlarmJcjAossAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by sll on 2019/4/23.
 */
@Configuration
@EnableScheduling
@ConditionalOnProperty(prefix = "job", name = "AlarmJcjJob", havingValue = "1", matchIfMissing = false)
public class AlarmJcjJob {

    @Autowired
    private AlarmJcjAossAdapter alarmJcjAossAdapter;

    /**
     * Timing task
     */
    @Scheduled(cron = "0 */3 * * * ?")
    public void fetchAossJamEvent(){
        alarmJcjAossAdapter.dbTaskHandlerAlarm();
    }
}
