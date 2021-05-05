package org.coastline.one.flink.stream.model;

/**
 * @author Jay.H.Zou
 * @date 2021/3/22
 */
public class MonitorData {

    private long time;

    private long duration;

    private String name;

    private String value;

    public MonitorData(long time, long duration, String name) {
        this.time = time;
        this.duration = duration;
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MonitorData{" +
                "time=" + time +
                ", duration=" + duration +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
