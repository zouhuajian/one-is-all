package org.coastline.algorithm.sort;

/**
 * @author Jay.H.Zou
 * @date 2020/2/25
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {5, 1, 7, 3, 20, 6, 9, 4};
        quickSort(arr, 0, arr.length - 1);
        for (int i : arr) {
            System.out.print(i + ", ");
        }
    }

    /**
     * @param arr        待排序列
     * @param leftIndex  待排序列起始位置
     * @param rightIndex 待排序列结束位置
     */
    private static void quickSort(int[] arr, int leftIndex, int rightIndex) {
        if (leftIndex >= rightIndex) {
            return;
        }
        int left = leftIndex;
        int right = rightIndex;
        //待排序的第一个元素作为基准值
        int key = arr[right];
        //从左右两边交替扫描，直到left = right
        while (left < right) {

            while (right > left && arr[left] <= key) {
                //从左往右扫描，找到第一个比基准值大的元素
                left++;
            }
            //找到这种元素将arr[left]放入arr[right]中
            arr[right] = arr[left];

            while (right > left && arr[right] >= key) {
                //从右往左扫描，找到第一个比基准值小的元素
                right--;
            }
            //找到这种元素将arr[right]放入arr[left]中
            arr[left] = arr[right];
        }
        //基准值归位
        arr[right] = key;
        //对基准值左边的元素进行递归排序
        quickSort(arr, leftIndex, left - 1);
        //对基准值右边的元素进行递归排序。
        quickSort(arr, right + 1, rightIndex);
    }

    /**
     * 单边循环法
     *
     * @param arr
     * @param startIndex
     * @param endIndex
     */
    private static void quickSort2(int[] arr, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }
        // 取第一个位置，也可以选择其他位置
        int pivot = arr[startIndex];
        int mark = startIndex;
        for (int i = startIndex + 1; i <= endIndex; i++) {
            if (arr[i] < pivot) {
                mark++;
                int temp = arr[mark];
                arr[mark] = arr[i];
                arr[i] = temp;
            }
        }
        arr[startIndex] = arr[mark];
        arr[mark] = pivot;
        quickSort2(arr, startIndex, mark - 1);
        quickSort2(arr, mark + 1, endIndex);
    }
}
