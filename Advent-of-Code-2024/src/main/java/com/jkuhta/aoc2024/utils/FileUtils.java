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

        ArrayList<String> lines = new ArrayList<>();

        try (Scanner scanner = new Scanner(input)) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        }

        return lines;
    }
}