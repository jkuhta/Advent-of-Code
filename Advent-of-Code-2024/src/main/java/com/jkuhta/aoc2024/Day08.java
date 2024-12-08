package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.FileUtils;
import main.java.com.jkuhta.aoc2024.utils.Point;

import java.io.IOException;
import java.util.*;

public class Day08 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("08");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static int solvePart1(String input) {

        char[][] lines = FileUtils.read2DCharGrid(input);

        Map<Character, Set<Point>> label2point = new HashMap<>();
        int mapSize = lines.length;

        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length; j++) {
                if (lines[i][j] != '.') {
                    char currentChar = lines[i][j];
                    Point point = new Point(j, i, currentChar);
                    label2point.computeIfAbsent(currentChar, k -> new HashSet<>()).add(point);
                }
            }
        }
        Set<Point> antinodes = new HashSet<>();

        for (Character key : label2point.keySet()) {
            Set<Point> frequencyPoints = label2point.get(key);
            calculateUniqueLocationsForFrequency(new ArrayList<>(frequencyPoints), label2point, antinodes, mapSize);
        }

        return antinodes.size();
    }

    public static int solvePart2(String input) {
        char[][] lines = FileUtils.read2DCharGrid(input);

        Map<Character, Set<Point>> label2point = new HashMap<>();
        int mapSize = lines.length;

        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length; j++) {
                if (lines[i][j] != '.') {
                    char currentChar = lines[i][j];
                    Point point = new Point(j, i, currentChar);
                    label2point.computeIfAbsent(currentChar, k -> new HashSet<>()).add(point);
                }
            }
        }
        Set<Point> antinodes = new HashSet<>();

        for (Character key : label2point.keySet()) {
            Set<Point> frequencyPoints = label2point.get(key);
            calculateUniqueLocationsForFrequency2(new ArrayList<>(frequencyPoints), label2point, antinodes, mapSize);
        }

        return antinodes.size();
    }

    private static void calculateUniqueLocationsForFrequency(List<Point> frequencyPoints, Map<Character, Set<Point>> label2point, Set<Point> antinodes, int mapSize) {
        for (int i = 0; i < frequencyPoints.size(); i++) {
            for (int j = i + 1; j < frequencyPoints.size(); j++) {
                addAntinodes(frequencyPoints.get(i), frequencyPoints.get(j), label2point, antinodes, mapSize);
                addAntinodes(frequencyPoints.get(j), frequencyPoints.get(i), label2point, antinodes, mapSize);
            }
        }
    }

    private static void calculateUniqueLocationsForFrequency2(List<Point> frequencyPoints, Map<Character, Set<Point>> label2point, Set<Point> antinodes, int mapSize) {
        for (int i = 0; i < frequencyPoints.size(); i++) {
            for (int j = i + 1; j < frequencyPoints.size(); j++) {
                addAntinodes2(frequencyPoints.get(i), frequencyPoints.get(j), label2point, antinodes, mapSize);
                addAntinodes2(frequencyPoints.get(j), frequencyPoints.get(i), label2point, antinodes, mapSize);
            }
        }
    }

    private static void addAntinodes(Point point1, Point point2, Map<Character, Set<Point>> label2point, Set<Point> antinodes, int mapSize) {
        Point locationCandidate = new Point(point1.getX() + (point1.getX() - point2.getX()), point1.getY() + (point1.getY() - point2.getY()), '#');

        if (isInBounds(locationCandidate, mapSize) && isAntinodeLocation(locationCandidate, label2point, antinodes, point1.getLabel())) {
            antinodes.add(locationCandidate);
        }
    }

    private static void addAntinodes2(Point point1, Point point2, Map<Character, Set<Point>> label2point, Set<Point> antinodes, int mapSize) {

        for (int i = 0; i < mapSize; i++) {
            Point locationCandidate = new Point(point1.getX() + i * (point1.getX() - point2.getX()), point1.getY() + i * (point1.getY() - point2.getY()), '#');
            if (isInBounds(locationCandidate, mapSize) && isAntinodeLocation(locationCandidate, label2point, antinodes, point1.getLabel())) {
                antinodes.add(locationCandidate);
                antinodes.add(point1);
                antinodes.add(point2);
            }
        }
    }

    private static boolean isInBounds(Point point, int mapSize) {
        return point.getX() >= 0 && point.getX() < mapSize && point.getY() >= 0 && point.getY() < mapSize;
    }

    private static boolean isAntinodeLocation(Point point, Map<Character, Set<Point>> label2point, Set<Point> antinodes, char frequency) {

        return !antinodes.contains(point) && !label2point.get(frequency).contains(point);
    }
}
