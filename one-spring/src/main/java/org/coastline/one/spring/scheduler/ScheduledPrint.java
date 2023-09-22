package org.coastline.one.spring.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 2023/8/31
 */
@Component
public class ScheduledPrint {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledPrint.class);

    @Async("firstAsyncExecutor")
    @Scheduled(fixedRate = 5, initialDelay = 0, timeUnit = TimeUnit.SECONDS)
    public void asyncRrint() {
        LOGGER.error("====== async {} ======", Thread.currentThread().getName());
    }

    @Async("secondAsyncExecutor")
    @Scheduled(fixedRate = 5, initialDelay = 0, timeUnit = TimeUnit.SECONDS)
    public void commonPrint() {
        LOGGER.warn("%%%%%%% scheduled {} %%%%%%%", Thread.currentThread().getName());
        try {
            TimeUnit.HOURS.sleep(10);
        } catch (InterruptedException ignored) {
        }
    }

}
