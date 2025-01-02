package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.FileUtils;
import main.java.com.jkuhta.aoc2024.utils.Point;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Day20 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("20");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static int solvePart1(String input) {
        String[] lines = FileUtils.readLines(input).toArray(new String[0]);
        boolean[][] grid = new boolean[lines.length][lines[0].length()];

        Point start = null, end = null;

        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[0].length(); j++) {
                if (lines[i].charAt(j) == 'S') start = new Point(i, j, 'S');
                else if (lines[i].charAt(j) == 'E') end = new Point(i, j, 'E');
                grid[i][j] = lines[i].charAt(j) != '#';
            }
        }

        int maxTime = findShortestPath(grid, start, end, Integer.MAX_VALUE);

        int count = 0;

        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[0].length - 1; j++) {
                if (!grid[i][j]) {
                    grid[i][j] = true;
                    count += maxTime - findShortestPath(grid, start, end, maxTime) >= 100 ? 1 : 0;
                    grid[i][j] = false;
                }
            }
        }
        return count;
    }

    public static int solvePart2(String input) {
        return 0;
    }

    public static int findShortestPath(boolean[][] grid, Point start, Point end, int maxTime) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        Queue<int[]> queue = new LinkedList<>();

        queue.add(new int[]{start.getX(), start.getY(), 0});

        int[] dX = {-1, 1, 0, 0};
        int[] dY = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            int distance = current[2];

            if (x == end.getX() && y == end.getY() && distance < maxTime) {
                return distance;
            } else if (distance >= maxTime) {
                return Integer.MAX_VALUE;
            }

            visited[x][y] = true;

            for (int i = 0; i < 4; i++) {
                int newX = x + dX[i];
                int newY = y + dY[i];

                if (newX > 0 && newX < grid.length - 1 && newY > 0 && newY < grid[0].length - 1 &&
                        grid[newX][newY] && !visited[newX][newY]) {
                    queue.add(new int[]{newX, newY, distance + 1});
//                    visited[newX][newY] = true;
                }
            }
        }


        return -1;
    }
}
