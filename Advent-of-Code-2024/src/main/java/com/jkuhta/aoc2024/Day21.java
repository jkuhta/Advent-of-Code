package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.FileUtils;
import main.java.com.jkuhta.aoc2024.utils.Point;

import java.io.IOException;
import java.util.*;

public class Day21 {
    static final char[][] NUMERIC = {
            {'7', '8', '9'},
            {'4', '5', '6'},
            {'1', '2', '3'},
            {' ', '0', 'A'}
    };
    static final char[][] DIRECTIONAL = {
            {' ', '^', 'A'},
            {'<', 'v', '>'}
    };

    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("21");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static long solvePart1(String input) {

        List<String> lines = FileUtils.readLines(input);
        Map<Character, Map<Character, List<String>>> numericDict = buildDict(NUMERIC);
        Map<Character, Map<Character, List<String>>> directionalDict = buildDict(DIRECTIONAL);

        long complexity = 0;
        int numRobots = 2;

        for (String line : lines) {
            complexity += getLineComplexity(line, numericDict, directionalDict, numRobots);
        }

        return complexity;
    }


    public static long solvePart2(String input) {


        List<String> lines = FileUtils.readLines(input);
        Map<Character, Map<Character, List<String>>> numericDict = buildDict(NUMERIC);
        Map<Character, Map<Character, List<String>>> directionalDict = buildDict(DIRECTIONAL);

        long complexity = 0;
        int numRobots = 25;

        for (String line : lines) {
            complexity += getLineComplexity(line, numericDict, directionalDict, numRobots);
        }

        return complexity;
    }

    private static long getLineComplexity(String line,
                                          Map<Character, Map<Character, List<String>>> numericDict,
                                          Map<Character, Map<Character, List<String>>> directionalDict,
                                          int numRobots
    ) {

        Map<Character, Map<Character, Map<Integer, Long>>> memo = new HashMap<>();

        long shortestLengthGlobal = 0;
        char start = 'A';

        for (int j = 0; j < line.length(); j++) {
            char end = line.charAt(j);
            long shortestLengthLocal = Long.MAX_VALUE;

            List<String> possibleSequences = numericDict.get(start).get(end);

            for (String sequence : possibleSequences) {
                shortestLengthLocal = Math.min(shortestLengthLocal, findShortestSequence(sequence, directionalDict, 0, numRobots, memo));
            }

            shortestLengthGlobal += shortestLengthLocal;

            start = end;
        }

        int numericPart = Integer.parseInt(line.replaceAll("\\D", ""));
//        System.out.println(line + " : " + shortestLengthGlobal + " | " + numericPart);
//        System.out.println("---------------------------------------");
        return shortestLengthGlobal * numericPart;
    }

    private static Long findShortestSequence(String line,
                                             Map<Character, Map<Character, List<String>>> directionalDict,
                                             int currentRobot,
                                             int numRobots,
                                             Map<Character, Map<Character, Map<Integer, Long>>> memo) {

        if (currentRobot == numRobots) return (long) line.length();

        char start = 'A';
        long shortestLengthGlobal = 0;


        for (int i = 0; i < line.length(); i++) {
            long shortestLengthLocal = Long.MAX_VALUE;
            char end = line.charAt(i);

            List<String> possibleSequences = directionalDict.get(start).get(end);

            if (memo.containsKey(start) && memo.get(start).containsKey(end) && memo.get(start).get(end).containsKey(currentRobot)) {
                shortestLengthLocal = memo.get(start).get(end).get(currentRobot);
            } else {
                for (String possibleSequence : possibleSequences) {
                    shortestLengthLocal = Math.min(shortestLengthLocal, findShortestSequence(possibleSequence, directionalDict, currentRobot + 1, numRobots, memo));
                }
                if (!memo.containsKey(start)) memo.put(start, new HashMap<>());
                if (!memo.get(start).containsKey(end)) memo.get(start).put(end, new HashMap<>());
                memo.get(start).get(end).put(currentRobot, shortestLengthLocal);
            }

            shortestLengthGlobal += shortestLengthLocal;
            start = end;
        }

        return shortestLengthGlobal;
    }


    private static Map<Character, Map<Character, List<String>>> buildDict(char[][] keypad) {
        Map<Character, Map<Character, List<String>>> dict = new HashMap<>();

        for (int i = 0; i < keypad.length; i++) {
            for (int j = 0; j < keypad[i].length; j++) {
                Point start = new Point(i, j);
                Map<Character, List<String>> dict2 = new HashMap<>();

                for (int k = 0; k < keypad.length; k++) {
                    for (int l = 0; l < keypad[k].length; l++) {
                        if (keypad[k][l] == ' ') continue;
                        Point end = new Point(k, l);
                        List<String> shortestPath = findShortestPath(keypad, start, end);
                        dict2.put(keypad[k][l], shortestPath);
                    }
                }
                if (keypad[i][j] != ' ') dict.put(keypad[i][j], dict2);
            }
        }

        return dict;
    }

    public static List<String> findShortestPath(char[][] grid, Point start, Point end) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        Queue<Point> queue = new LinkedList<>();

        List<String> sequences = new ArrayList<>();

        char[] directions = {'>', '^', 'v', '<'};

        queue.add(new Point(start.getX(), start.getY(), 0, ""));

        int[] dX = {0, -1, 1, 0};
        int[] dY = {1, 0, 0, -1};

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            int x = current.getX();
            int y = current.getY();
            int distance = current.getValue();

            if (grid[x][y] == ' ') continue;

            if (x == end.getX() && y == end.getY()) {
                sequences.add(current.getLabelString() + 'A');
            }

            visited[x][y] = true;

            for (int i = 0; i < 4; i++) {
                int newX = x + dX[i];
                int newY = y + dY[i];

                if (newX >= 0 && newX < grid.length && newY >= 0 && newY < grid[0].length && !visited[newX][newY]) {
                    Point newPoint = new Point(newX, newY, distance + 1, current.getLabelString() + directions[i]);
                    queue.add(newPoint);
                }
            }
        }
        return sequences;
    }
}