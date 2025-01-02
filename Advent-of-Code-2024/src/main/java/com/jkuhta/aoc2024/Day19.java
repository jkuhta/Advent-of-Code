package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.FileUtils;

import java.io.IOException;
import java.util.*;

public class Day19 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("19");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static int solvePart1(String input) {

        List<String> lines = FileUtils.readLines(input);
        SortedSet<String> towels = new TreeSet<>(Comparator.comparingInt(String::length)
                .thenComparing(Comparator.naturalOrder()));
        towels.addAll(List.of(lines.getFirst().split(", ")));

        int count = 0;

        for (int i = 2; i < lines.size(); i++) {
            count += isDesignPossible(towels, lines.get(i)) ? 1 : 0;
        }

        return count;
    }

    public static int solvePart2(String input) {

        List<String> lines = FileUtils.readLines(input);
        SortedSet<String> towels = new TreeSet<>(Comparator.comparingInt(String::length)
                .thenComparing(Comparator.naturalOrder()));
        towels.addAll(List.of(lines.getFirst().split(", ")));

        Map<String, Integer> subDesignArrangements = new HashMap<>();

        int count = 0;

        for (int i = 2; i < lines.size(); i++) {
            count += findAllArrangements(towels, lines.get(i), subDesignArrangements);
        }

        return count;
    }

    public static boolean isDesignPossible(SortedSet<String> towels, String line) {

        if (line.isEmpty()) return true;
        boolean isPossible;

        for (int i = 1; i <= line.length(); i++) {
            String subDesign = line.substring(0, i);
            if (towels.contains(subDesign)) {
                String substring = line.substring(i);
                isPossible = isDesignPossible(towels, substring);

                if (isPossible) {
                    return true;
                }
            }
        }
        return false;

    }

    public static int findAllArrangements(SortedSet<String> towels, String line, Map<String, Integer> subDesignArrangements) {

        if (subDesignArrangements.containsKey(line)) return subDesignArrangements.get(line);
        if (line.isEmpty()) {
            System.out.println();
            return 1;
        }

        int count = 0;

        for (int i = 1; i <= line.length(); i++) {
            String subDesign = line.substring(0, i);
            if (towels.contains(subDesign)) {
                String substring = line.substring(i);
                System.out.println(subDesign);
                count += findAllArrangements(towels, substring, subDesignArrangements);

                if (count > 0) subDesignArrangements.put(subDesign, count);
            }
        }
        return count;
    }


}
