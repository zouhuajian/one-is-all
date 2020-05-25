package org.coastline.algorithm.exercise;


import org.coastline.algorithm.entity.ListNode;

import java.util.Stack;

/**
 * 问题：如何实现一个高效的单向链表逆序输出？
 * 出题人：阿里巴巴出题专家：昀龙／阿里云弹性人工智能负责人
 * 参考答案：下面是其中一种写法，也可以有不同的写法，比如递归等。供参考。
 *
 * @author Jay.H.Zou
 * @date 2020/2/27
 */
public class ReverseOrder<T> {

    /**
     * 使用栈作为中转容器
     *
     * @param head
     */
    public static void reverse(ListNode<Integer> head) {
        if (head == null || head.next == null) {
            return;
        }
        ListNode<Integer> currentNode = head;
        Stack<ListNode<Integer>> stack = new Stack<>();
        while (currentNode != null) {
            stack.push(currentNode);
            ListNode<Integer> tempNode = currentNode.next;
            // 断开连接
            currentNode.next = null;
            currentNode = tempNode;
        }

        head = stack.pop();
        currentNode = head;

        while (!stack.isEmpty()) {
            currentNode.next = stack.pop();
            currentNode = currentNode.next;
        }
    }

    /**
     * 时间复杂度：O(n)，假设 n 是列表的长度，时间复杂度是 O(n)。
     * 空间复杂度：O(1)。
     *
     * @param head
     * @return
     */
    public static ListNode<Integer> reverseIterate(ListNode<Integer> head) {
        ListNode<Integer> prev = null;
        ListNode<Integer> curr = head;
        while (curr != null) {
            // 保存下个节点的信息
            ListNode<Integer> nextTemp = curr.next;
            // 断掉next
            curr.next = prev;
            // 将当前节点作为下个节点的上一个节点
            prev = curr;
            // 遍历到下个节点
            curr = nextTemp;
        }
        return prev;
    }


    /**
     * 时间复杂度：O(n)，假设 nn 是列表的长度，那么时间复杂度为 O(n)。
     * 空间复杂度：O(n)，由于使用递归，将会使用隐式栈空间。递归深度可能会达到 nn 层。
     *
     * @param head
     * @return
     */
    public static ListNode<Integer> reverseRecursive(ListNode<Integer> head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode<Integer> newHead = reverseRecursive(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    private static void print(ListNode<Integer> head) {
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    public static void main(String[] args) {
        int size = 5;
        ListNode<Integer> head = null;
        ListNode<Integer> node = null;
        for (int i = 1; i < size; i++) {
            if (node == null) {
                node = new ListNode<>(i);
                head = node;
            } else {
                node = node.next;
            }
            node.next = new ListNode<Integer>(i + 1);
        }
        print(head);
        System.out.println();
        ListNode<Integer> integerListNode = reverseIterate(head);
        print(integerListNode);
    }

}