package org.coastline.common.util;

import java.util.Random;

/**
 * @author Jay.H.Zou
 * @date 2021/5/4
 */
public class CommonUtil {

    private static final Random random = new Random();

    private CommonUtil(){}
    public static int getRandomInt(int range) {
        return random.nextInt(range);
    }

}
