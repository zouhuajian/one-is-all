package org.coastline.one.core.test.tool;

import com.google.common.collect.Lists;
import org.coastline.one.core.tool.ArrayStabilityTool;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author Jay.H.Zou
 * @date 2023/4/27
 */
public class TestArrayStabilityTool {

    @Test
    public void testComputeStabilityScore() {
        ArrayList<Integer> arr = Lists.newArrayList(2, 2, 3, 1, 1, 1, 1, 1, 9);
        ArrayStabilityTool.StabilityResult stabilityResult = ArrayStabilityTool.computeStabilityScore(arr, 1.5, 0.8);
        System.out.println(stabilityResult);
    }
}
