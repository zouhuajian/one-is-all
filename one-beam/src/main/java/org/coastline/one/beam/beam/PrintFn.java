package org.coastline.one.beam.beam;

import org.apache.beam.sdk.transforms.DoFn;

public class PrintFn extends DoFn<String, Void> {
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

        // 输出
        System.out.println(inputStr);
    }
}
