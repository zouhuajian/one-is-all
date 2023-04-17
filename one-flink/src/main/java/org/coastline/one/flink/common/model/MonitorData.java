package org.coastline.one.flink.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2021/3/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonitorData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private long index;
    private long time;
    private long timeNano;
    private String traceId;
    private String msgId;
    private String parentMsgId;
    private String service;
    private String zone;
    private String host;
    private String category;
    private String type;
    private String name;
    private double duration;
    private long durationMicros;
    private String status;
    private String data;
    private List<MonitorData> children;
}
