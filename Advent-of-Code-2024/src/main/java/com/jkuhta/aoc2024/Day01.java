package main.java.com.jkuhta.aoc2024;


import main.java.com.jkuhta.aoc2024.utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day01 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("01");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));

    }

    public static int solvePart1(String input) {

        List<String> lines = FileUtils.readLines(input);

        List<Integer> leftColumn = new ArrayList<>();
        List<Integer> rightColumn = new ArrayList<>();

        for (String line : lines) {
            String[] words = line.split(" {3}");
            leftColumn.add(Integer.parseInt(words[0]));
            rightColumn.add(Integer.parseInt(words[1]));
        }

        leftColumn.sort(Integer::compareTo);
        rightColumn.sort(Integer::compareTo);

        int totalDistance = 0;

        for (int i = 0; i < leftColumn.size(); i++) {
            Integer firstElement = leftColumn.get(i);
            Integer secondElement = rightColumn.get(i);

            totalDistance += Math.abs(firstElement - secondElement);
        }

        return totalDistance;
    }

    public static int solvePart2(String input) {

        List<String> lines = FileUtils.readLines(input);

        List<Integer> firstColumn = new ArrayList<>();

        Map<Integer, Integer> rightElement2count = new HashMap<>();

        for (String line : lines) {
            String[] words = line.split(" {3}");

            int leftElement = Integer.parseInt(words[0]);
            int rightElement = Integer.parseInt(words[1]);

            firstColumn.add(leftElement);

            rightElement2count.put(rightElement, rightElement2count.getOrDefault(rightElement, 0) + 1);
        }

        int similarity = 0;

        for (Integer firstElement : firstColumn) {
            similarity += firstElement * rightElement2count.getOrDefault(firstElement, 0);
        }

        return similarity;
    }
}