package main.java.com.jkuhta.aoc2024;

import java.io.IOException;
import java.util.List;

import main.java.com.jkuhta.aoc2024.utils.CommonUtils;
import main.java.com.jkuhta.aoc2024.utils.FileUtils;

public class Day06
{
    public static void main(String[] args) throws IOException
    {
        String input = FileUtils.readFile("06");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static int solvePart1(String input)
    {
        char[][] grid = FileUtils.read2DCharGrid(input);
        List<Character> directions = List.of('^', '>', 'v', '<');
        int[] position = findCurrentPosition(grid, directions);
        int x = position[0];
        int y = position[1];

        int direction = directions.indexOf(grid[x][y]);
        int distinctPositions = 0;

        while (!CommonUtils.isOutOfBounds(x, y, grid))
        {
            distinctPositions += move(position, grid, direction);
            direction = (direction + 1) % 4;
        }

        return distinctPositions;
    }

    public static int solvePart2(String input)
    {
        return 0;
    }

    public static int[] findCurrentPosition(char[][] grid, List<Character> targets)
    {
        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[i].length; j++)
            {
                if (targets.contains(grid[i][j]))
                {
                    return new int[]
                    { i, j };
                }
            }
        }
        return new int[0];
    }

    public static int move(int[] position, char[][] grid, int direction)
    {
        int distinctPositions = 0;

        int x = position[0];
        int y = position[1];

        while (!CommonUtils.isOutOfBounds(x, y, grid) && grid[x][y] != '#')
        {
            if (grid[x][y] != 'X')
            {
                distinctPositions += 1;
                grid[x][y] = 'X';
            }

            switch (direction)
            {
                case 0:
                    if (grid[x - 1][y] != '#')
                        x--;
                    else
                        break;
                    break;
                case 1:
                    if (grid[x][y + 1] != '#')
                        y++;
                    else
                        break;
                    break;
                case 2:
                    if (grid[x + 1][y] != '#')
                        x++;
                    else
                        break;

                    break;
                case 3:
                    if (grid[x][y - 1] != '#')
                        y--;
                    else
                        break;

                    break;
            }
        }
        position[0] = x;
        position[1] = y;

        return distinctPositions;
    }
}
