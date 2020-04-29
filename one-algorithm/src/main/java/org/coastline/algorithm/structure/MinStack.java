package org.coastline.algorithm.structure;

import java.util.Stack;

/**
 * 功能：出栈，入栈，取最小元素
 * 时间复杂度均为 O(1)
 *
 * @author Jay.H.Zou
 * @date 2020/4/29
 */
public class MinStack {

    Stack<Integer> mainStack = new Stack<>();

    Stack<Integer> minStack = new Stack<>();

    public int pop() {
        // 如果出栈的是最小值，则将 minStack 中的最小值也移除
        if (mainStack.peek().equals(minStack.peek())) {
            minStack.pop();
        }
        // emptyException
        return mainStack.pop();
    }

    public void push(int element) {
        mainStack.push(element);
        if (minStack.isEmpty() || minStack.peek() > element) {
            minStack.push(element);
        }
    }

    public int getMin() throws Exception {
        if (mainStack.isEmpty()) {
            throw new Exception("stack is empty");
        }
        return minStack.peek();
    }
}
