package org.coastline.algorithm.sort;

import java.util.Arrays;

/**
 * @author Jay.H.Zou
 * @date 2020/2/19
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = {5, 3, 6, 4, 2, 9, 2, 7};
        System.out.println("排序后的数组：" + bubbleSort3(arr));
    }

    private static String bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            // 有序标记，每一轮的初始值都是 true
            boolean isSorted = true;
            // 二层循环置换
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j + 1] < arr[j]) {
                    exchange(arr, j);
                    // 因为有数据交换，所以认为不是有序的，则循环不会退出
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
        }
        return Arrays.toString(arr);
    }

    /**
     * 冒泡排序优化方案
     * int[] arr = {3, 4, 2, 1, 5, 6, 7, 8}
     *
     * @param arr
     * @return
     */
    private static String bubbleSort2(int[] arr) {
        // 最后一次交换的位置
        int lastExchangeIndex = 0;
        // 无序数列的边界，每次比较只需要比到这里即可
        int sortBorder = arr.length - 1;
        for (int i = 0; i < arr.length - 1; i++) {
            // 有序标记，每一轮的初始值都是 true
            boolean isSorted = true;
            for (int j = 0; j < sortBorder; j++) {
                if (arr[j] > arr[j + 1]) {
                    exchange(arr, j);
                    // 因为有元素交换，所以不是有序的
                    isSorted = false;
                    lastExchangeIndex = j;
                }
            }
            sortBorder = lastExchangeIndex;
            if (isSorted) {
                break;
            }
        }
        return Arrays.toString(arr);
    }

    /**
     * 鸡尾酒排序
     *
     * @param arr
     * @return
     */
    private static String bubbleSort3(int[] arr) {
        for (int i = 0; i < arr.length / 2; i++) {
            boolean isSorted = true;
            // 奇数轮，从左向右比较和交换
            for (int j = i; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    exchange(arr, j);
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
            isSorted = true;
            // 偶数轮，从右向左遍历
            for (int j = arr.length - i - 1; j > i; j--) {
                if (arr[j] > arr[j - 1]) {
                    exchange(arr, j - 1);
                    isSorted = false;
                }

            }
            if (isSorted) {
                break;
            }
        }
        return Arrays.toString(arr);
    }

    private static void exchange(int[] arr, int index) {
        int temp = arr[index];
        arr[index] = arr[index + 1];
        arr[index + 1] = temp;
    }

}
