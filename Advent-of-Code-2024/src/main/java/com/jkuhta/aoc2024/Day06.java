package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.CommonUtils;
import main.java.com.jkuhta.aoc2024.utils.FileUtils;
import main.java.com.jkuhta.aoc2024.utils.Point;

import java.io.IOException;
import java.util.ArrayList;
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
        Point start = findCurrentPosition(grid, directions);

        int direction = directions.indexOf(start.getLabel());
        start.setValue(direction);

        List<Point> visited = moveRecursive(start, grid, new ArrayList<>());
        return new HashSet<>(visited).size();
    }

    public static int solvePart2(String input) {
        char[][] grid = FileUtils.read2DCharGrid(input);
        List<Character> directions = List.of('^', '>', 'v', '<');
        Point start = findCurrentPosition(grid, directions);

        int direction = directions.indexOf(start.getLabel());
        start.setValue(direction);

        List<Point> visited = moveRecursive(start, grid, new ArrayList<>());

        Set<Point> obstructions = new HashSet<>();

        for (Point obstruction : visited) {

            if (!CommonUtils.isOutOfBounds(obstruction.getX(), obstruction.getY(), grid) && grid[obstruction.getX()][obstruction.getY()] != '#' && grid[obstruction.getX()][obstruction.getY()] != '^') {
                grid[obstruction.getX()][obstruction.getY()] = 'O';
                start.setValue(direction);

                if (isLoop(start, grid, new ArrayList<>())) {
                    obstructions.add(obstruction);
                }
                grid[obstruction.getX()][obstruction.getY()] = '.';
            }
        }

        return obstructions.size();
    }

    public static List<Point> moveRecursive(Point position, char[][] grid, List<Point> visited) {

        List<Point> newVisited = new ArrayList<>(visited);
        newVisited.add(position);

        Point nextPosition = nextPosition(position);

        if (CommonUtils.isOutOfBounds(nextPosition.getX(), nextPosition.getY(), grid)) {
            return newVisited;
        }

        while (grid[nextPosition.getX()][nextPosition.getY()] == '#') {
            position.setValue((position.getValue() + 1) % 4);
            nextPosition = nextPosition(position);
        }
        return moveRecursive(nextPosition, grid, newVisited);
    }

    public static boolean isLoop(Point position, char[][] grid, List<Point> visited) {

        Point nextPosition = nextPosition(position);

        if (CommonUtils.isOutOfBounds(nextPosition.getX(), nextPosition.getY(), grid)) {
            return false;
        }

        if (visited.contains(nextPosition) && visited.get(visited.indexOf(nextPosition)).getValue() == nextPosition.getValue()) {
            return true;
        }

        List<Point> newVisited = new ArrayList<>(visited);
        newVisited.add(nextPosition);

        while (grid[nextPosition.getX()][nextPosition.getY()] == '#' || grid[nextPosition.getX()][nextPosition.getY()] == 'O') {
            position.setValue((position.getValue() + 1) % 4);
            nextPosition = nextPosition(position);
        }
        return isLoop(nextPosition, grid, newVisited);
    }

    private static Point findCurrentPosition(char[][] grid, List<Character> targets) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (targets.contains(grid[i][j])) {
                    return new Point(i, j, grid[i][j]);
                }
            }
        }
        return null;
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
}
