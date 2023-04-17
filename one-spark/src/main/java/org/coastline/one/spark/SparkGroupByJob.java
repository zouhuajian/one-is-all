package org.coastline.one.spark;

import com.google.common.collect.Lists;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import scala.Tuple2;
import scala.Tuple6;

import java.util.*;

/**
 * @author Jay.H.Zou
 * @date 2023/4/3
 */
public class SparkGroupByJob {

    private static final StructType SCHEMA = new StructType(new StructField[]{
            new StructField("employee_name", DataTypes.StringType, false, Metadata.empty()),
            new StructField("department", DataTypes.StringType, false, Metadata.empty()),
            new StructField("state", DataTypes.StringType, false, Metadata.empty()),
            new StructField("salary", DataTypes.IntegerType, false, Metadata.empty()),
            new StructField("age", DataTypes.IntegerType, false, Metadata.empty()),
            new StructField("bonus", DataTypes.IntegerType, false, Metadata.empty())
    });

    private static final List<Tuple6<String, String, String, Integer, Integer, Integer>> DATA = Lists.newArrayList(
            new Tuple6<>("James", "Sales", "NY", 90000, 34, 10000),
            new Tuple6<>("Michael", "Sales", "NY", 86000, 56, 20000),
            new Tuple6<>("Robert", "Sales", "CA", 81000, 30, 23000),
            new Tuple6<>("Maria", "Finance", "CA", 90000, 24, 23000),
            new Tuple6<>("Raman", "Finance", "CA", 99000, 40, 24000),
            new Tuple6<>("Scott", "Finance", "NY", 83000, 36, 19000),
            new Tuple6<>("Jen", "Finance", "NY", 79000, 53, 15000),
            new Tuple6<>("Jeff", "Marketing", "CA", 80000, 25, 18000),
            new Tuple6<>("Kumar", "Marketing", "NY", 91000, 50, 21000)
    );

    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName("group-job")
                .master("local[*]")
                .getOrCreate();

        List<Row> rows = new ArrayList<>();
        for (Tuple6<String, String, String, Integer, Integer, Integer> data : DATA) {
            rows.add(RowFactory.create(data._1(), data._2(), data._3(), data._4(), data._5(), data._6()));
        }

        spark.createDataFrame(rows, SCHEMA).javaRDD()
                .groupBy(new Function<Row, String>() {
                    @Override
                    public String call(Row row) throws Exception {
                        return row.getString(1);
                    }
                }).foreach(new VoidFunction<Tuple2<String, Iterable<Row>>>() {
                    @Override
                    public void call(Tuple2<String, Iterable<Row>> tuple2) throws Exception {
                        System.out.println(tuple2);
                    }
                });
    }
}
