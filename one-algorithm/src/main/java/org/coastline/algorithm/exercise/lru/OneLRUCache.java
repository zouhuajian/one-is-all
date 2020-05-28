package org.coastline.algorithm.exercise.lru;

import org.coastline.algorithm.exercise.lru.entity.Node;

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

    public void remove(String key) {
        Node node = hashMap.get(key);
        removeNode(node);
        hashMap.remove(key);
    }

    /**
     * 刷新被访问节点的位置
     *
     * @param node
     */
    private void refreshNode(Node node) {
        if (node == null) {
            return;
        }
        removeNode(node);
        addNode(node);
    }

    /**
     * 添加节点
     *
     * @param node
     */
    private void addNode(Node node) {
        if (end != null) {
            end.next = node;
            node.pre = end;
            node.next = null;
        }
        end = node;
        if (head == null) {
            head = node;
        }
    }

    /**
     * 删除节点
     *
     * @param node
     * @return
     */
    private String removeNode(Node node) {
        if (node == head && node == end) {
            head = null;
            end = null;
        } else if (node == end) {
            // 断开 end node 的链
            end = end.pre;
            end.next = null;
        } else if (node == head) {
            head = head.next;
            head.pre = null;
        } else {
            // 中间节点
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }
        return node.key;
    }

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
    }
}


