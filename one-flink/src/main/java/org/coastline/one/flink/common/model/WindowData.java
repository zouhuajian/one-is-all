package org.coastline.one.flink.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.io.Serializable;
import java.util.List;

/**
 * @author zouhuajian
 * @date 2021/5/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WindowData<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String key;
    private TimeWindow window;
    private List<T> dataList;
}
