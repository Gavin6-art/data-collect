package com.iwhale.congestion.index.job;

import com.iwhale.congestion.index.service.AcdDutySimpleAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@ConditionalOnProperty(prefix = "job", name = "AcdDutySimpleJob", havingValue = "1", matchIfMissing = false)
public class AcdDutySimpleJob {

    @Autowired
    private AcdDutySimpleAdapter acdDutySimpleAdapter;

    /**
     * Timing task
     */
    @Scheduled(cron = "0 */3 * * * ?")
    public void fetchAossJamEvent(){
        acdDutySimpleAdapter.dbTaskHandler();
    }
}
