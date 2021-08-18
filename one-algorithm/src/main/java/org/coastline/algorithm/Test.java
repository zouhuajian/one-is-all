package org.coastline.algorithm;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2021/8/9
 */
public class Test {

    public static void main(String[] args) {
        List<Long> list = Lists.newArrayList(9L,8L,7L,6L);
        System.out.println(list.get(0));
        list.remove(9L);
        System.out.println(list.get(0));
        System.out.println(list);
    }
}
