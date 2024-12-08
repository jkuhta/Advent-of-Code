package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.FileUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day07 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("07");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static long solvePart1(String input) {

        List<String> lines = FileUtils.readLines(input);

        long totalCalibrationResult = 0;

        for (String line : lines) {
            String[] parts = line.split(": ");
            long leftSide = Long.parseLong(parts[0]);
            int[] values = Arrays.stream(parts[1].split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            totalCalibrationResult += calculateEquation(1, values, values[0], leftSide) ? leftSide : 0;

        }
        return totalCalibrationResult;
    }

    public static long solvePart2(String input) {
        List<String> lines = FileUtils.readLines(input);

        long totalCalibrationResult = 0;

        for (String line : lines) {
            String[] parts = line.split(": ");
            long leftSide = Long.parseLong(parts[0]);
            int[] values = Arrays.stream(parts[1].split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            totalCalibrationResult += calculateEquation2(1, values, values[0], leftSide) ? leftSide : 0;
        }
        return totalCalibrationResult;
    }


    private static boolean calculateEquation(int i, int[] values, long result, long leftSide) {

        if (result == leftSide) return true;
        else if (result > leftSide || i >= values.length) return false;
        else
            return calculateEquation(i + 1, values, result + values[i], leftSide) || calculateEquation(i + 1, values, result * values[i], leftSide);
    }

    private static boolean calculateEquation2(int i, int[] values, long result, long leftSide) {
        if (result == leftSide) return true;
        else if (i >= values.length) return false;
        else {
            long concatenated = Long.parseLong(result + "" + values[i]);
            return calculateEquation2(i + 1, values, result + values[i], leftSide) ||
                    calculateEquation2(i + 1, values, result * values[i], leftSide) ||
                    calculateEquation2(i + 1, values, concatenated, leftSide);
        }
    }
}
