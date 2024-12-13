package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.FileUtils;

import java.io.IOException;

public class Day13 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("13");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static int solvePart1(String input) {

        String[] machinesData = input.split("\n\n");

        int totalTokens = 0;

        for (String machineData : machinesData) {
            Machine machine = parseMachine(machineData);
            totalTokens += playMachine(machine);
        }
        return totalTokens;
    }

    public static long solvePart2(String input) {
        String[] machinesData = input.split("\n\n");

        long totalTokens = 0;

        for (String machineData : machinesData) {
            Machine machine = parseMachine(machineData);
            totalTokens += playMachine2(machine);
        }
        return totalTokens;
    }

    public static Machine parseMachine(String input) {
        String[] lines = input.split("\n");

        // Extract values using regex
        int ax = Integer.parseInt(lines[0].replaceAll(".*X\\+(\\d+),.*", "$1"));
        int ay = Integer.parseInt(lines[0].replaceAll(".*Y\\+(\\d+)", "$1"));
        int bx = Integer.parseInt(lines[1].replaceAll(".*X\\+(\\d+),.*", "$1"));
        int by = Integer.parseInt(lines[1].replaceAll(".*Y\\+(\\d+)", "$1"));
        int px = Integer.parseInt(lines[2].replaceAll(".*X=(\\d+),.*", "$1"));
        int py = Integer.parseInt(lines[2].replaceAll(".*Y=(\\d+)", "$1"));

        // Create and return the machine
        return new Machine(new Button(3, ax, ay), new Button(1, bx, by), new Prize(px, py));
    }

    public static int playMachine(Machine machine) {
        int minCost = Integer.MAX_VALUE;

        for (int aPresses = 0; aPresses <= 100; aPresses++) {
            for (int bPresses = 0; bPresses <= 100; bPresses++) {
                int x = aPresses * machine.a.x + bPresses * machine.b.x;
                int y = aPresses * machine.a.y + bPresses * machine.b.y;

                if (x == machine.p.x && y == machine.p.y) {
                    int cost = aPresses * machine.a.cost + bPresses * machine.b.cost;
                    minCost = Math.min(minCost, cost);
                }
            }
        }
        return minCost == Integer.MAX_VALUE ? 0 : minCost;
    }

    public static long playMachine2(Machine machine) {
        int ax = machine.a.x;
        int ay = machine.a.y;
        int bx = machine.b.x;
        int by = machine.b.y;

        long px = machine.p.x + 10000000000000L;
        long py = machine.p.y + 10000000000000L;

        int det = bx * ay - ax * by;
        long numeratorX = bx * py - px * by;

        if (det == 0 || numeratorX % det != 0) return 0;

        long aPresses = numeratorX / det;
        long numeratorY = px - aPresses * ax;

        if (numeratorY % bx == 0) {
            long bPresses = numeratorY / bx;
            return aPresses * machine.a.cost + bPresses * machine.b.cost;
        }

        return 0;
    }

}

class Button {
    int cost;
    int x;
    int y;
    int presses;

    public Button(int cost, int x, int y) {
        this.cost = cost;
        this.x = x;
        this.y = y;
        this.presses = 0;
    }
}

class Prize {
    long x;
    long y;

    public Prize(long x, long y) {
        this.x = x;
        this.y = y;
    }
}

class Machine {
    Button a;
    Button b;
    Prize p;

    public Machine(Button a, Button b, Prize p) {
        this.a = a;
        this.b = b;
        this.p = p;
    }
}