package main.java.com.jkuhta.aoc2024;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import main.java.com.jkuhta.aoc2024.utils.CommonUtils;
import main.java.com.jkuhta.aoc2024.utils.FileUtils;
import main.java.com.jkuhta.aoc2024.utils.Point;

public class Day12
{
    public static void main(String[] args) throws IOException
    {
        String input = FileUtils.readFile("12");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static int solvePart1(String input)
    {

        char[][] points = FileUtils.read2DCharGrid(input);

        Set<Point> visited = new HashSet<>();

        int price = 0;

        for (int i = 0; i < points.length; i++)
        {
            for (int j = 0; j < points[i].length; j++)
            {
                Point point = new Point(i, j, points[i][j]);
                if (!visited.contains(point))
                {
                    Region region = floodFill(points, point, visited);
                    price += region.calculatePrice();
                }
            }
        }

        return price;
    }

    public static int solvePart2(String input)
    {

        return 0;
    }

    private static Region floodFill(char[][] points, Point point, Set<Point> visited)
    {
        Queue<Point> queue = new LinkedList<>();
        Region region = new Region(point.getLabel());
        queue.add(point);

        char label = point.getLabel();

        while (!queue.isEmpty())
        {
            Point current = queue.poll();
            int x = current.getX();
            int y = current.getY();

            if (region.points.contains(current))
                continue;

            if (CommonUtils.isOutOfBounds(x, y, points) || points[x][y] != region.label || visited.contains(current))
            {
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

class Region
{
    char label;
    int area;
    int perimeter;

    Set<Point> points = new HashSet<>();

    public Region(char label)
    {
        this.label = label;
        this.area = 0;
        this.perimeter = 0;
    }

    public void addPoint(Point point)
    {
        this.points.add(point);
        this.area++;
    }

    public void incrementPerimeter()
    {
        this.perimeter++;
    }

    public int calculatePrice()
    {
        return this.area * this.perimeter;
    }

}
