package org.coastline.one.common.java.reflect;

import org.coastline.one.common.model.User;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2022/1/29
 */
public class TestReflect {

    public static void main(String[] args) {
        User user = User.builder()
                .name("jay")
                .age(22)
                .sex(1)
                .build();
        Class<?> clazz = User.class;
        // 获取当前对象的所有属性字段
        // clazz.getFields()：获取public修饰的字段
        // clazz.getDeclaredFields()： 获取所有的字段包括private修饰的字段
        List<Field> allFields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        allFields.forEach(field -> {
            // 设置字段可访问， 否则无法访问private修饰的变量值
            field.setAccessible(true);
            try {
                // 获取字段名称
                String fieldName = field.getName();
                // 获取指定对象的当前字段的值
                Object fieldVal = field.get(user);
                System.out.println(fieldName + "=" + fieldVal);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

}
