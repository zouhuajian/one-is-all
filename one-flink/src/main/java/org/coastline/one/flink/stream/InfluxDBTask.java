package org.coastline.one.flink.stream;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import scala.util.parsing.json.JSONObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class InfluxDBTask {


    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.addSource(new RichSourceFunction<JSONObject>() {

            /**
             * 查询间隔
             */
            int duration = 60;

            boolean runFlag = true;

            InfluxDB influxDB;

            @Override
            public void open(Configuration parameters) {
                System.out.println(parameters);
                influxDB = InfluxDBFactory.connect("http://xxx:8086");
                influxDB.setDatabase("cat");
            }

            @Override
            public void run(SourceContext<JSONObject> sourceContext) throws Exception {
                // TODO: 计算开始/结束时间

                LocalDateTime now = LocalDateTime.now();
                Instant endTime = now.toInstant(ZoneOffset.UTC);
                Instant startTime = now.minusHours(20).toInstant(ZoneOffset.UTC);
                // while (runFlag) {
                // TODO: 计算时间范围

                String query = "SELECT * FROM \"arch-replay-service\" " +
                        "WHERE \"time\" >  '" + startTime.toString() + "'" +
                        " AND \"time\" <= '" + endTime.toString() + "'" +
                        " ORDER BY time DESC";

                Query showDatabases = new Query(query);
                QueryResult queryResult = influxDB.query(showDatabases);
                List<QueryResult.Result> results = queryResult.getResults();
                for (QueryResult.Result resultResult : results) {
                    for (QueryResult.Series series : resultResult.getSeries()) {
                        Map<String, String> tags = series.getTags();
                        List<String> columns = series.getColumns();
                        System.out.println(columns);
                        System.out.println(tags);
                        for (List<Object> list : series.getValues()) {
                            System.out.println(list);
                        }
                    }
                }
                TimeUnit.SECONDS.sleep(duration);

                //}

                //sourceContext.collect(null);

            }

            @Override
            public void cancel() {
                if (influxDB != null) {
                    influxDB.close();
                }
            }
        }).print().setParallelism(1);

        env.execute("influxdb-task");
    }

}
