package org.coastline.one.common;


import org.coastline.one.common.structure.ListNode;

import java.util.*;

/**
 * @author Jay.H.Zou
 * @date 2022/8/24
 */
public class Solution {

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 存储顶点的关系
        List<Set<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new HashSet<>());
        }
        int[] vertexFromCount = new int[numCourses];
        for (int[] prerequisite : prerequisites) {
            // 构建顶点指向，原本 [1, 2], 构建为：2 -> 1
            graph.get(prerequisite[1]).add(prerequisite[0]);
            // 统计顶点上游的个数
            vertexFromCount[prerequisite[0]]++;
        }
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (vertexFromCount[i] == 0) {
                queue.add(i);
            }
        }
        int index = 0;
        while (!queue.isEmpty()) {
            int course = queue.removeFirst();
            index++;
            // 清理图数据和统计数据
            Set<Integer> nextCourseSet = graph.get(course);
            for (int nextCourse : nextCourseSet) {
                // 当前课程学习结束，所以下游课程的来源都 -1
                vertexFromCount[nextCourse]--;
                if (vertexFromCount[nextCourse] == 0) {
                    queue.add(nextCourse);
                }
            }
        }
        return index == numCourses;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        // System.out.println(solution.maximalRectangle(new char[][]{{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '0', '0', '1', '0'}}));
    }
}
