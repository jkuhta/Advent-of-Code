package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.FileUtils;
import main.java.com.jkuhta.aoc2024.utils.Point;

import java.io.IOException;
import java.util.*;

public class Day14 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("14");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static long solvePart1(String input) {

        List<String> lines = FileUtils.readLines(input);

        int width = 101;
        int height = 103;
        int iterations = 100;

        Set<Robot> robots = parseRobots(lines);
        Map<Point, Integer> map = new HashMap<>();

        for (Robot robot : robots) {
            robot.moveRobot(iterations, width, height);
            Point position = robot.getPosition();
            map.put(position, map.getOrDefault(position, 0) + 1);
        }

        int q1 = 0;
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Point point = new Point(j, i);
                if (point.getX() == width / 2 || point.getY() == height / 2) {
                    continue;
                }
                if (map.containsKey(point)) {
                    if (point.getX() < width / 2 && point.getY() < height / 2) {
                        q1 += map.get(point);
                    } else if (point.getX() > width / 2 && point.getY() < height / 2) {
                        q2 += map.get(point);
                    } else if (point.getX() < width / 2 && point.getY() > height / 2) {
                        q3 += map.get(point);
                    } else if (point.getX() > width / 2 && point.getY() > height / 2) {
                        q4 += map.get(point);
                    }
                }
            }
        }


        return (long) q1 * q2 * q3 * q4;
    }

    public static int solvePart2(String input) {

        List<String> lines = FileUtils.readLines(input);

        int width = 101;
        int height = 103;
        int iterations = 0;

        Set<Robot> robots = parseRobots(lines);
        Set<Point> set = new HashSet<>();

        boolean done = false;

        while (!done) {
            iterations++;
            set = new HashSet<>();
            for (Robot robot : robots) {
                robot.moveRobot(1, width, height);
                Point position = robot.getPosition();
                set.add(position);
            }

            if (set.size() == robots.size()) done = true;
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Point point = new Point(j, i);
                if (set.contains(point))
                    System.out.print("#");
                else
                    System.out.print(".");
            }
            System.out.println();
        }

        return iterations;
    }

    public static Set<Robot> parseRobots(List<String> lines) {
        Set<Robot> robots = new HashSet<>();

        for (String line : lines) {
            String[] parts = line.split(" ");

            String positionPart = parts[0].substring(2);
            String[] positionCoords = positionPart.split(",");
            int px = Integer.parseInt(positionCoords[0]);
            int py = Integer.parseInt(positionCoords[1]);
            Point position = new Point(px, py);

            String velocityPart = parts[1].substring(2);
            String[] velocityCoords = velocityPart.split(",");
            int vx = Integer.parseInt(velocityCoords[0]);
            int vy = Integer.parseInt(velocityCoords[1]);
            Point velocity = new Point(vx, vy);

            robots.add(new Robot(position, velocity));
        }

        return robots;
    }
}


class Robot {
    private Point position;
    private Point velocity;

    public Robot(Point position, Point velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public Point getPosition() {
        return this.position;
    }

    public void moveRobot(int iterations, int width, int height) {
        int newX = (position.getX() + velocity.getX() * iterations) % width;
        if (newX < 0) newX += width;
        int newY = (position.getY() + velocity.getY() * iterations) % height;
        if (newY < 0) newY += height;

        this.position = new Point(newX, newY);
    }
}
