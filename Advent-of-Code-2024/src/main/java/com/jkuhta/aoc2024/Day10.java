package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.FileUtils;
import main.java.com.jkuhta.aoc2024.utils.Point;

import java.io.IOException;
import java.util.*;

public class Day10 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("10");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static int solvePart1(String input) {

        int[][] table = FileUtils.read2DIntGrid(input);
        Set<Point> trailHeads = new HashSet<>();

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                if (table[i][j] == 0) trailHeads.add(new Point(i, j, 0));
            }
        }

        int trailtopsReachedCount = 0;

        for (Point trailHead : trailHeads) {
            trailtopsReachedCount += dfs(table, trailHead);
        }

        return trailtopsReachedCount;
    }

    public static int solvePart2(String input) {
        int[][] table = FileUtils.read2DIntGrid(input);
        Set<Point> trailHeads = new HashSet<>();

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                if (table[i][j] == 0) trailHeads.add(new Point(i, j, 0));
            }
        }

        int ratingTotal = 0;

        for (Point trailHead : trailHeads) {
            ratingTotal += dfs2(table, trailHead);
        }

        return ratingTotal;
    }

    public static int dfs(int[][] table, Point trailHead) {
        Stack<Point> stack = new Stack<>();
        Set<Point> visited = new HashSet<>();
        Set<Point> trailtopsReached = new HashSet<>();

        stack.add(trailHead);
        visited.add(trailHead);

        while (!stack.isEmpty()) {
            Point point = stack.pop();

            if (point.getValue() == 9) {
                trailtopsReached.add(point);
                continue;
            }

            for (Point neighbor : getNeighbors(table, point)) {
                if (!visited.contains(neighbor)) {
                    stack.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return trailtopsReached.size();
    }

    public static int dfs2(int[][] table, Point trailHead) {
        Stack<Point> stack = new Stack<>();
        Set<Point> visited = new HashSet<>();

        int rating = 0;
        stack.add(trailHead);
        visited.add(trailHead);

        while (!stack.isEmpty()) {
            Point point = stack.pop();

            if (point.getValue() == 9) {
                visited = new HashSet<>();
                rating += 1;
                continue;
            }

            for (Point neighbor : getNeighbors(table, point)) {
                if (!visited.contains(neighbor)) {
                    stack.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return rating;
    }

    public static List<Point> getNeighbors(int[][] table, Point point) {
        List<Point> neighbors = new ArrayList<>();
        int x = point.getX();
        int y = point.getY();
        int height = point.getValue();

        if (x > 0 && table[x - 1][y] - height == 1) {
            neighbors.add(new Point(x - 1, y, table[x - 1][y]));
        }
        if (x < table.length - 1 && table[x + 1][y] - height == 1) {
            neighbors.add(new Point(x + 1, y, table[x + 1][y]));
        }
        if (y > 0 && table[x][y - 1] - height == 1) {
            neighbors.add(new Point(x, y - 1, table[x][y - 1]));
        }
        if (y < table[0].length - 1 && table[x][y + 1] - height == 1) {
            neighbors.add(new Point(x, y + 1, table[x][y + 1]));
        }

        return neighbors;
    }
}
