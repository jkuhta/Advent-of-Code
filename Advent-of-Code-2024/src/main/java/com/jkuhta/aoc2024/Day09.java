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

        List<Block> blocks = new LinkedList<>();
        int[] blockSizes = input.chars().map(c -> c - '0').toArray();

        boolean prevIsEmpty = false;
        Block previousBlock = null;
        for (int blockIndex = 0; blockIndex < input.length(); blockIndex++) {
            int blockSize = blockSizes[blockIndex];

            Block newBlock = null;

            if (blockSize > 0 && blockIndex % 2 == 0) {
                newBlock = new Block(blockSize, blockIndex / 2);
                prevIsEmpty = false;
            } else if (blockSize > 0) {
                if (prevIsEmpty) {
                    Block lastBlock = blocks.get(blocks.size() - 1);
                    lastBlock.size += blockSize;
                    newBlock = lastBlock;
                } else {
                    newBlock = new Block(blockSize, -1);
                    prevIsEmpty = true;
                }
            }

            if (newBlock != null) {
                if (previousBlock != null) {
                    previousBlock.next = newBlock;
                    newBlock.prev = previousBlock;
                }

                blocks.add(newBlock);
                previousBlock = newBlock;
            }
        }

        long checksum = 0;

        Block block = blocks.getLast();
        while (block != null) {

            if (!isEmptySpace(block) && swapBlocks(blocks.getFirst(), block)) {
                block.value = -1;
            }
            block = block.prev;
        }

        int ix = 0;
        block = blocks.getFirst();
        while (block != null) {

            for (int j = 0; j < block.size; j++) {
                if (!isEmptySpace(block))
                    checksum += (long) block.value * (ix);
                ix++;
            }
            block = block.next;
        }

        return checksum;
    }

    private static boolean isEmptySpace(Block block) {
        return block.value == -1;
    }

    private static boolean swapBlocks(Block possibleEmptyBlock, Block block) {

        while (possibleEmptyBlock != block) {

            if (possibleEmptyBlock.size >= block.size && isEmptySpace(possibleEmptyBlock)) {
                int remainingSize = possibleEmptyBlock.size - block.size;

                possibleEmptyBlock.size = block.size;

                if (remainingSize > 0) {
                    Block newBlock = new Block(remainingSize, -1);  // Add a new empty block
                    newBlock.prev = possibleEmptyBlock;
                    newBlock.next = possibleEmptyBlock.next;
                    possibleEmptyBlock.next = newBlock;
                }

                possibleEmptyBlock.value = block.value;

                return true;
            }

            possibleEmptyBlock = possibleEmptyBlock.next;
        }
        return false;
    }
}

class Block {
    int size;
    int value;
    Block prev;
    Block next;

    public Block(int size, int value) {
        this.size = size;
        this.value = value;
    }

    @Override
    public String toString() {
        if (size <= 0) {
            return "";
        }
        return String.valueOf(value >= 0 ? value : ".").repeat(size);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Block other = (Block) obj;
        return this.value == other.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}