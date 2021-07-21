package org.coastline.algorithm.bitmap;

import java.util.BitSet;

/**
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public class JavaBitSetTest {

    public static void main(String[] args) {
        BitSet bitSet = new BitSet(8);
        System.out.println(bitSet.length());
        System.out.println(bitSet.size());

        int arr[] = {2, 3, 14, 7, 0};

        // set
        for (int num : arr) {
            bitSet.set(num, true);
        }

        // sort
        for (int i = 0; i < bitSet.size(); i++) {
            if (bitSet.get(i)) {
                System.out.print(i + " ");
            }
        }
    }
}
