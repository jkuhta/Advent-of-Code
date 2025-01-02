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
        int rows = inputMap.length;
        int cols = inputMap[0].length();
        Node[][] grid = new Node[rows][cols];

        Node currentNode = parseMapToGraph(inputMap, nodes, true, grid);

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

        String[] inputParts = input.split("\n\n");

        String[] inputMap = inputParts[0].split("\n");
        String inputPath = inputParts[1].replaceAll("\n", "");

        Set<Node> nodes = new HashSet<>();
        int rows = inputMap.length;
        int cols = inputMap[0].length();
        Node[][] grid = new Node[rows][cols * 2];

        Node currentNode = parseMapToGraph(inputMap, nodes, false, grid);

        for (char direction : inputPath.toCharArray()) {
            currentNode = moveRobot2(currentNode, direction, grid);
            // PRINT MOVE
            // System.out.println("Move " + direction);
            // printGraph(grid, currentNode);
            for (Node[] row : grid) {
                for (Node node : row) {
                    // HACK - should be fixed
                    if (node != null && node.getLabel() == '[' && node.getRight() != null && node.getRight().getLabel() != ']'
                    ) {
                        node.getRight().setLabel(']');
                    }
                    if (
                            node != null && node.getLabel() == ']' && node.getLeft() != null && node.getLeft().getLabel() != '[') {
                        node.getLeft().setLabel('[');
                    }
                }

            }
        }

        int sum = 0;
        for (Node[] row : grid) {
            for (Node node : row) {
                if (node != null && node.getLabel() == '[') {
                    sum += node.getY() + 100 * node.getX();
                }
            }

        }
        return sum;

    }

    public static Node parseMapToGraph(String[] map, Set<Node> nodes, boolean simple, Node[][] grid) {

        int rows = map.length;
        int cols = map[0].length();
        Node startNode = null;

        // Create points for non-wall cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map[i].charAt(j) != '#') {
                    char label = map[i].charAt(j);
                    if (simple) {
                        Node node = new Node(i, j, label == '@' ? '.' : label);
                        grid[i][j] = node;
                        nodes.add(node);
                        if (label == '@') startNode = node;

                    } else {
                        Node node = new Node(i, 2 * j, label == 'O' ? '[' : '.');
                        Node node2 = new Node(i, 2 * j + 1, label == 'O' ? ']' : '.');
                        grid[i][2 * j] = node;
                        grid[i][2 * j + 1] = node2;
                        if (label == '@') startNode = node;
                    }
                }
            }
        }

        // Link points
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < (simple ? cols : cols * 2); j++) {
                if (grid[i][j] != null) {
                    // Link to the node above
                    if (i > 0 && grid[i - 1][j] != null) grid[i][j].setUp(grid[i - 1][j]);

                    // Link to the node to the right
                    if (j < (simple ? cols - 1 : cols * 2 - 1) && grid[i][j + 1] != null)
                        grid[i][j].setRight(grid[i][j + 1]);

                    // Link to the node below
                    if (i < rows - 1 && grid[i + 1][j] != null) grid[i][j].setDown(grid[i + 1][j]);

                    // Link to the node to the left
                    if (j > 0 && grid[i][j - 1] != null) grid[i][j].setLeft(grid[i][j - 1]);
                }
            }
        }
        return startNode;
    }

    public static Node moveRobot(Node node, char direction) {
        Node nextNode = getNextNode(node, direction);
        if (nextNode == null) return node;

        if (nextNode.getLabel() == '.') {
            return nextNode;
        } else if (nextNode.getLabel() == 'O') {
            if (moveSimpleBox(nextNode, direction)) {
                nextNode.setLabel('.');
                return nextNode;
            }
        }
        return node;
    }

    public static Node moveRobot2(Node node, char direction, Node[][] grid) {
        Node nextNode = getNextNode(node, direction);
        if (nextNode == null) return node;

        if (nextNode.getLabel() == '.') {
            return nextNode;
        } else if ((direction == '<' || direction == '>') && (nextNode.getLabel() == '[' || nextNode.getLabel() == ']')) {
            if (canPushHorizontally(nextNode, direction)) {
                moveBoxHorizontally(node, direction);
                return nextNode;
            }
        } else if ((direction == '^' || direction == 'v') && (nextNode.getLabel() == '[' || nextNode.getLabel() == ']')) {
            if (canPushVertically(nextNode, direction)) {
                Set<Node> movedNodes = new HashSet<>();
                moveBoxVertically2(nextNode, '.', direction, grid, false, false, movedNodes);
                return nextNode;
            }
        }
        return node;
    }

    private static Node getNextNode(Node node, char direction) {
        return switch (direction) {
            case '^' -> node.getUp();
            case '>' -> node.getRight();
            case 'v' -> node.getDown();
            case '<' -> node.getLeft();
            default -> null;
        };
    }

    public static boolean moveSimpleBox(Node node, char direction) {
        if (node == null) return false;

        if (node.getLabel() == '.') {
            node.setLabel('O');
            return true;
        }

        if (direction == '^') return moveSimpleBox(node.getUp(), direction);
        if (direction == '>') return moveSimpleBox(node.getRight(), direction);
        if (direction == 'v') return moveSimpleBox(node.getDown(), direction);
        if (direction == '<') return moveSimpleBox(node.getLeft(), direction);

        return false;
    }

    public static boolean canPushHorizontally(Node node, char direction) {
        if (node == null) return false;

        if (node.getLabel() == '.') {
            return true;
        }

        if (direction == '>') return canPushHorizontally(node.getRight(), direction);
        if (direction == '<') return canPushHorizontally(node.getLeft(), direction);

        return false;
    }

    public static boolean canPushVertically(Node node, char direction) {
        if (node == null) return false;

        if (node.getLabel() == '.') {
            return true;
        }

        if (node.getLabel() == '[') {
            if (direction == '^')
                return canPushVertically(node.getUp(), direction) && canPushVertically(node.getRight().getUp(), direction);
            if (direction == 'v')
                return canPushVertically(node.getDown(), direction) && canPushVertically(node.getRight().getDown(), direction);
        } else if (node.getLabel() == ']') {
            if (direction == '^')
                return canPushVertically(node.getUp(), direction) && canPushVertically(node.getLeft().getUp(), direction);
            if (direction == 'v')
                return canPushVertically(node.getDown(), direction) && canPushVertically(node.getLeft().getDown(), direction);
        }
        return false;
    }

    public static void moveBoxHorizontally(Node node, char direction) {
        Node current = node;
        Node next;
        char prevLabel = '.';

        while (true) {
            next = getNextNode(current, direction);
            if (next == null || next.getLabel() == '#') {
                break;
            }
            if (next.getLabel() == '.') {
                next.setLabel(prevLabel);
                break;
            }
            char tempLabel = next.getLabel();
            next.setLabel(prevLabel);
            prevLabel = tempLabel;
            current = next;
        }
    }

    public static void moveBoxVertically2(Node node, char label, char direction, Node[][] grid, boolean checkedLeft, boolean checkedRight, Set<Node> movedNodes) {
        if (node == null) return;
        if (movedNodes.contains(node)) return;
        movedNodes.add(node);

        if (!checkedLeft && !movedNodes.contains(node.getLeft()) && node.getLabel() == ']' && node.getLeft() != null) {
            Node leftNode = node.getLeft();
            if (leftNode.getLabel() != '.') {
                moveBoxVertically2(leftNode, '.', direction, grid, false, true, movedNodes);
            }
        }

        if (!checkedRight && !movedNodes.contains(node.getRight()) && node.getLabel() == '[' && node.getRight() != null) {
            Node rightNode = node.getRight();
            if (rightNode.getLabel() != '.') {
                moveBoxVertically2(rightNode, '.', direction, grid, true, false, movedNodes);
            }
        }

        if (node.getLabel() == '.') {
            node.setLabel(label);
        } else {
            moveBoxVertically2(getNextNode(node, direction), node.getLabel(), direction, grid, node.getLeft() != null && node.getLeft().getLabel() != '.' && checkedLeft, node.getRight() != null && node.getRight().getLabel() != '.' && checkedRight, movedNodes);
            node.setLabel(label);
        }
    }

    public static void printGraph(Node[][] grid, Node currentNode) {
        for (Node[] nodes : grid) {
            for (Node node : nodes) {
                if (node == null) {
                    System.out.print("#");
                } else {
                    char label = node.getLabel();
                    if (node == currentNode) {
                        System.out.print("@");
                    } else {
                        System.out.print(label);
                    }
                }
            }
            System.out.println();
        }
    }
}