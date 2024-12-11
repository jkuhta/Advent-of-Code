package main.java.com.jkuhta.aoc2024;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import main.java.com.jkuhta.aoc2024.utils.FileUtils;

public class Day11
{
    public static void main(String[] args) throws IOException
    {
        String input = FileUtils.readFile("11");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static long solvePart1(String input)
    {

        String[] stones = input.split(" ");
        Map<String, Map<Integer, Long>> map = new HashMap<>();

        long stoneCount = 0;
        for (String stone : stones)
        {
            stoneCount += blink(stone, 25, map);
        }

        return stoneCount;
    }

    public static long solvePart2(String input)
    {
        String[] stones = input.split(" ");
        Map<String, Map<Integer, Long>> map = new HashMap<>();

        long stoneCount = 0;
        for (String stone : stones)
        {
            stoneCount += blink(stone, 75, map);
        }

        return stoneCount;
    }

    private static long blink(String stone, int blinkCount, Map<String, Map<Integer, Long>> map)
    {

        if (blinkCount == 0)
            return 1;

        if (map.containsKey(stone) && map.get(stone).containsKey(blinkCount))
            return map.get(stone).get(blinkCount);

        long stoneCount;
        if (stone.equals("0"))
            stoneCount = blink("1", blinkCount - 1, map);
        else if (stone.length() % 2 == 1)
            stoneCount = blink(String.valueOf((Long.parseLong(stone) * 2024)), blinkCount - 1, map);
        else
            stoneCount = blink(stone.substring(0, stone.length() / 2), blinkCount - 1, map) + blink(trimLeadingZeros(stone.substring(stone.length() / 2)), blinkCount - 1, map);

        map.computeIfAbsent(stone, k -> new HashMap<>())
                .putIfAbsent(blinkCount, stoneCount);

        return stoneCount;
    }

    private static String trimLeadingZeros(String stone)
    {
        String trimmed = stone.replaceFirst("^0+", "");
        return !trimmed.isEmpty() ? trimmed : "0";
    }
}
