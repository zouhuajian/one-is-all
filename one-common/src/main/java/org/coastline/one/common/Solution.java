package org.coastline.one.common;


import org.coastline.one.common.structure.ListNode;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Jay.H.Zou
 * @date 2022/8/24
 */
public class Solution {

    int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    int maxArea = 0;

    public int maxAreaOfIsland(int[][] grid) {
        int maxRow = grid.length;
        int maxColumn = grid[0].length;
        boolean[][] visited = new boolean[maxRow][maxColumn];
        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxColumn; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    int area = dfs(grid, visited, i, j);
                    maxArea = Math.max(maxArea, area);
                }
            }
        }
        return maxArea;
    }

    private int dfs(int[][] grid, boolean[][] visited, int row, int column) {
        int maxRow = grid.length;
        int maxColumn = grid[0].length;
        if (grid[row][column] == 0) {
            return 0;
        }
        visited[row][column] = true;
        int area = 1;
        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newColumn = column + direction[1];
            if (newRow >= 0 && newRow < maxRow && newColumn >= 0 && newColumn < maxColumn && !visited[newRow][newColumn]) {
                area += dfs(grid, visited, newRow, newColumn);
            }
        }
        return area;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        // System.out.println(solution.deleteDuplicates(ListNode.create(new int[]{1, 1, 1, 2, 3})));
    }
}
