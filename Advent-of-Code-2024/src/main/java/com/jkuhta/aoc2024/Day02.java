package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.FileUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Day02 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("02");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static int solvePart1(String input) {

        List<String> lines = FileUtils.readLines(input);

        int safeCount = 0;


        for (String line: lines) {

            List<Integer> parts = new ArrayList<>(Arrays.stream(line.split(" ")).map(Integer::parseInt).toList());

            if (isSafe(parts)) {
                safeCount += 1;
            }

        }
        return safeCount;

    }

    public static int solvePart2(String input) {
        List<String> lines = FileUtils.readLines(input);

        int safeCount = 0;

        for (String line: lines) {

            List<Integer> parts = new ArrayList<>(Arrays.stream(line.split(" ")).map(Integer::parseInt).toList());

                for (int i = -1; i < parts.size(); i++) {

                    List<Integer> subList = new ArrayList<>(parts);

                    if (i >= 0) subList.remove(i);

                    if (isSafe(subList)) {
                        safeCount += 1;
                        break;
                    }
                }
        }
        return safeCount;
    }

    public static boolean isSafe(List<Integer> list) {
        boolean isStable = true;
        boolean isAscending = true;
        boolean isDescending = true;

        Set<Integer> set = new HashSet<>(list);

        if (set.size() != list.size()) return false;

        for (int i = 1; i < list.size(); i++) {

            if (Math.abs(list.get(i) - list.get(i - 1)) > 3) {
                return false;
            }

            if (list.get(i) < list.get(i - 1)) {
                isAscending = false;
            }

            if (list.get(i) > list.get(i - 1)) {
                isDescending = false;
            }
        }

        if ((isAscending || isDescending) && isStable) {
            return true;
        }
        return false;
    }
}
