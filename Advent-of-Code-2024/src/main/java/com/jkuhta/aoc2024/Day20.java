package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.FileUtils;
import main.java.com.jkuhta.aoc2024.utils.Node;
import main.java.com.jkuhta.aoc2024.utils.Point;

import java.io.IOException;
import java.util.*;

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

        List<Node> path = findShortestPath(grid, start, end, Integer.MAX_VALUE);

        return findCheats(2, path, 100);
    }

    public static int solvePart2(String input) {
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

        List<Node> path = findShortestPath(grid, start, end, Integer.MAX_VALUE);

        return findCheats(20, path, 100);

    }

    public static int findCheats(int cheatTime, List<Node> mainPath, int threshold) {
        Map<Integer, Integer> cheatsTime2count = new HashMap<>();

        int numberOfCheats = 0;

        for (int i = 0; i < mainPath.size() - threshold; i++) {
            Node cheatStart = mainPath.get(i);
            for (int j = i + threshold; j < mainPath.size(); j++) {
                Node cheatEnd = mainPath.get(j);
                int manhattanDistance = cheatStart.manhattanDistanceTo(cheatEnd);
                if (manhattanDistance <= cheatTime) {

                    int savedTime = cheatEnd.getDistance() - cheatStart.getDistance() - manhattanDistance;

                    if (savedTime >= threshold) {
//                        System.out.println("S: " + cheatStart + " | E: " + cheatEnd + " | " + savedTime);
                        cheatsTime2count.put(savedTime, cheatsTime2count.getOrDefault(savedTime, 0) + 1);

                    }
                }
            }
        }

        for (Integer key : cheatsTime2count.keySet().stream().sorted().toList()) {
            numberOfCheats += cheatsTime2count.get(key);
        }

        return numberOfCheats;

    }

    public static List<Node> findShortestPath(boolean[][] grid, Point start, Point end, int maxTime) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        Queue<Node> queue = new LinkedList<>();

        queue.add(new Node(start.getX(), start.getY(), 0));

        int[] dX = {-1, 1, 0, 0};
        int[] dY = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int x = current.getX();
            int y = current.getY();
            int distance = current.getDistance();

            if (x == end.getX() && y == end.getY() && distance < maxTime) {
                List<Node> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = current.getPrev();
                }
                return path.reversed();
            } else if (distance >= maxTime) {
                return null;
            }

            visited[x][y] = true;

            for (int i = 0; i < 4; i++) {
                int newX = x + dX[i];
                int newY = y + dY[i];

                if (newX > 0 && newX < grid.length - 1 && newY > 0 && newY < grid[0].length - 1 &&
                        grid[newX][newY] && !visited[newX][newY]) {
                    Node newNode = new Node(newX, newY, distance + 1);
                    newNode.setPrev(current);
                    queue.add(newNode);
                }
            }
        }
        return null;
    }
}
