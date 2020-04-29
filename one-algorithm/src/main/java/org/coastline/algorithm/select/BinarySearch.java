package org.coastline.algorithm.select;

/**
 * @author Jay.H.Zou
 * @date 2020/2/26
 */
public class BinarySearch {

    static int index = -1;

    public static void main(String[] args) {
        int[] array = {1, 3, 4, 5, 6, 7, 9, 20};
        System.err.println(search(array, 0, array.length - 1, 20));
    }

    /**
     * @param array  已经排序好的数组
     * @param low    低位数值
     * @param high   高位
     * @param target
     * @return
     */
    private static int search(int[] array, int low, int high, int target) {

        int mid = (low + high) / 2;
        if (array[mid] == target) {
            index = mid;
            return mid;
        }
        if (low > high) {
            return -1;
        } else if (target > array[mid]) {
            return search(array, mid + 1, high, target);
        } else if (target < array[mid]) {
            return search(array, low, mid - 1, target);
        }
        return -1;
    }

}
