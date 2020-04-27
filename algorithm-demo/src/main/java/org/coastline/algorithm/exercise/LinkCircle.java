package org.coastline.algorithm.exercise;

/**
 * 判断链表是否有环
 *
 * @author Jay.H.Zou
 * @date 2020/3/10
 */
public class LinkCircle {

    public static boolean check(ListNode<Integer> head) {
        ListNode<Integer> quick = head;
        ListNode<Integer> slow = head;
        while (quick != null && quick.next != null) {
            slow = slow.next;
            quick = quick.next.next;
            if (slow == quick) {
                return true;
            }
        }
        return false;
    }
}

class ListNode<T> {

    T val;

    public ListNode(T val) {
        this.val = val;
    }

    ListNode<T> next;

}
