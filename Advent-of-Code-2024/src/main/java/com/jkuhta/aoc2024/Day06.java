package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.CommonUtils;
import main.java.com.jkuhta.aoc2024.utils.FileUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day06 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("06");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static int solvePart1(String input) {
        char[][] grid = FileUtils.read2DCharGrid(input);
        List<Character> directions = List.of('^', '>', 'v', '<');
        int[] position = findCurrentPosition(grid, directions);
        int i = position[0];
        int j = position[1];

        int direction = directions.indexOf(grid[i][j]);

        moveRecursive(i, j, grid, direction);

        return countChars(grid);
    }

    public static int solvePart2(String input) {
        char[][] grid = FileUtils.read2DCharGrid(input);
        List<Character> directions = List.of('^', '>', 'v', '<');
        int[] position = findCurrentPosition(grid, directions);
        int i = position[0];
        int j = position[1];

        int direction = directions.indexOf(grid[i][j]);
        Set<String> obstructions = new HashSet<>();
        int obstructionCount = moveRecursive2(i, j, grid, direction, new int[0]);

        return obstructionCount;
    }

    public static void moveRecursive(int i, int j, char[][] grid, int direction) {

        grid[i][j] = 'X';

        int[] nextPosition = nextPosition(i, j, direction);
        int nextI = nextPosition[0];
        int nextJ = nextPosition[1];

        if (CommonUtils.isOutOfBounds(nextI, nextJ, grid)) {
            return;
        }

        if (grid[nextI][nextJ] == '#') {
            moveRecursive(i, j, grid, (direction + 1) % 4);
        } else {
            moveRecursive(nextI, nextJ, grid, direction);
        }
    }

    public static int moveRecursive2(int i, int j, char[][] grid, int direction, int[] obstruction) {


        grid[i][j] = 'X';

        int[] nextPosition = nextPosition(i, j, direction);
        int nextI = nextPosition[0];
        int nextJ = nextPosition[1];

        if (CommonUtils.isOutOfBounds(nextI, nextJ, grid)) {
            return 0;
        }

        if (obstruction.length > 0 && nextI == obstruction[0] && nextJ == obstruction[1]) {
            return 1;
        }

        if (grid[nextI][nextJ] == '#') {
            return moveRecursive2(i, j, grid, (direction + 1) % 4, obstruction);
        } else {
            if (obstruction.length > 0) {
                return moveRecursive2(nextI, nextJ, grid, direction, obstruction);
            } else if (grid[nextI][nextJ] == 'X') {
                return moveRecursive2(nextI, nextJ, grid, direction, obstruction);
            } else {
                int[] newObstruction = new int[]{nextI, nextJ};
                return moveRecursive2(nextI, nextJ, grid, direction, obstruction) + moveRecursive2(i, j, grid, (direction + 1) % 4, newObstruction);
            }
        }
    }

    private static int[] findCurrentPosition(char[][] grid, List<Character> targets) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (targets.contains(grid[i][j])) {
                    return new int[]
                            {i, j};
                }
            }
        }
        return new int[0];
    }

    private static int countChars(char[][] grid) {
        int count = 0;
        for (char[] row : grid) {
            for (char cell : row) {
                if (cell == 'X') {
                    count++;
                }
            }
        }
        return count;
    }


    private static int[] nextPosition(int i, int j, int direction) {
        int nextI = i;
        int nextJ = j;

        if ((direction == 0)) {
            nextI--;
        } else if ((direction == 1)) {
            nextJ++;
        } else if ((direction == 2)) {
            nextI++;
        } else if ((direction == 3)) {
            nextJ--;
        }

        return new int[]{nextI, nextJ};
    }

    private static String encodeCoordinate(int x, int y) {
        return x + "," + y;
    }
}
