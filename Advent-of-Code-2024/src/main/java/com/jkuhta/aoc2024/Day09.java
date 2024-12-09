package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.FileUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day09 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("09");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static long solvePart1(String input) {

        List<Integer> blocks = new LinkedList<>();
        for (int i = 0; i < input.length(); i++) {
            int digit = Character.digit(input.toCharArray()[i], 10);

            for (int j = 0; j < digit; j++) {
                if (i % 2 == 0) {
                    blocks.add(i / 2);
                } else {
                    blocks.add(-1);
                }
            }
        }
        long checksum = 0;

        int i = 0;
        while (i <= blocks.size()) {
            if (blocks.getLast() < 0) {
                blocks.removeLast();
                continue;
            }
            if (i >= blocks.size()) break;
            if (blocks.get(i) < 0) {
                blocks.set(i, blocks.getLast());
                blocks.removeLast();
            } else {
                i++;
            }
        }
        for (int j = 0; j < blocks.size(); j++) {
            checksum += blocks.get(j) * j;
        }

        return checksum;
    }

    public static long solvePart2(String input) {

        List<List<Integer>> blocks = new LinkedList<>();
        for (int i = 0; i < input.length(); i++) {
            int digit = Character.digit(input.toCharArray()[i], 10);

            List<Integer> block = new LinkedList<>();
            for (int j = 0; j < digit; j++) {
                if (i % 2 == 0) {
                    block.add(i / 2);
                } else {
                    block.add(-1);
                }
            }
            if (!block.isEmpty())
                blocks.add(block);
        }

        long checksum = 0;

        List<Integer> finalFileSystem = new LinkedList<>();
        int blockIxFromEnd = blocks.size() - 1;

        for (int i = blocks.size() - 1; i >= 0; i--) {

            if (!isFreeSpace(blocks.get(i))) {
                List<Integer> block = blocks.get(i);
                int emptyBlockIx = findEmptySpace(blocks, i, block.size());
                if (emptyBlockIx >= 0) {
                    List<Integer> emptyBlock = blocks.get(emptyBlockIx);

                    List<Integer> newEmptyBlock = emptyBlock.subList(0, emptyBlock.size() - block.size());
                    blocks.remove(block);
                    blocks.add(i, emptyBlock.subList(0, block.size()));

                    blocks.remove(emptyBlock);
                    if (!newEmptyBlock.isEmpty())
                        blocks.add(emptyBlockIx, newEmptyBlock);
                    blocks.add(emptyBlockIx, block);

                }
            }
        }

        int ix = 0;
        for (int i = 0; i < blocks.size(); i++) {
            for (int j = 0; j < blocks.get(i).size(); j++) {
                if (!isFreeSpace(blocks.get(i)))
                    checksum += (long) blocks.get(i).get(j) * (ix);
                ix++;
            }

        }

        return checksum;
    }

    private static boolean isFreeSpace(List<Integer> block) {
        return block.stream().anyMatch(num -> num < 0);
    }

    private static int blockSum(List<Integer> block) {
        int sum = 0;
        for (int num : block) {
            sum += num;
        }
        return sum;
    }

    private static int findEmptySpace(List<List<Integer>> blocks, int blockIndex, int blockSize) {
        for (int i = 0; i < blockIndex; i++) {
            if (blocks.get(i).size() >= blockSize && isFreeSpace(blocks.get(i))) {
                return i;
            }
        }
        return -1;
    }
}
