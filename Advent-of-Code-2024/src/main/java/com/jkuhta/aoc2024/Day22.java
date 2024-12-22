package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.FileUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day22 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("22");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static long solvePart1(String input) {

        List<Integer> initialNumbers = FileUtils.readLines(input).stream().mapToInt(Integer::parseInt).boxed().toList();

        long sum = 0;

        for (long initialNumber : initialNumbers) {
            sum += predictSecretNumber(initialNumber);
        }
        return sum;
    }

    public static long solvePart2(String input) {
        List<Integer> initialNumbers = FileUtils.readLines(input).stream().mapToInt(Integer::parseInt).boxed().toList();


        Map<String, Integer> sequence2prices = new HashMap<>();

        List<Integer> lastFour = new ArrayList<>();
        int lastPrice = initialNumbers.getFirst() % 10;


        for (long secretNumber : initialNumbers) {
            Set<String> alreadySeenSequences = new HashSet<>();

            for (int i = 0; i < 2000; i++) {
                secretNumber = calculateNextSecretNumber(secretNumber);
                int currentPrice = (int) (secretNumber % 10);
                int diff = currentPrice - lastPrice;

                lastFour.add(diff);
                if (lastFour.size() > 4) lastFour.removeFirst();

                String key = lastFour.toString();
                if (lastFour.size() == 4 && !alreadySeenSequences.contains(key)) {
                    sequence2prices.merge(key, currentPrice, Integer::sum);
                    alreadySeenSequences.add(key);
                }
                lastPrice = currentPrice;
            }
        }

        return sequence2prices.values().stream().max(Integer::compareTo).get();
    }

    public static long predictSecretNumber(long initialNumber) {

        long result = initialNumber;
        for (long i = 0; i < 2000; i++) {
            result = calculateNextSecretNumber(result);
        }
        return result;
    }

    public static long mixAndPrune(long result, long secretNumber) {
        return (secretNumber ^ result) % 16777216;
    }

    public static long calculateNextSecretNumber(long secretNumber) {
        long result = secretNumber;
        result = mixAndPrune(result * 64, result);
        result = mixAndPrune(result / 32, result);
        result = mixAndPrune(result * 2048, result);

        return result;
    }

}
