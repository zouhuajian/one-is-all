package org.coastline.one.sentinel.redis;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 不支持多重降级
 * 支持多个规则降级
 * 如果因为限流而降级会走到 blockHandler 中
 *
 * @author Jay.H.Zou
 * @date 8/27/2019
 */
@Service
public class RedisService implements ApplicationListener<ContextRefreshedEvent> {


    @SentinelResource(value = "onlineRedis", fallback = "offlineRedis", blockHandler = "blockHandler")
    public String onlineRedis(String key) throws Exception {
        System.out.println("====== query online ====== " + key);
        Thread.sleep(100);
        return "online";
    }

    //@SentinelResource(value = "onlineRedis", fallback = "offlineRedis", blockHandler = "blockHandler")
    public String onlineRedis2(String key) throws Exception {
        System.out.println("====== query online ====== " + key);
        Thread.sleep(50);
        return "online";
    }

    //@SentinelResource(value = "onlineRedis", fallback = "fallbackAgain", blockHandler = "blockHandler")
    public String offlineRedis(String key, Throwable e) {
        System.err.println("====== query fallback ====== " + key + " " + e);
        return "offline";
    }

    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public String blockHandler(String key, BlockException e) {
        // Do some log here.
        System.err.println("====== query block ====== " + key + " " + e);
        return "Oops, error occurred at " + key;
    }

    public String fallbackAgain(String key) {
        System.err.println("====== return nothing ======");
        return "nothing";
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        // 流量控制
        List<FlowRule> flowRules = new ArrayList<>();
        FlowRule flowRule = new FlowRule("onlineRedis");
        // set limit qps to 20
        flowRule.setCount(20);
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setLimitApp("default");
        // 快速失败：CONTROL_BEHAVIOR_DEFAULT -> 达到阈值后，全部直接走 blockHandler
        // 预热: CONTROL_BEHAVIOR_WARM_UP -> 先预热，如果达到最大值超过阈值，则会走入 blockHandler
        // 匀速排队: DEGRADE_GRADE_EXCEPTION_COUNT -> 达到最大值后排队
        flowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER);
        flowRule.setWarmUpPeriodSec(10);
        flowRules.add(flowRule);
        //FlowRuleManager.loadRules(flowRules);

        // 降级
        List<DegradeRule> degradeRules = new ArrayList<>();
        // 基于平均响应时间
        DegradeRule rtRule = new DegradeRule("onlineRedis");
        rtRule.setCount(20);
        rtRule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        rtRule.setTimeWindow(5);
        degradeRules.add(rtRule);

        DegradeRule exRule = new DegradeRule("onlineRedis");
        exRule.setCount(3);
        exRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        exRule.setTimeWindow(5);
        //degradeRules.add(exRule);
        DegradeRuleManager.loadRules(degradeRules);

    }

}
