package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.FileUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day17 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("17");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static String solvePart1(String input) {

        String[] registerLines = input.split("\n\n")[0].split("\n");
        String[] programLine = input.split("\n\n")[1].split(": ")[1].split(",");

        long A = Long.parseLong(registerLines[0].split(": ")[1]);
        int B = Integer.parseInt(registerLines[1].split(": ")[1]);
        int C = Integer.parseInt(registerLines[2].split(": ")[1]);

        List<Integer> program = Arrays.stream(programLine).map(Integer::parseInt).toList();

        return runProgram(program, A, B, C).stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public static long solvePart2(String input) {

        String[] programLine = input.split("\n\n")[1].split(": ")[1].split(",");

        List<Integer> program = Arrays.stream(programLine).map(Integer::parseInt).toList();

        return selfReplicator(program);
    }

    private static List<Integer> runProgram(List<Integer> program, long A, long B, long C) {

        int position = 0;
        List<Integer> outputs = new ArrayList<>();

        while (position < program.size()) {

            int opcode = program.get(position);
            int operand = program.get(position + 1);
            long combo = combo(program.get(position + 1), A, B, C);

            switch (opcode) {
                case 0:
                    A = dv(A, combo);
                    break;
                case 1:
                    B = xor(B, operand);
                    break;
                case 2:
                    B = bst(combo);
                    break;
                case 3:
                    position = jnz(A, operand, position);
                    break;
                case 4:
                    B = xor(B, C);
                    break;
                case 5:
                    outputs.add(out(combo));
                    break;
                case 6:
                    B = dv(A, combo);
                    break;
                case 7:
                    C = dv(A, combo);
                    break;
            }
            position += 2;

        }
        return outputs;
    }

    public static long selfReplicator(List<Integer> program) {
        SortedSet<Long> candidates = new TreeSet<>();
        candidates.add(0L);
        for (int i = 0; i < program.size(); i++) {
            var lastInstruction = program.get(program.size() - i - 1);
            SortedSet<Long> newCandidates = new TreeSet<>();
            for (var old : candidates) {
                long candidate = old << 3;
                for (int j = 0; j < 8; j++) {
                    if (Objects.equals(runProgram(program, candidate, 0, 0).getFirst(), lastInstruction)) {
                        newCandidates.add(candidate);
                    }
                    candidate++;
                }
            }
            candidates = newCandidates;
        }
        return candidates.first();
    }

    private static long combo(int operand, long A, long B, long C) {
        return operand <= 3 ? operand : operand == 4 ? A : operand == 5 ? B : operand == 6 ? C : -1;
    }

    private static long dv(long A, long combo) {
        return (long) (A / Math.pow(2, combo));
    }

    private static long xor(long a, long b) {
        return a ^ b;
    }

    private static int bst(long combo) {
        return (int) (combo % 8);
    }

    private static int jnz(long A, int operand, int position) {
        return A == 0 ? position : operand - 2;
    }

    private static int out(long combo) {
        return (int) (combo % 8);
    }
}
