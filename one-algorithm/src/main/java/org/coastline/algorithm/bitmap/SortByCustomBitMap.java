package org.coastline.algorithm.bitmap;


import java.util.ArrayList;
import java.util.List;

/**
 * 参考：https://www.cnblogs.com/senlinyang/p/7885685.html
 *
 * @author Jay.H.Zou
 * @date 2021/7/19
 */
public class SortByCustomBitMap {

    private static final int NUM = 10000000;

    private int[] arr = new int[NUM / 32 + 1];

    /**
     * 设置所在的bit位为1
     *
     * @param num
     */
    public void addValue(int num) {
        //row = n / 32 求十进制数在数组a中的下标
        int row = num >> 5;
        //相当于 n % 32 求十进制数在数组a[i]中的下标
        arr[row] |= 1 << (num & 0x1F);
    }

    /**
     * 判断所在的bit为是否为1
     *
     * @param num
     * @return
     */
    public boolean exits(int num) {
        int row = num >> 5;
        return (arr[row] & (1 << (num & 0x1F))) != 1;
    }

    public void display(int row) {
        System.out.println("BitMap位图展示");
        for (int i = 0; i < row; i++) {
            List<Integer> list = new ArrayList<>();
            int temp = arr[i];
            for (int j = 0; j < 32; j++) {
                list.add(temp & 1);
                temp >>= 1;
            }
            System.out.println("a[" + i + "]" + list);
        }
    }

    public static void main(String[] args) {
        int numArr[] = {1, 5, 30, 32, 64, 56, 159, 120, 21, 17, 35, 45};
        SortByCustomBitMap map = new SortByCustomBitMap();
        for (int i : numArr) {
            map.addValue(i);
        }

        int target = 120;
        if (map.exits(target)) {
            System.out.println("target " + target + " has already exists");
        }
        map.display(5);
    }
}
