package com.iwhale.congestion.index.job;

import com.iwhale.congestion.index.service.AcciInfoAossAdapter;
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
@ConditionalOnProperty(prefix = "job", name = "AcciInfoJob", havingValue = "1", matchIfMissing = false)
public class AcciInfoJob {

    @Autowired
    private AcciInfoAossAdapter acciInfoAossAdapter;

    /**
     * Timing task
     */
    @Scheduled(cron = "0 */3 * * * ?")
    public void fetchAossJamEvent(){
        acciInfoAossAdapter.dbTaskHandlerAlarm();
    }
}
