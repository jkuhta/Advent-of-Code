package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.CommonUtils;
import main.java.com.jkuhta.aoc2024.utils.FileUtils;
import main.java.com.jkuhta.aoc2024.utils.Point;

import java.io.IOException;
import java.util.*;

public class Day12 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("12");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static int solvePart1(String input) {

        char[][] points = FileUtils.read2DCharGrid(input);

        Set<Point> visited = new HashSet<>();

        int price = 0;

        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                Point point = new Point(i, j, points[i][j]);
                if (!visited.contains(point)) {
                    Region region = floodFill(points, point, visited);
                    price += region.calculatePrice();
                }
            }
        }

        return price;
    }

    public static int solvePart2(String input) {
        char[][] points = FileUtils.read2DCharGrid(input);

        Set<Point> visited = new HashSet<>();

        int price = 0;

        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                Point point = new Point(i, j, points[i][j]);
                if (!visited.contains(point)) {
                    Region region = floodFill(points, point, visited);
                    int sides = region.countCorners();
//                    System.out.println(region.label + " : " + sides);
                    price += sides * region.area;
                }
            }
        }
        return price;
    }

    private static Region floodFill(char[][] points, Point point, Set<Point> visited) {
        Queue<Point> queue = new LinkedList<>();
        Region region = new Region(point.getLabel());
        queue.add(point);

        char label = point.getLabel();

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            int x = current.getX();
            int y = current.getY();

            if (region.points.contains(current))
                continue;

            if (CommonUtils.isOutOfBounds(x, y, points) || points[x][y] != region.label || visited.contains(current)) {
                region.incrementPerimeter();
                continue;
            }

            region.addPoint(current);
            visited.add(current);

            queue.add(new Point(x - 1, y, label));
            queue.add(new Point(x + 1, y, label));
            queue.add(new Point(x, y - 1, label));
            queue.add(new Point(x, y + 1, label));
        }

        return region;
    }

}

class Region {
    char label;
    int area;
    int perimeter;
    List<Point> points = new ArrayList<>();

    public Region(char label) {
        this.label = label;
        this.area = 0;
        this.perimeter = 0;
    }

    public void addPoint(Point point) {
        this.points.add(point);
        this.area++;
    }

    public void incrementPerimeter() {
        this.perimeter++;
    }

    public int calculatePrice() {
        return this.area * this.perimeter;
    }

    public int countCorners() {
        int numCorners = 0;

        int[] offsets = {1, -1};

        for (Point point : this.points) {
            int row = point.getX();
            int col = point.getY();

            for (int rowOffset : offsets) {
                for (int colOffset : offsets) {
                    Point rowNeighbor = new Point(row + rowOffset, col, label);
                    Point colNeighbor = new Point(row, col + colOffset, label);
                    Point diagonalNeighbor = new Point(row + rowOffset, col + colOffset, label);

                    if (!this.points.contains(rowNeighbor) && !this.points.contains(colNeighbor)) {
                        numCorners++;
                    }
                    if (
                            this.points.contains(rowNeighbor) &&
                                    this.points.contains(colNeighbor) &&
                                    !this.points.contains(diagonalNeighbor)
                    ) {
                        numCorners++;
                    }
                }
            }
        }
        return numCorners;
    }
}
