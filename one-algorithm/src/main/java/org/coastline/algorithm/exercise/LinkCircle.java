package org.coastline.algorithm.exercise;


import org.coastline.algorithm.exercise.entity.ListNode;

/**
 * 判断链表是否有环
 *
 * @author Jay.H.Zou
 * @date 2020/3/10
 */
public class LinkCircle {

    public static boolean isCycle(ListNode<Integer> head) {
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

    public static void main(String[] args) {
        ListNode<Integer> node1 = new ListNode<>(1);
        ListNode<Integer> node2 = new ListNode<>(3);
        ListNode<Integer> node3 = new ListNode<>(7);
        ListNode<Integer> node4 = new ListNode<>(2);
        ListNode<Integer> node5 = new ListNode<>(6);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        System.out.println(isCycle(node1));
    }
}
