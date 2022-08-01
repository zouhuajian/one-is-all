package org.coastline.one.flink.common;

import org.apache.flink.api.common.typeinfo.TypeInformation;

/**
 * @author Jay.H.Zou
 * @date 2022/7/20
 */
public class TypeTest {

    public static void main(String[] args) {
        TypeInformation<OneData> info = TypeInformation.of(OneData.class);
        System.out.println(info);

    }
    class OneData {
        private String name;
    }
}
