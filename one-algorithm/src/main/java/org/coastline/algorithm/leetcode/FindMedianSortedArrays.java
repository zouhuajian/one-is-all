package org.coastline.algorithm.leetcode;

/**
 * 给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。
 * <p>
 * 请你找出这两个正序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 * <p>
 * 你可以假设 nums1 和 nums2 不会同时为空。
 *
 * @author Jay.H.Zou
 * @date 2020/5/17
 */
public class FindMedianSortedArrays {

    /**
     * O(m + n)
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays0(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int length = m + n;
        int left = -1;
        int right = -1;
        int start1 = 0;
        int start2 = 0;
        for (int i = 0; i <= length / 2; i++) {
            left = right;
            if (start1 < m && (start2 >= n || nums1[start1] < nums2[start2])) {
                right = nums1[start1++];
            } else {
                right = nums2[start2++];
            }
        }
        if ((length & 1) == 0) {
            return (left + right) / 2.0;
        } else {
            return right;
        }
    }

    /**
     * 转变思路，寻找第 k 小的数字
     * O(log(m + n))
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays1(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        // 不能确定奇数还是偶数，所以找寻中位数的左右两个数
        int left = (n + m + 1) / 2;
        int right = (n + m + 2) / 2;
        //将偶数和奇数的情况合并，如果是奇数，会求两次同样的 k 。
        return (getKth(nums1, 0, n - 1, nums2, 0, m - 1, left)
                + getKth(nums1, 0, n - 1, nums2, 0, m - 1, right)) * 0.5;

    }

    private static int getKth(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;
        //让 len1 的长度小于 len2，这样就能保证如果有数组空了，一定是 len1
        // 其实此处仅是用来交换位置
        if (len1 > len2) {
            return getKth(nums2, start2, end2, nums1, start1, end1, k);
        }
        // 如果第一个数组为空，则直接返回第二个数组的中位数
        if (len1 == 0) {
            return nums2[start2 + k - 1];
        }
        // 如果 k 是查询到最后一个了，则返回最小的那个即可
        if (k == 1) {
            return Math.min(nums1[start1], nums2[start2]);
        }

        int i = start1 + Math.min(len1, k / 2) - 1;
        int j = start2 + Math.min(len2, k / 2) - 1;

        if (nums1[i] > nums2[j]) {
            return getKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
        } else {
            return getKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
        }
    }

    /**
     * O(log(min(m, n)))
     *
     * @param arrayA
     * @param arrayB
     * @return
     */
    public static double findMedianSortedArrays2(int[] arrayA, int[] arrayB) {
        int small = arrayA.length;
        int big = arrayB.length;
        // to ensure m <= n
        if (small > big) {
            int[] temp = arrayA;
            arrayA = arrayB;
            arrayB = temp;
            small = arrayA.length;
            big = arrayB.length;
        }
        int iMin = 0, iMax = small, halfLen = (small + big + 1) / 2;
        while (iMin <= iMax) {
            //
            int i = (iMin + iMax) / 2;
            int j = halfLen - i;
            if (i < iMax && arrayB[j - 1] > arrayA[i]) {
                // i is too small
                iMin = i + 1;
            } else if (i > iMin && arrayA[i - 1] > arrayB[j]) {
                // i is too big
                iMax = i - 1;
            } else { // i is perfect
                int maxLeft = 0;
                if (i == 0) {
                    maxLeft = arrayB[j - 1];
                } else if (j == 0) {
                    maxLeft = arrayA[i - 1];
                } else {
                    maxLeft = Math.max(arrayA[i - 1], arrayB[j - 1]);
                }
                if ((small + big) % 2 == 1) {
                    return maxLeft;
                }

                int minRight = 0;
                if (i == small) {
                    minRight = arrayB[j];
                } else if (j == big) {
                    minRight = arrayA[i];
                } else {
                    minRight = Math.min(arrayB[j], arrayA[i]);
                }

                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }

    public static void main(String[] args) {
        int[] arrayA = {1, 2};
        int[] arrayB = {3};
        double medianSortedArrays = findMedianSortedArrays0(arrayA, arrayB);
        System.out.println(medianSortedArrays);
    }
}
