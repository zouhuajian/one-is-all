package org.coastline.algorithm.exercise.entity;

/**
 * @author Jay.H.Zou
 * @date 2020/4/29
 */
public class ListNode<T> {

    public T val;

    public ListNode(T val) {
        this.val = val;
    }

    public ListNode<T> next;
}
