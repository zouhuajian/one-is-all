package org.coastline.algorithm.bitmap;

import java.util.BitSet;

/**
 * @author Jay.H.Zou
 * @date 2021/7/20
 */
public class JavaBitSetTest {

    public static void main(String[] args) {
        BitSet bitSet = new BitSet(64);
        System.out.println(bitSet.length());
        System.out.println(bitSet.size());
        System.out.println("=========================");
        for (int i = 0; i < 1000000000; i+=1000) {
            bitSet.set(i, true);

        }
        System.out.println(bitSet.length());
        System.out.println(bitSet.size());
        System.out.println(bitSet.cardinality());
        // sort

        /*for (int i = 0; i < bitSet.size(); i++) {
            if (bitSet.get(i)) {
                System.out.print(i + " ");
            }
        }*/
    }
}
