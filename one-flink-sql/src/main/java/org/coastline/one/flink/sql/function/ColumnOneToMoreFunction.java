package org.coastline.one.flink.sql.function;

import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.annotation.FunctionHint;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.types.Row;

/**
 * @author Jay.H.Zou
 * @date 2023/4/28
 */
@FunctionHint(output = @DataTypeHint("ROW<time_first STRING, time_second STRING>"))
public class ColumnOneToMoreFunction extends TableFunction<Row> {

    public void eval(String column) {
        String[] split = column.split("\\.");
        collect(Row.of(split[0], split[1]));
    }
}
