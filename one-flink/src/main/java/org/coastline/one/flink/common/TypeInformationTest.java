package org.coastline.one.flink.common;

import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;

/**
 * @author Jay.H.Zou
 * @date 2022/7/20
 */
public class TypeInformationTest<T> {
    private T key;

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }


    public static void main(String[] args) {

        TypeInformation<TypeInformationTest<String>> information = TypeInformation.of(new TypeHint<TypeInformationTest<String>>() {
        });
        System.out.println(information);
        System.out.println(TypeInformation.of(TypeInformationTest.class));
    }
}
