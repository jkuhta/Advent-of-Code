package main.java.com.jkuhta.aoc2024;


import main.java.com.jkuhta.aoc2024.utils.FileUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day04 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("04");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static int solvePart1(String input) {
        List<String> lines = FileUtils.readLines(input);

        String searchWord = "XMAS";
        int[][] directions = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};

        int allAppearences = 0;

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.getFirst().length(); j++) {

                Map<Integer, Integer> direction2count = new HashMap<>();

                for (int k = 0; k < searchWord.length(); k++) {

                    for (int d = 0; d < directions.length; d++) {
                        int di = directions[d][0];
                        int dj = directions[d][1];
                        if (isCharRelevant(i + k * di, j + k * dj, lines, searchWord.charAt(k))) {
                            direction2count.put(d + 1, direction2count.getOrDefault(d + 1, 0) + 1);
                        }
                    }
                }
                allAppearences += (int) direction2count.values().stream().filter(count -> count == 4).count();
            }
        }

        return allAppearences;
    }

    public static int solvePart2(String input) {
        List<String> lines = FileUtils.readLines(input);

        int allAppearences = 0;

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.getFirst().length(); j++) {
                if (isCharRelevant(i, j, lines, 'A')) {
                    if (isXmas(i, j, lines, 'M', 'M', 'S', 'S') ||
                            isXmas(i, j, lines, 'S', 'S', 'M', 'M') ||
                            isXmas(i, j, lines, 'M', 'S', 'M', 'S') ||
                            isXmas(i, j, lines, 'S', 'M', 'S', 'M')) {
                        allAppearences += 1;
                    }
                }
            }
        }

        return allAppearences;
    }

    public static boolean isOutOfBounds(int i, int j, List<String> table) {
        return i < 0 || i >= table.size() || j < 0 || j >= table.getFirst().length();
    }

    public static boolean isCharRelevant(int i, int j, List<String> table, char c) {
        return !isOutOfBounds(i, j, table) && table.get(i).charAt(j) == c;
    }

    public static boolean isXmas(int i, int j, List<String> table, char... chars) {
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        for (int d = 0; d < directions.length; d++) {
            int di = directions[d][0];
            int dj = directions[d][1];
            if (!isCharRelevant(i + di, j + dj, table, chars[d])) return false;
        }
        return true;
    }
}
