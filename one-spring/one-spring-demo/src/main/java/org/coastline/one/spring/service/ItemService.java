package org.coastline.one.spring.service;

import cn.hutool.core.net.NetUtil;
import io.opentelemetry.api.metrics.GlobalMeterProvider;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.common.Labels;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import org.coastline.one.spring.config.OTelConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jay.H.Zou
 * @date 2021/1/24
 */
@Service
public class ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    private LongCounter longCounter =
            GlobalMeterProvider.get().get(this.getClass().getName())
                    .longCounterBuilder("item_metrics_count")
                    .setDescription("item metrics")
                    .build();

    public String getItem(String key) {
        String localHostName = NetUtil.getLocalHostName();
        longCounter.add(10, Labels.of("host", localHostName, "key", key));
        logger.info("get item, key = {}", key);
        // do stuff
        return this.getClass().getName();
    }


    @Transactional(rollbackFor = Exception.class)
    public void updateItem() {
        System.out.println(this.getClass().getName());
    }

}
