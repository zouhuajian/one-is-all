package org.coastline.one.trace;

import io.jaegertracing.Configuration;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

import java.time.Instant;

/**
 * @author zouhuajian
 * @date 2021/6/16
 */
public class MyTrace {
    public static void main(String[] args) throws InterruptedException {
        Configuration config = new Configuration("one-jaeger");
        // 设置数据发送方式
        Configuration.SenderConfiguration sender = new Configuration.SenderConfiguration();
        // 将 <endpoint> 替换为控制台概览页面上相应客户端和地域的接入点。
        sender.withAgentHost("xxx");
        sender.withAgentPort(5775);
        config.withSampler(new Configuration.SamplerConfiguration().withType("const").withParam(1));
        config.withReporter(new Configuration.ReporterConfiguration().withSender(sender).withMaxQueueSize(10000));
        GlobalTracer.registerIfAbsent(config.getTracer());
        Tracer tracer = GlobalTracer.get();
        // 创建 Span, 并指定 operationName
        Span urlSpan = tracer.buildSpan("URL").start();
        // 主要用于链路追踪结果对查询和过滤。需要注意的是， tags 不会传递给下一个 span 调用，即仅自己可见。
        urlSpan.setTag("http_code", "200");
        urlSpan.setTag("http_method", "GET");
        tracer.scopeManager().activate(urlSpan);

        tracer.activeSpan().setTag("tag_one", "tag_value");
        Span redisSpan = GlobalTracer.get().buildSpan("Redis").asChildOf(urlSpan).start();
        // 业务逻辑。
        secondBiz();
        redisSpan.log("SET");
        redisSpan.log("GET");
        redisSpan.log(Instant.now().getNano(), "HGET");
        redisSpan.finish();
        urlSpan.finish();
    }

    private static void secondBiz() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("biz code");
    }


}
