package org.coastline.one.common;


import org.coastline.one.common.structure.ListNode;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Jay.H.Zou
 * @date 2022/8/24
 */
public class Solution {


    public static void main(String[] args) {
        String spanId = "1.1^1.3|d5be3ffa4f702627";
        String lastSplitSignal = spanId.lastIndexOf(".") > spanId.lastIndexOf("^") ?
                "." : "^";
        String lastNumber = spanId.substring(spanId.lastIndexOf(lastSplitSignal), spanId.lastIndexOf("|"));
        System.out.println(lastNumber);
    }
}
