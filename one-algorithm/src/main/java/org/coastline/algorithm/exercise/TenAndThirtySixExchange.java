package org.coastline.algorithm.exercise;

import java.util.HashMap;

/**
 * @author Jay.H.Zou
 * @date 2020/5/26
 */
public class TenAndThirtySixExchange {

    //设置字符数组
    //可以添加任意不重复字符，提高能转换的进制的上限
    static char chs[] = new char[36];

    static {
        for (int i = 0; i < 10; i++) {
            chs[i] = (char) ('0' + i);
        }
        for (int i = 10; i < chs.length; i++) {
            chs[i] = (char) ('A' + (i - 10));
        }
    }

    /**
     * 转换方法
     *
     * @param num       元数据字符串
     * @param fromRadix 元数据的进制类型
     * @param toRadix   目标进制类型
     * @return
     */
    static String transRadix(String num, int fromRadix, int toRadix) {
        int number = Integer.valueOf(num, fromRadix);
        StringBuilder sb = new StringBuilder();
        while (number != 0) {
            sb.append(chs[number % toRadix]);
            number = number / toRadix;
        }
        return sb.reverse().toString();

    }

    //测试
    public static void main(String[] args) {
        System.out.println(transRadix("YGL", 36, 10));
    }


}
