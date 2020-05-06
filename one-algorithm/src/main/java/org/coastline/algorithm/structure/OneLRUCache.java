package org.coastline.algorithm.structure;

import java.util.HashMap;

/**
 * Least Recently Used
 *
 * @author Jay.H.Zou
 * @date 2020/5/6
 */
public class OneLRUCache {

    private Node head;

    private Node end;

    private int limit;

    private HashMap<String, Node> hashMap;

    public OneLRUCache(int limit) {
        this.limit = limit;
        hashMap = new HashMap<>(limit);
    }

    public String get(String key) {
        Node node = hashMap.get(key);
        if (node == null) {
            return null;
        }
        // 刷新结构
        refreshNode(node);
        return node.value;
    }

    public void put(String key, String value) {
        Node node = hashMap.get(key);
        if (node == null) {
            if (hashMap.size() >= limit) {
                // 移除
                String oldKey = removeNode(head);
                hashMap.remove(oldKey);
            }
            node = new Node(key, value);
            addNode(node);
            hashMap.put(key, node);
        } else {
            node.value = value;
            refreshNode(node);
        }
    }

    private void addNode(Node node) {
    }

    private void refreshNode(Node node) {

    }

    private String removeNode(Node head) {
        return head.key;
    }
}

class Node {

    public Node pre;

    public Node next;

    public String key;

    public String value;

    Node(String key, String value) {
        this.key = key;
        this.value = value;
    }
}