package org.coastline.one.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * 一只母牛，第二年底生一只母牛和一只公牛，第三年底生一只母牛 ，第五年开始母牛会死。
 * 公牛也只能活四年。请问一个农场开始只有一只刚出生的母牛，N年后一共有多少只牛。请写一个函数输出结果。
 * <p>
 * 1：1
 * 2：3
 * 3：6
 * 4：11
 * 5：17
 * 6：28
 *
 * @author Jay.H.Zou
 * @date 2022/5/9
 */
public class CountCow {


    // 递归方式
    private static int countCow(int years) {
        switch (years) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 3;
            case 3:
                return 6;
            case 4:
                return 11;
            default:
                return countCow(years - 2) + countCow(years - 1);
        }
    }

    // 母牛, key: age, value: count;
    private static Map<Integer, Integer> cowMap = new HashMap<>();
    private static Map<Integer, Integer> bullMap = new HashMap<>();

    // 非递归方式
    private static int countCow2(int years) {
        for (int i = 0; i < years; i++) {
            Map<Integer, Integer> newCowMap = new HashMap<>();
            Map<Integer, Integer> newBullMap = new HashMap<>();
            for (int age : cowMap.keySet()) {
                int count = cowMap.get(age);
                switch (age) {
                    case 1:
                        for (int j = 0; j < count; j++) {
                            increase(newCowMap, 1, 1);
                            increase(newBullMap, 1, 1);
                        }
                        break;
                    case 2:
                        for (int j = 0; j < count; j++) {
                            increase(newCowMap, 1, 1);
                        }
                        break;
                }
                if (++age < 5) {
                    increase(newCowMap, age, cowMap.get(age - 1));
                }
            }
            for (int age : bullMap.keySet()) {
                if (++age < 4) {
                    increase(newBullMap, age, bullMap.get(age - 1));
                }
            }
            cowMap = newCowMap;
            bullMap = newBullMap;
            System.out.println("================== 第 " + (i + 1) + " 年, 共: " + count() + " ==================");
        }
        return count();
    }

    private static void increase(Map<Integer, Integer> storage, int age, int count) {
        storage.compute(age, (k, v) -> {
            if (v == null) {
                v = count;
            } else {
                v += count;
            }
            return v;
        });
    }

    private static int count() {
        int result = 0;
        for (int count : cowMap.values()) {
            result += count;
        }
        for (int count : bullMap.values()) {
            result += count;
        }
        return result;
    }

    public static void main(String[] args) {
        int result = countCow(10);
        System.out.println(result);

        // 非递归
        cowMap.put(0, 1);
        int i = countCow2(10);
        System.out.println(i);
    }

}
