package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.FileUtils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("03");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static int solvePart1(String input) {

        String regex = "mul\\((-?\\d{1,3}),(-?\\d{1,3})\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        int sum = 0;
        while (matcher.find()) {
            int left = Integer.parseInt(matcher.group(1));
            int right = Integer.parseInt(matcher.group(2));

            int mul = left * right;
            sum += mul;
        }

        return sum;
    }

    public static int solvePart2(String input) {
        String regex = "mul\\((-?\\d{1,3}),(-?\\d{1,3})\\)|do\\(\\)|don't\\(\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        int sum = 0;
        boolean enabled = true;

        while (matcher.find()) {
            if (matcher.group().equals("do()")) {
                enabled = true;
            } else if (matcher.group().equals("don't()")) {
                enabled = false;
            } else if (enabled) {
                int left = Integer.parseInt(matcher.group(1));
                int right = Integer.parseInt(matcher.group(2));

                int mul = left * right;
                sum += mul;
            }
        }
        return sum;
    }
}