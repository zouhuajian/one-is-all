package org.coastline.algorithm.exercise.lru.entity;

/**
 * @author Jay.H.Zou
 * @date 2020/5/7
 */
public class Node {

    public Node pre;

    public Node next;

    public String key;

    public String value;

    public Node(String key, String value) {
        this.key = key;
        this.value = value;
    }
}