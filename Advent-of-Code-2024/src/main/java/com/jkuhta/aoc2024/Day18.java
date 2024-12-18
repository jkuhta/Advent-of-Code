package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.FileUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Day18 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("18");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static int solvePart1(String input) {
        String[] lines = FileUtils.readLines(input).toArray(new String[0]);
        int size = 71;
        boolean[][] obstacles = new boolean[size][size];

        for (int i = 0; i < 1024; i++) {
            String[] parts = lines[i].split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            obstacles[x][y] = true;
        }

        return findShortestPath(obstacles, size);
    }

    public static String solvePart2(String input) {
        String[] lines = FileUtils.readLines(input).toArray(new String[0]);
        int size = 71;
        boolean[][] obstacles = new boolean[size][size];

        for (int i = 0; i < lines.length; i++) {
            String[] parts = lines[i].split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            obstacles[x][y] = true;

            if (findShortestPath(obstacles, size) == -1) {
                return x + "," + y;
            }
        }
        return "";
    }

    public static int findShortestPath(boolean[][] obstacles, int size) {
        boolean[][] visited = new boolean[size][size];
        Queue<int[]> queue = new LinkedList<>();

        queue.add(new int[]{0, 0, 0});

        int[] dX = {-1, 1, 0, 0};
        int[] dY = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            int distance = current[2];

            if (x == size - 1 && y == size - 1) {
                return distance;
            }

            visited[x][y] = true;

            for (int i = 0; i < 4; i++) {
                int newX = x + dX[i];
                int newY = y + dY[i];

                if (newX >= 0 && newX < size && newY >= 0 && newY < size &&
                        !obstacles[newX][newY] && !visited[newX][newY]) {
                    queue.add(new int[]{newX, newY, distance + 1});
                    visited[newX][newY] = true;
                }
            }
        }

        return -1;
    }
}
