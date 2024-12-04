package main.java.com.jkuhta.aoc2024.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    public static String readFile(String day) throws IOException {
        return Files.readString(Path.of(String.format("src/main/resources/day%s-input.txt", day)));
    }
}