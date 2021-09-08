package org.coastline.one.flink.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Jay.H.Zou
 * @date 2021/3/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonitorData implements Serializable {
    private static final long serialVersionUID = 1L;
    private long time;
    private String service;
    private String zone;
    private String name;
    private long duration;
}
