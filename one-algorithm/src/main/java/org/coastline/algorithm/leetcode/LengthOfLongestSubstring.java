package org.coastline.algorithm.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * @author Jay.H.Zou
 * @date 2020/5/28
 */
public class LengthOfLongestSubstring {

    public static int lengthOfLongestSubstring(String s) {
        int length = s.length();
        Set<Character> characters = new HashSet<>(length);
        int rightKey = -1;
        int ans = 0;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                // 左指针往右移动，则从set中移除上一个字符
                characters.remove(s.charAt(i - 1));
            }
            while (rightKey + 1 < length && !characters.contains(s.charAt(rightKey + 1))) {
                characters.add(s.charAt(rightKey + 1));
                rightKey++;
            }
            ans = Math.max(ans, rightKey - i + 1);
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
    }

}
