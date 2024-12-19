package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.FileUtils;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

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
        return 0;
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
                    towels.add(substring);
                    return true;
                }
            }
        }
        return false;

    }


}
