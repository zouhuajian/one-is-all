package org.coastline.algorithm.exercise;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Jay.H.Zou
 * @date 2020/3/9
 */
public class StringRepeatSolution {

    public static void main(String[] args) {
        System.err.println(lengthOfLongestSubstring02("assassag"));
    }

    public static int lengthOfLongestSubstring02(String str) {
        int result = 0;
        int length = str.length();
        Set<Character> set = new HashSet<>();
        int left = 0, right = 0;
        while (left < length && right < length) {
            char rightSingle = str.charAt(right);
            if (!set.contains(rightSingle)) {
                set.add(rightSingle);
                right++;
                result = Math.max(right - left, result);
            } else {
                set.remove(str.charAt(left));
                left++;
            }
        }
        return result;
    }

    public static int lengthOfLongestSubstring(String s) {
        int length = s.length();
        Set<Character> set = new HashSet<>();
        int answer = 0, left = 0, right = 0;
        while (left < length && right < length) {
            // try to extend the range [i, j]
            char jStr = s.charAt(right);
            if (!set.contains(jStr)) {
                char single = s.charAt(right);
                right++;
                set.add(single);
                // 判断
                answer = Math.max(answer, right - left);
            } else {
                set.remove(s.charAt(left++));
            }
        }
        return answer;
    }

    public static int lengthOfLongestSubstring03(String s) {
        int length = s.length(), ans = 0;
        //创建map窗口,left为左区间，right为右区间，右边界移动
        Map<Character, Integer> map = new HashMap<>();
        for (int right = 0, left = 0; right < length; right++) {
            // 如果窗口中包含当前字符
            if (map.containsKey(s.charAt(right))) {
                //左边界移动到相同字符的下一个位置和left当前位置中更靠右的位置，这样是为了防止left向左移动
                left = Math.max(map.get(s.charAt(right)), left);
            }
            //比对当前无重复字段长度和储存的长度，选最大值并替换
            //right-left+1是因为此时left,right索引仍处于不重复的位置，right还没有向后移动，取的[left,right]长度
            ans = Math.max(ans, right - left + 1);
            // 将当前字符为key，下一个索引为value放入map中
            // value为right+1是为了当出现重复字符时，left直接跳到上个相同字符的下一个位置，if中取值就不用+1了
            map.put(s.charAt(right), right + 1);
        }
        return ans;
    }

}
