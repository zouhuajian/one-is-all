package org.coastline.one.algorithm;

import java.util.*;

/**
 * 滑动窗口可以很好的解决数组或列表的问题。如果一个问题可以通过不断的暴力循环直接解决，那么借助滑动窗口就可以把
 * O(n^2) 这种时间复杂度降低到 O(n) 。
 * 一个很简单的例子，就是求一串数组当中连续5个数最大和。
 * <p>
 * 或是当你遇到题目要求在一串大的数组、字符串当中求最大值，最小值，子序列这种时，就可以尝试使用滑动窗口去求解答案。
 *
 * @author Jay.H.Zou
 * @date 2022/5/12
 */
public class SlidingWindow {

    private static int lengthOfLongestSubstring(String str) {
        if (str == null) {
            return 0;
        }
        int length = str.length();
        if (length == 1) {
            return 1;
        }
        int left, right, max = 0;
        int number = 0;
        int p[] = new int[256];


        return 0;
    }

    // 给定一个含有 n 个正整数的数组和一个正整数 target 。
    //
    //找出该数组中满足其和 ≥ target 的长度最小的 连续子数组 [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。如果不存在符合条件的子数组，返回 0 。

    private static int minSubArrayLen(int target, int[] nums) {
        // 暴力解法 O(n²)
        int n = nums.length;
        /*int answer = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum += nums[j];
                if (sum >= target) {
                    answer = Math.min(answer, j - i + 1);
                    break;
                }
            }
        }
        return answer == Integer.MAX_VALUE ? 0 : answer;*/

        // 滑动窗口 O(n)
        int left = 0, right = 0, sum = 0;
        int answer = Integer.MAX_VALUE;
        for (int num : nums) {
            sum += num;
            while (sum >= target) {
                answer = Math.min(answer, right - left + 1);
                sum -= nums[left];
                left++;
            }
            right++;
        }
        return answer == Integer.MAX_VALUE ? 0 : answer;
    }

    // 如果一个字符串不含有任何重复字符，我们称这个字符串为 好字符串。
    //给你一个字符串 s，请你返回 s 中长度为 3的 好子字符串 的数量。
    //注意，如果相同的好子字符串出现多次，每一次都应该被记入答案之中。
    //子字符串 是一个字符串中连续的字符序列。
    public static int countGoodSubstrings(String str) {
        int answer = 0;
        int left = 0, right = 0;
        int length = str.length();
        for (int i = 0; i < length; i++) {
            int range = right - left + 1;
            if (range == 3) {
                Set<Integer> temp = new HashSet<>();
                for (int j = left; j <= right; j++) {
                    temp.add((int) str.charAt(j));
                }
                if (temp.size() == 3) {
                    answer++;
                }
                left++;
            }
            right++;
        }
        return answer;
    }

    public static void main(String[] args) {
        //System.out.println(minSubArrayLen(11, new int[]{1, 2, 3, 4, 5}));
        System.out.println(countGoodSubstrings("aababcabc"));
    }
}
