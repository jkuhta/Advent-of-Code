package main.java.com.jkuhta.aoc2024;

import main.java.com.jkuhta.aoc2024.utils.FileUtils;
import main.java.com.jkuhta.aoc2024.utils.Node;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Day15 {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile("15");
        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    public static int solvePart1(String input) {

        String[] inputParts = input.split("\n\n");

        String[] inputMap = inputParts[0].split("\n");
        String inputPath = inputParts[1].replaceAll("\n", "");

        Set<Node> nodes = new HashSet<>();
        Node currentNode = parseMapToGraph(inputMap, nodes);

        for (char direction : inputPath.toCharArray()) {
            currentNode = moveRobot(currentNode, direction);
        }

        int sum = 0;

        for (Node node : nodes) {
            if (node.getLabel() == 'O') {
                sum += node.getY() + 100 * node.getX();
            }
        }
        return sum;
    }

    public static int solvePart2(String input) {
        return 0;
    }

    public static Node parseMapToGraph(String[] map, Set<Node> nodes) {
        int rows = map.length;
        int cols = map[0].length();
        Node[][] grid = new Node[rows][cols];

        Node startNode = null;

        // Create points for non-wall cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map[i].charAt(j) != '#') {
                    char label = map[i].charAt(j);
                    Node node = new Node(i, j, label == '@' ? '.' : label);
                    grid[i][j] = node;
                    nodes.add(node);
                    if (label == '@') startNode = node;
                }
            }
        }

        // Link points
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] != null) {
                    if (i > 0 && grid[i - 1][j] != null) grid[i][j].setUp(grid[i - 1][j]);
                    if (j < cols - 1 && grid[i][j + 1] != null) grid[i][j].setRight(grid[i][j + 1]);
                    if (i < rows - 1 && grid[i + 1][j] != null) grid[i][j].setDown(grid[i + 1][j]);
                    if (j > 0 && grid[i][j - 1] != null) grid[i][j].setLeft(grid[i][j - 1]);
                }
            }
        }
        return startNode;
    }

    public static Node moveRobot(Node node, char direction) {

        if (direction == '^') {
            if (node.getUp() == null) return node;
            else if (node.getUp().getLabel() == '.') return node.getUp();
            else if (node.getUp().getLabel() == 'O') {
                if (moveBox(node.getUp(), direction)) {
                    node.getUp().setLabel('.');
                    return node.getUp();
                } else {
                    return node;
                }
            }
        } else if (direction == '>') {
            if (node.getRight() == null) return node;
            else if (node.getRight().getLabel() == '.') return node.getRight();
            else if (node.getRight().getLabel() == 'O') {
                if (moveBox(node.getRight(), direction)) {
                    node.getRight().setLabel('.');
                    return node.getRight();
                } else {
                    return node;
                }
            }
        } else if (direction == 'v') {
            if (node.getDown() == null) return node;
            else if (node.getDown().getLabel() == '.') return node.getDown();
            else if (node.getDown().getLabel() == 'O') {
                if (moveBox(node.getDown(), direction)) {
                    node.getDown().setLabel('.');
                    return node.getDown();
                } else {
                    return node;
                }
            }
        } else {
            if (node.getLeft() == null) return node;
            else if (node.getLeft().getLabel() == '.') return node.getLeft();
            else if (node.getLeft().getLabel() == 'O') {
                if (moveBox(node.getLeft(), direction)) {
                    node.getLeft().setLabel('.');
                    return node.getLeft();
                } else {
                    return node;
                }
            }
        }

        return node;
    }

    public static boolean moveBox(Node node, char direction) {
        if (node == null) return false;

        if (node.getLabel() == '.') {
            node.setLabel('O');
            return true;
        }

        if (direction == '^') return moveBox(node.getUp(), direction);
        if (direction == '>') return moveBox(node.getRight(), direction);
        if (direction == 'v') return moveBox(node.getDown(), direction);
        if (direction == '<') return moveBox(node.getLeft(), direction);

        return false;
    }
}