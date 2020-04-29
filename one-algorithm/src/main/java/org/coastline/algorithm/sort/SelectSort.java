package org.coastline.algorithm.sort;

import java.util.Arrays;

/**
 * 选择排序
 * <p>
 * 首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，
 * 然后再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。以此类推，直到所有元素均排序完毕。
 *
 * @author Jay.H.Zou
 * @date 2020/2/19
 */
public class SelectSort {

    public static void main(String[] args) {
        int[] arr = {5, 3, 6, 4, 2};
        System.out.println("排序后的数组：" + selectionSort(arr));
    }

    private static String selectionSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
        return Arrays.toString(arr);

    }
}
