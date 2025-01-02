package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.FileUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day24 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("24");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static long solvePart1(String input) {

        List<String> initialValuesLines = FileUtils.readLines(input.split("\n\n")[0]);
        List<String> gatesLines = FileUtils.readLines(input.split("\n\n")[1]);

        Map<String, Integer> valuesMap = parseValues(initialValuesLines);
        parseGates(gatesLines, valuesMap);

        StringBuilder result = new StringBuilder();
        for (String key : valuesMap.keySet().stream().sorted().toList()) {
            if (key.startsWith("z")) {
                result.insert(0, valuesMap.get(key));
            }
        }
        return Long.parseLong(result.toString(), 2);
    }

    public static String solvePart2(String input) {

        List<String> initialValuesLines = FileUtils.readLines(input.split("\n\n")[0]);
        List<String> gatesLines = FileUtils.readLines(input.split("\n\n")[1]);

        Map<String, Integer> valuesMap = parseValues(initialValuesLines);
        List<String[]> gates = parseGates(gatesLines, valuesMap);

        Set<String> wrongGates = findWrongGates(gates);

        return wrongGates.stream()
                .sorted()
                .collect(Collectors.joining(","));

    }

    private static Map<String, Integer> parseValues(List<String> lines) {
        Map<String, Integer> valuesMap = new HashMap<>();
        for (String line : lines) {
            String wire = line.split(": ")[0];
            int value = Integer.parseInt(line.split(": ")[1]);
            valuesMap.put(wire, value);
        }
        return valuesMap;
    }

    private static List<String[]> parseGates(List<String> lines, Map<String, Integer> valuesMap) {
        int ix = 0;

        List<String[]> gates = new ArrayList<>();

        while (!lines.isEmpty()) {

            ix = ix % lines.size();
            String line = lines.get(ix);

            String resultWire = line.split(" -> ")[1];

            String left = line.split(" -> ")[0];
            String wire1 = left.split(" ")[0];
            String wire2 = left.split(" ")[2];
            String operand = left.split(" ")[1];

            gates.add(new String[]{wire1, wire2, operand, resultWire});

            if (!valuesMap.containsKey(wire1) || !valuesMap.containsKey(wire2)) {
                ix++;
                continue;
            }

            int result;
            if (operand.equals("AND")) result = valuesMap.get(wire1) & valuesMap.get(wire2);
            else if (operand.equals("OR")) result = valuesMap.get(wire1) | valuesMap.get(wire2);
            else result = valuesMap.get(wire1) ^ valuesMap.get(wire2);

            valuesMap.put(resultWire, result);

            lines.remove(ix);
        }
        return gates;
    }

    public static Set<String> findWrongGates(List<String[]> gates) {

        Set<String> wrongs = new HashSet<>();

        for (String[] gate : gates) {
            String wire1 = gate[0];
            String wire2 = gate[1];
            String operand = gate[2];
            String resultWire = gate[3];

            if (resultWire.startsWith("z") && !operand.equals("XOR") && !resultWire.equals("z45")) {
                wrongs.add(resultWire);
            }

            if (operand.equals("XOR")
                    && !resultWire.matches("^z.*")
                    && !wire1.matches("^[xy].*")
                    && !wire2.matches("^[xy].*")) {
                wrongs.add(resultWire);
            }

            if (operand.equals("AND") && !wire1.equals("x00") && !wire2.equals("x00")) {
                for (String[] subGate : gates) {
                    String subWire1 = subGate[0];
                    String subWire2 = subGate[1];
                    String subOperand = subGate[2];

                    if ((resultWire.equals(subWire1) || resultWire.equals(subWire2)) && !subOperand.equals("OR")) {
                        wrongs.add(resultWire);
                    }
                }
            }

            if (operand.equals("XOR")) {
                for (String[] subGate : gates) {
                    String subWire1 = subGate[0];
                    String subWire2 = subGate[1];
                    String subOperand = subGate[2];

                    if ((resultWire.equals(subWire1) || resultWire.equals(subWire2)) && subOperand.equals("OR")) {
                        wrongs.add(resultWire);
                    }
                }
            }
        }

        return wrongs;
    }
}
