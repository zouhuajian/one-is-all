package org.coastline.one.common.math;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.junit.Test;

/**
 * @author Jay.H.Zou
 * @date 2023/9/14
 */
public class TestMath {

    @Test
    public void testGrowthStabilityCalculator() {
        double[] data = {10, 15, 20, 25, 30}; // 用你的数据替换这个数组

        // 计算平均增长率
        double growthRate = calculateGrowthRate(data);

        // 计算标准差，用于衡量稳定性
        double standardDeviation = calculateStandardDeviation(data);

        // 根据增长率和稳定性得出结论
        String conclusion = analyzeData(growthRate, standardDeviation);

        System.out.println("平均增长率: " + growthRate);
        System.out.println("稳定性 (标准差): " + standardDeviation);
        System.out.println("结论: " + conclusion);
    }

    // 计算平均增长率
    private static double calculateGrowthRate(double[] data) {
        double firstValue = data[0];
        double lastValue = data[data.length - 1];
        return (lastValue - firstValue) / firstValue;
    }

    // 计算标准差
    private static double calculateStandardDeviation(double[] data) {
        double mean = new Mean().evaluate(data);
        double sumSquaredDifferences = 0;

        for (double value : data) {
            double difference = value - mean;
            sumSquaredDifferences += difference * difference;
        }

        return Math.sqrt(sumSquaredDifferences / (data.length - 1));
    }

    // 根据增长率和稳定性得出结论
    private static String analyzeData(double growthRate, double standardDeviation) {
        if (Math.abs(growthRate) < 0.01) {
            return "增长率接近于零，数据趋于稳定。";
        } else if (growthRate > 0) {
            return "数据呈现增长趋势。";
        } else {
            return "数据呈现下降趋势。";
        }
    }
}
