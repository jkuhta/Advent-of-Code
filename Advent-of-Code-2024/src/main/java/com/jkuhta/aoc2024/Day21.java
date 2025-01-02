package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.FileUtils;
import main.java.com.jkuhta.aoc2024.utils.Point;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day21 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("21");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static int solvePart1(String input) {

        List<String> lines = FileUtils.readLines(input);

        char[][] numericKeys = {{'7', '8', '9'}, {'4', '5', '6'}, {'1', '2', '3'}, {' ', 0, 'A'}};
        Set<Point> points = new HashSet<>();

        for (int i = 0; i < numericKeys.length; i++) {
            for (int j = 0; j < numericKeys[0].length; j++) {
                points.add(new Point(i, j, numericKeys[i][j]));
            }
        }

        int sum = 0;
        for (String line : lines) {
            sum += findShortestSequence(line) * getNumericPart(line);
        }
        return sum;
    }

    public static int solvePart2(String input) {
        return 0;
    }

    public static int findShortestSequence(String code) {

        Set<String> numericSequences = getNumericSequences(code);

        return getDirectionalSequence(numericSequence).length;
    }

    public static Set<String> getNumericSequence(String code) {


        return Set.of();
    }

    public static String getDirectionalSequence(String seq) {
        return List.of();
    }

    public static int getNumericPart(String code) {
        return Integer.parseInt(code.split("A")[0]);
    }
}
