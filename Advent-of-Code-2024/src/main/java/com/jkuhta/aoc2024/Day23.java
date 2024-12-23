package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.FileUtils;

import java.io.IOException;
import java.util.*;

public class Day23 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("23");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static int solvePart1(String input) {

        List<String> lines = FileUtils.readLines(input);
        Map<String, Set<String>> graph = buildGraph(lines);

        Set<Set<String>> triangles = new HashSet<>();

        for (String pc1 : graph.keySet()) {
            for (String pc2 : graph.get(pc1)) {
                for (String pc3 : graph.get(pc2)) {
                    if (graph.get(pc1).contains(pc3) && (pc1.startsWith("t") || pc2.startsWith("t") || pc3.startsWith("t"))) {
                        Set<String> triangle = new TreeSet<>(Arrays.asList(pc1, pc2, pc3));
                        triangles.add(triangle);
                    }
                }
            }
        }
        return triangles.size();
    }

    public static String solvePart2(String input) {

        List<String> lines = FileUtils.readLines(input);
        Map<String, Set<String>> graph = buildGraph(lines);

        Set<String> largestClique = findLargestClique(graph);

        return largestClique.stream().sorted().toList().toString().replace(" ", "").replace("[", "").replace("]", "");
    }

    public static Map<String, Set<String>> buildGraph(List<String> lines) {
        Map<String, Set<String>> graph = new HashMap<>();

        for (String line : lines) {
            String[] tokens = line.split("-");
            String pc1 = tokens[0];
            String pc2 = tokens[1];

            graph.computeIfAbsent(pc1, k -> new HashSet<>()).add(pc2);
            graph.computeIfAbsent(pc2, k -> new HashSet<>()).add(pc1);
        }

        return graph;
    }

    public static Set<String> findLargestClique(Map<String, Set<String>> graph) {
        List<Set<String>> maximumCliques = new ArrayList<>();
        bronKerbosch(new HashSet<>(), new HashSet<>(graph.keySet()), new HashSet<>(), graph, maximumCliques);
        return maximumCliques.stream().max(Comparator.comparingInt(Set::size)).orElse(new HashSet<>());
    }

    private static void bronKerbosch(Set<String> R, Set<String> P, Set<String> X, Map<String, Set<String>> graph, List<Set<String>> maximumCliques) {
        if (P.isEmpty() && X.isEmpty()) {
            maximumCliques.add(new HashSet<>(R));
            return;
        }

        for (String v : new HashSet<>(P)) {
            Set<String> neighbors = graph.getOrDefault(v, Collections.emptySet());

            bronKerbosch(
                    union(R, v),
                    intersection(P, neighbors),
                    intersection(X, neighbors),
                    graph,
                    maximumCliques
            );

            P.remove(v);
            X.add(v);
        }
    }

    private static Set<String> union(Set<String> set, String element) {
        Set<String> result = new HashSet<>(set);
        result.add(element);
        return result;
    }

    private static Set<String> intersection(Set<String> set1, Set<String> set2) {
        Set<String> result = new HashSet<>(set1);
        result.retainAll(set2);
        return result;
    }
}
