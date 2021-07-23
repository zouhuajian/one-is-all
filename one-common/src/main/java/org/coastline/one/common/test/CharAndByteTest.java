package org.coastline.one.common.test;

/**
 * @author Jay.H.Zou
 * @date 2021/7/22
 */
public class CharAndByteTest {

    public static void main(String[] args) {
        byte g = -89;   //b对应ASCII是98
        char h = (char) 89;
        char i = 85;    //U对应ASCII是85
        int j = 'h';    //h对应ASCII是104
        System.out.println(g);
        System.out.println(h);
        System.out.println(i);

        System.out.println(j);
    }
}
