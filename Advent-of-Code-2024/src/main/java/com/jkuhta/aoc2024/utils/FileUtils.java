package main.java.com.jkuhta.aoc2024.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils {
    public static String readFile(String day) throws IOException {
        return Files.readString(Path.of(String.format("src/main/resources/day%s-input.txt", day)));
    }

    public static List<String> readLines(String input) {

        List<String> lines = new ArrayList<>();

        try (Scanner scanner = new Scanner(input)) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        }

        return lines;
    }

    public static char[][] read2DCharGrid(String input) {
        List<char[]> tempList = new ArrayList<>();
        try (Scanner scanner = new Scanner(input)) {
            while (scanner.hasNextLine()) {
                tempList.add(scanner.nextLine().toCharArray());
            }
        }
        return tempList.toArray(new char[0][]);
    }

    public static int[][] read2DIntGrid(String input) {
        List<int[]> tempList = new ArrayList<>();
        try (Scanner scanner = new Scanner(input)) {
            while (scanner.hasNextLine()) {
                tempList.add(scanner.nextLine().chars().map(c -> c - '0').toArray());
            }
        }
        return tempList.toArray(new int[0][]);
    }
}