package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.CommonUtils;
import main.java.com.jkuhta.aoc2024.utils.FileUtils;
import main.java.com.jkuhta.aoc2024.utils.Node;

import java.io.IOException;
import java.util.*;

public class Day16 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("16");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static int solvePart1(String input) {

        char[][] grid = FileUtils.read2DCharGrid(input);

        Node start = null;
        Node end = null;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 'S') start = new Node(i, j, '>', 0);
                else if (grid[i][j] == 'E') end = new Node(i, j, 'E');
            }
        }

        int cost = findShortestPath(grid, start, end).getValue();
        return cost;
    }

    public static int solvePart2(String input) {
        char[][] grid = FileUtils.read2DCharGrid(input);

        Node start = null;
        Node end = null;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 'S') start = new Node(i, j, '>', 0);
                else if (grid[i][j] == 'E') end = new Node(i, j, 'E');
            }
        }

        return findTilesOnShortestPath(grid, start, end);
    }

    private static Node findShortestPath(char[][] grid, Node start, Node end) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getValue));
        pq.add(start);

        List<Character> directions = new ArrayList<>(Arrays.asList('^', '>', 'v', '<'));
        Map<Node, Integer> visited = new HashMap<>();

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (current.equals(end)) return current;

            if (visited.containsKey(current)) {
                if (current.getValue() < visited.get(current)) {
                    visited.put(current, current.getValue());
                } else {
                    continue;
                }
            } else visited.put(current, current.getValue());

            // STRAIGHT
            Node nextStraight = nextNode(current, current.getLabel(), grid);
            if (nextStraight != null) {
                nextStraight.setValue(current.getValue() + 1);
                nextStraight.setPrev(current);
                pq.add(nextStraight);
            }

            // RIGHT
            Node nextRight = nextNode(current, directions.get((directions.indexOf(current.getLabel()) + 3) % 4), grid);
            if (nextRight != null) {
                nextRight.setValue(current.getValue() + 1001);
                nextRight.setPrev(current);
                pq.add(nextRight);
            }

            // LEFT
            Node nextLeft = nextNode(current, directions.get((directions.indexOf(current.getLabel()) + 1) % 4), grid);
            if (nextLeft != null) {
                nextLeft.setValue(current.getValue() + 1001);
                nextLeft.setPrev(current);
                pq.add(nextLeft);
            }
        }

        return null;
    }

    private static int findTilesOnShortestPath(char[][] grid, Node start, Node end) {

        Node finalNode = findShortestPath(grid, start, end);

        Map<Node, Integer> nodesOnPath = new HashMap<>();

        Node temp = finalNode;
        while (temp != null) {
            nodesOnPath.put(temp, temp.getValue());
            temp = temp.getPrev();
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getValue));
        pq.add(start);

        List<Character> directions = new ArrayList<>(Arrays.asList('^', '>', 'v', '<'));
        Map<Node, Integer> visited = new HashMap<>();

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (nodesOnPath.containsKey(current)) {
                if (nodesOnPath.get(current) + 1000 < current.getValue()) {
                    continue;
                } else if (nodesOnPath.get(current) == current.getValue()) {
                    temp = current.getPrev();
                    while (temp != null) {
                        nodesOnPath.put(temp, temp.getValue());
                        temp = temp.getPrev();
                    }
                    current.setPrev(null);
                }

            }

            if (visited.containsKey(current)) {
                if (current.getValue() < visited.get(current)) {
                    visited.put(current, current.getValue());
                } else if (current.getValue() > visited.get(current) + 1000) {
                    continue;
                }
            } else visited.put(current, current.getValue());

            // STRAIGHT
            Node nextStraight = nextNode(current, current.getLabel(), grid);
            if (nextStraight != null) {
                nextStraight.setValue(current.getValue() + 1);
                nextStraight.setPrev(current);
                pq.add(nextStraight);
            }

            // RIGHT
            Node nextRight = nextNode(current, directions.get((directions.indexOf(current.getLabel()) + 3) % 4), grid);
            if (nextRight != null) {
                nextRight.setValue(current.getValue() + 1001);
                nextRight.setPrev(current);
                pq.add(nextRight);
            }

            // LEFT
            Node nextLeft = nextNode(current, directions.get((directions.indexOf(current.getLabel()) + 1) % 4), grid);
            if (nextLeft != null) {
                nextLeft.setValue(current.getValue() + 1001);
                nextLeft.setPrev(current);
                pq.add(nextLeft);
            }
        }

        return nodesOnPath.size();
    }

    private static Node nextNode(Node node, char direction, char[][] grid) {
        Node next = null;
        if (direction == '>') next = new Node(node.getX(), node.getY() + 1, '>');
        else if (direction == 'v') next = new Node(node.getX() + 1, node.getY(), 'v');
        else if (direction == '<') next = new Node(node.getX(), node.getY() - 1, '<');
        else if (direction == '^') next = new Node(node.getX() - 1, node.getY(), '^');

        if (!CommonUtils.isOutOfBounds(next.getX(), next.getY(), grid) && grid[next.getX()][next.getY()] != '#')
            return next;
        else return null;
    }
}
