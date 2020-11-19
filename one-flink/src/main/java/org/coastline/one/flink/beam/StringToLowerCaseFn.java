package org.coastline.one.flink.beam;

import org.apache.beam.sdk.transforms.DoFn;

public class StringToLowerCaseFn extends DoFn<String, String> {
    /**
     * processElement，过程元素处理方法，类似于spark、mr中的map操作
     * 必须加上@ProcessElement注解，并实现processElement方法
     *
     * @param context
     */
    @ProcessElement
    public void processElement(ProcessContext context) {
        // 从管道中取出的1个元素
        String inputStr = context.element();
        // 转成大写
        String outputStr = inputStr.toLowerCase();
        // 输出结果
        context.output(outputStr);
    }

}
