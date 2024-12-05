package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.FileUtils;

import java.io.IOException;
import java.util.*;

public class Day05 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("05");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static int solvePart1(String input) {

        String[] inputParts = input.split("\n\n");

        List<String> rules = FileUtils.readLines(inputParts[0]);
        List<String> updates = FileUtils.readLines(inputParts[1]);

        Map<Integer, Set<Integer>> page2pagesAfter = new HashMap<>();
        List<List<Integer>> updateLists = new ArrayList<>();

        for (String rule: rules) {
            List<Integer> tokens = Arrays.stream(rule.split("\\|")).map(Integer::parseInt).toList();
            page2pagesAfter.computeIfAbsent(tokens.getFirst(), k -> new HashSet<>()).add(tokens.getLast());
        }

        for (String update: updates) {
            updateLists.add(Arrays.stream(update.split(",")).map(Integer::parseInt).toList());
        }

        int middlePageSum = 0;

        for (List<Integer> updateList : updateLists) {
            if (isCorrectlyOrdered(updateList, page2pagesAfter)) {
                middlePageSum += updateList.get(updateList.size() / 2);
            }
        }

        return middlePageSum;
    }

    public static int solvePart2(String input) {

        String[] inputParts = input.split("\n\n");

        List<String> rules = FileUtils.readLines(inputParts[0]);
        List<String> updates = FileUtils.readLines(inputParts[1]);

        Map<Integer, Set<Integer>> page2pagesAfter = new HashMap<>();
        List<List<Integer>> updateLists = new ArrayList<>();

        for (String rule: rules) {
            List<Integer> tokens = Arrays.stream(rule.split("\\|")).map(Integer::parseInt).toList();
            page2pagesAfter.computeIfAbsent(tokens.getFirst(), k -> new HashSet<>()).add(tokens.getLast());
        }

        for (String update: updates) {
            updateLists.add(Arrays.stream(update.split(",")).map(Integer::parseInt).toList());
        }

        int middlePageSum = 0;

        for (List<Integer> updateList : updateLists) {
            if (!isCorrectlyOrdered(updateList, page2pagesAfter)) {
                middlePageSum += getMiddleOfOrderedList(updateList, page2pagesAfter);
            }
        }

        return middlePageSum;    }

    public static boolean isCorrectlyOrdered(List<Integer> list, Map<Integer, Set<Integer>> map) {
        Set<Integer> checkedSet = new HashSet<>();
        for (Integer page : list) {
            if (!Collections.disjoint(checkedSet, map.getOrDefault(page, new HashSet<>()))) return false;
            checkedSet.add(page);

        }
        return true;
    }

    public static Integer getMiddleOfOrderedList(List<Integer> list, Map<Integer, Set<Integer>> map) {

        for (Integer page : list) {
           Set<Integer> intersection = new HashSet<>(list);
            intersection.retainAll(map.getOrDefault(page, new HashSet<>()));

            if (intersection.size() == list.size() / 2) {
                return page;
            }
        }
        return 0;
    }
}
