package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.CommonUtils;
import main.java.com.jkuhta.aoc2024.utils.FileUtils;
import main.java.com.jkuhta.aoc2024.utils.Point;

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
        Point position = findCurrentPosition(grid, directions);

        int direction = directions.indexOf(position.getLabel());
        position.setValue(direction);

        moveRecursive(position, grid);

        return countChars(grid);
    }

    public static int solvePart2(String input) {
        char[][] grid = FileUtils.read2DCharGrid(input);
        List<Character> directions = List.of('^', '>', 'v', '<');
        Point position = findCurrentPosition(grid, directions);

        int direction = directions.indexOf(position.getLabel());
        position.setValue(direction);

        Set<Point> visited = new HashSet<>();
        int obstructionCount = moveRecursive2(position, grid, null, visited);

        return obstructionCount;
    }

    public static void moveRecursive(Point position, char[][] grid) {

        grid[position.getX()][position.getY()] = 'X';

        Point nextPosition = nextPosition(position);
        int nextX = nextPosition.getX();
        int nextY = nextPosition.getY();

        if (CommonUtils.isOutOfBounds(nextX, nextY, grid)) {
            return;
        }

        if (grid[nextX][nextY] == '#') {
            position.setValue((position.getValue() + 1) % 4);
            moveRecursive(position, grid);
        } else {
            moveRecursive(nextPosition, grid);
        }
    }

    public static int moveRecursive2(Point position, char[][] grid, Point obstruction, Set<Point> visited) {


        grid[position.getX()][position.getY()] = 'X';
        visited.add(position);

        Point nextPosition = nextPosition(position);
        int nextX = nextPosition.getX();
        int nextY = nextPosition.getY();

        if (CommonUtils.isOutOfBounds(nextX, nextY, grid)) {
            return 0;
        }

        Point finalNextPosition = nextPosition;
        if (visited.stream().anyMatch(value -> value.equals(finalNextPosition) && value.getValue() == finalNextPosition.getValue())) {
            return 1;
        }

        if (grid[nextX][nextY] == '#' || obstruction != null && obstruction.equals(nextPosition)) {
            nextPosition = new Point(nextX, nextY, (position.getValue() + 1) % 4);
            return moveRecursive2(nextPosition, grid, obstruction, visited);
        } else {
            if (obstruction != null ) {
                return moveRecursive2(nextPosition, grid, obstruction, visited);
            } else {
                Point newObstruction = new Point(nextX, nextY, '0');
                Point position2 = new Point(position.getX(), position.getY(), (position.getLabel() + 1) % 4);
                return moveRecursive2(nextPosition, grid, obstruction, visited) + moveRecursive2(position2, grid, newObstruction, visited);
            }
        }
    }

    private static Point findCurrentPosition(char[][] grid, List<Character> targets) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (targets.contains(grid[i][j])) {
                    return new Point(i,j, grid[i][j]);
                }
            }
        }
        return null;
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


    private static Point nextPosition(Point point) {
        int nextX = point.getX();
        int nextY = point.getY();
        int direction = point.getValue();

        if ((direction == 0)) {
            nextX--;
        } else if ((direction == 1)) {
            nextY++;
        } else if ((direction == 2)) {
            nextX++;
        } else if ((direction == 3)) {
            nextY--;
        }

        return new Point(nextX, nextY, direction);
    }

    private static String encodeCoordinate(int x, int y) {
        return x + "," + y;
    }
}
