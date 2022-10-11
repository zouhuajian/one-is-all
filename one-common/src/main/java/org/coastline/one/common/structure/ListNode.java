package org.coastline.one.common.structure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jay.H.Zou
 * @date 2022/6/28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

    public static ListNode create(int[] arr) {
        ListNode dummy = new ListNode();
        ListNode tail = dummy;
        for (int i : arr) {
            tail.next = new ListNode(i);
            tail = tail.next;
        }
        return dummy.next;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        ListNode node = this;
        while (node != null) {
            builder.append(node.val);
            if (node.next != null) {
                builder.append("->");
            }
            node = node.next;
        }
        return builder.toString();
    }
}
