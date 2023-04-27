package org.coastline.one.core.tool;

import lombok.Builder;
import lombok.Data;

import java.util.*;

/**
 * @author Jay.H.Zou
 * @date 2023/4/26
 */
public class ArrayStabilityTool {

    private ArrayStabilityTool() {
    }

    public static StabilityResult computeStabilityScore(List<Integer> arr) {
        return computeStabilityScore(arr, 1.5, 0.5);
    }

    /**
     * 基于标准偏差计算的稳定性
     *
     * @param arr           原始数据
     * @param quartileRate  离群容忍力度
     * @param stabilityRate 稳定性阈值，比如为当前为标准差的 0.5 倍
     * @return 稳定性结果
     */
    public static StabilityResult computeStabilityScore(List<Integer> arr, double quartileRate, double stabilityRate) {
        System.err.println("Original array: " + arr);
        List<Integer> filteredArr = removeOutliersViaQuartiles(arr, quartileRate);
        System.err.println("Array with outliers removed: " + filteredArr);
        double len = filteredArr.size();
        double mean = filteredArr.stream().mapToInt(Integer::intValue).sum() / len;
        double sumAbsDev = 0;

        for (int ele : filteredArr) {
            sumAbsDev += Math.pow(ele - mean, 2);
        }
        double stabilityScore = Math.sqrt(sumAbsDev / len);

        double stabilityThreshold = stabilityScore * stabilityRate;
        int notStabilityCount = 0;
        for (int ele : filteredArr) {
            if (Math.abs(ele - mean) > stabilityThreshold) {
                notStabilityCount++;
            }
        }
        // 1/4 的元素低于阈值，则判断这个数组较为稳定
        boolean isStable = notStabilityCount <= len / 4;
        System.err.printf("Stability score: %f \nStability threshold is %f \nMean is %f \nNot stability elements: %d/%f%n",
                stabilityScore, stabilityThreshold, mean, notStabilityCount, len);
        return StabilityResult.builder()
                .stabilityScore(stabilityScore)
                .stabilityThreshold(stabilityThreshold)
                .mean(mean)
                .stable(isStable)
                .build();
    }


    /**
     * 基于 IQR 将离群值移除
     *
     * @param arr  原始数据
     * @param rate 离群容忍力度
     * @return 排除离群值之后的数据
     */
    public static List<Integer> removeOutliersViaQuartiles(List<Integer> arr, double rate) {
        arr.sort(Integer::compare);
        int len = arr.size();
        int q1Index = (int) Math.floor((len - 1) * 0.25);
        int q3Index = (int) Math.ceil((len - 1) * 0.75);
        int q1 = arr.get(q1Index);
        int q3 = arr.get(q3Index);
        int iqr = q3 - q1;
        double lowerBound = q1 - rate * iqr;
        double upperBound = q3 + rate * iqr;
        List<Integer> filtered = new ArrayList<>(len);
        for (int ele : arr) {
            if (ele >= lowerBound && ele <= upperBound) {
                filtered.add(ele);
            }
        }
        return filtered;
    }

    @Data
    @Builder
    public static class StabilityResult {
        private double stabilityScore;
        private double stabilityThreshold;
        private double mean;
        private boolean stable;
    }
}
