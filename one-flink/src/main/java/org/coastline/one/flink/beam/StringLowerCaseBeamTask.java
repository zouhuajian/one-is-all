package org.coastline.one.flink.beam;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

public class StringLowerCaseBeamTask {
    public static void main(String[] args) {
        // 建立选项
        PipelineOptions pipelineOptions = PipelineOptionsFactory.create();
        // 建立管道
        Pipeline pipeline = Pipeline.create(pipelineOptions);

        // 生成初始的输入数据
        // 相当于往管道里塞入了3个自己写的字符串元素
        PCollection<String> pcStart = pipeline.apply(
                Create.of(
                        "HELLO！",
                        "THIS IS BEAM DEMO!",
                        "HAPPY STUDY!"));

        // 组装小写转换
        PCollection<String> pcMid = pcStart.apply(ParDo.of(new StringToLowerCaseFn()));

        // 组装输出操作
        pcMid.apply(ParDo.of(new PrintFn()));

        // 运行结果
        pipeline.run().waitUntilFinish();
    }


}
