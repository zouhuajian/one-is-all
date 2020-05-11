package org.coastline.algorithm.exercise;

/**
 * f(n) = f(n -1) + f(n - 2)
 *
 * @author Jay.H.Zou
 * @date 2020/5/11
 */
public class JumpSolution {

    public static int jump(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int one = 1;
        int two = 2;
        int sum;
        for (int i = 3; i < n; i++) {
           sum = two;
           two = one + two;
           one = sum;
        }
        return one + two;
    }

    public static void main(String[] args) {
        System.out.println(jump(10));
    }
}
