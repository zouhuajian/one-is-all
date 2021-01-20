package org.coastline.common;

import net.bytebuddy.agent.ByteBuddyAgent;

import java.lang.instrument.Instrumentation;

/**
 * @author zouhuajian
 * @date 2021/1/20
 */
public class ByteBuddyTest {
    public static void main(String[] args) {
        Instrumentation instrumentation = ByteBuddyAgent.install();

    }
}
