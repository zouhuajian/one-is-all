package org.coastline.algorithm.exercise;

/**
 * 求出两数最大公约数，尽量优化算法性能
 *
 * @author Jay.H.Zou
 * @date 2020/4/29
 */
public class GreatestCommonDivisor {

    /**
     * 辗转相除法（欧几里得算法）：两个正整数 a 和 b (a > b)，它们的最大公约数等于 a 除以 b 的余数 c 和 b 之间的最大公约数
     *
     * @param a
     * @param b
     * @return
     */
    public static int calculate(int a, int b) {
        int big = Math.max(a, b);
        int small = Math.min(a, b);
        // 当两个数较大时，取模运算性能较差
        if (big % small == 0) {
            return small;
        }
        return calculate(big, small);
    }

    /**
     * 更相减损术：两个正整数 a 和 b (a > b)，它们的最大公约数等于 a - b 的差值 c 和较小数 b 的最大公约数
     *
     * @param a
     * @param b
     * @return
     */
    public static int calculate2(int a, int b) {
        if (a == b) {
            return a;
        }
        int big = Math.max(a, b);
        int small = Math.min(a, b);
        return calculate2(big - small, small);
    }

    public static int calculate3(int a, int b) {
        if (a == b) {
            return a;
        }
        // (a & 1) == 0 则说明 a 为偶数
        if ((a & 1) == 0 && (b & 1) == 0) {
            return calculate3(a >> 1, b >> 1) << 1;
        } else if ((a & 1) == 0 && (b & 1) != 0) {
            return calculate3(a >> 1, b);
        } else if ((a & 1) != 0 && (b & 1) == 0) {
            return calculate3(a, b >> 1);
        } else {
            int big = Math.max(a, b);
            int small = Math.min(a, b);
            return calculate3(big - small, small);
        }
    }

    public static void main(String[] args) {
        System.out.println(calculate(250000000, 5));
    }

}
