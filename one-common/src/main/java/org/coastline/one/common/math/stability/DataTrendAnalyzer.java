package org.coastline.one.common.math.stability;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 数据趋势分析
 *
 * @author Jay.H.Zou
 * @date 2023/9/25
 */
public class DataTrendAnalyzer {

    /*private static final List<Integer> ORIGIN_DATA = new ArrayList<>(Arrays.asList(
            164, 165, 165, 165, 165, 165, 165, 165, 166, 166, 166, 166, 166, 166, 166, 167, 167, 167, 167, 167, 167, 167,
            168, 168, 168, 168, 168, 168, 168, 169, 169, 169, 1690, 169, 169, 169, 169, 170, 170, 170, 170, 170, 170, 170,
            170, 171, 171, 171, 171, 171, 171, 171, 172, 172, 172, 172, 172, 172, 172, 172, 173, 173, 173, 173, 173, 173,
            173, 174, 174, 174, 174, 174, 174, 174, 174, 175, 175, 175, 175, 175, 175, 175, 176, 176, 176
    ));*/
    private static final List<Integer> ORIGIN_DATA = new ArrayList<>(Arrays.asList(
            267, 267, 268, 268, 268, 269, 269, 270, 270, 271, 271, 271, 271, 272, 273, 273, 274, 274, 274, 275, 275, 276,
            276, 277, 277, 277, 278, 278, 279, 279, 280, 280, 280, 281, 281, 282, 282, 283, 283, 283, 284, 284, 285, 284,
            284, 285, 285, 285, 286, 286, 286, 287, 287, 288, 288, 289, 289, 290, 290, 291, 290, 291, 292, 292, 292, 293,
            293, 294, 294, 294, 294, 295, 295, 296, 296, 297, 297, 298, 298, 298, 299, 299, 299, 291, 271, 271, 272, 272,
            272, 272, 272, 273, 273, 273, 273, 273, 274, 274, 274, 274, 274, 275, 275, 275, 275, 275, 276, 276, 277, 277,
            277, 278, 278, 279, 267, 267
    ));



    public static void main(String[] args) {
        Random random = new Random();
        int size = 10000;
        double[] data = new double[size];
        for (int i = 0; i < size; i++) {
            data[i] = random.nextDouble() * 10000;
        }
        //double[] data = ORIGIN_DATA.stream().mapToDouble(value -> value).toArray();
        List<Double> filteredData = getFilterData(data);

        // 建立线性回归模型
        SimpleRegression regression = new SimpleRegression();
        for (int i = 0; i < filteredData.size(); i++) {
            regression.addData(i, filteredData.get(i));
        }

        // 获取回归系数（斜率）和 p 值
        double slope = regression.getSlope();
        double pValue = regression.getSignificance();
        System.out.println("获取回归系数（斜率）:" + slope);
        System.out.println("获取回归 p 值: " + pValue);
        // 判断数据的趋势和稳定性
        if (Math.abs(slope) < 1E-6 && (Double.isNaN(pValue) || pValue > 0.05)) {
            System.out.println("数据稳定");
        } else if (slope > 0 && pValue <= 0.05) {
            System.out.println("数据递增");
        } else if (slope < 0 && pValue <= 0.05) {
            System.out.println("数据递减");
        } else {
            System.out.println("数据不稳定");
        }

        double predictedValue7Days = filteredData.get(filteredData.size() - 1) + slope * 7;
        double predictedValue30Days = filteredData.get(filteredData.size() - 1) + slope * 30;
        double predictedValue90Days = filteredData.get(filteredData.size() - 1) + slope * 90;

        System.out.println("预测未来7天后的数据：" + predictedValue7Days);
        System.out.println("预测未来30天后的数据：" + predictedValue30Days);
        System.out.println("预测未来90天后的数据：" + predictedValue90Days);
    }

    @NotNull
    private static List<Double> getFilterData(double[] data) {
        DescriptiveStatistics statistics = new DescriptiveStatistics(data);
        // 计算均值和标准差
        double mean = statistics.getMean();
        double stdDev = statistics.getStandardDeviation();

        // 定义离群值阈值（例如，2倍标准差）
        double outlierThreshold = 2 * stdDev;

        // 剔除离群值
        List<Double> filteredData = new ArrayList<>();
        for (double value : data) {
            if (Math.abs(value - mean) <= outlierThreshold) {
                filteredData.add(value);
            }
        }
        return filteredData;
    }
}
