package org.coastline.one.flink.stream.model;

/**
 * @author zouhuajian
 * @date 2020/11/20
 */
public class AggregateData {

    private String host;

    private String time;

    private Integer count;

    private Double value;


    public AggregateData(String host, String time, Double value) {
        this.host = host;
        this.time = time;
        this.value = value;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "AggregateData{" +
                "host='" + host + '\'' +
                ", time='" + time + '\'' +
                ", count=" + count +
                ", value=" + value +
                '}';
    }
}
