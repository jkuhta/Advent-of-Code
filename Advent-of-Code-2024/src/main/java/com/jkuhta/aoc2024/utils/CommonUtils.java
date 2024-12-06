package main.java.com.jkuhta.aoc2024.utils;

public class CommonUtils {

    public static boolean isOutOfBounds(int i, int j, char[][] grid) {
        return i < 0 || i >= grid.length || j < 0 || j >= (grid[i] != null ? grid[i].length : 0);
    }
}
