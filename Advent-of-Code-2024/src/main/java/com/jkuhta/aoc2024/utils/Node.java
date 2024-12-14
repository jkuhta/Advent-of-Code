package main.java.com.jkuhta.aoc2024.utils;

public class Node extends Point {
    Node up;
    Node right;
    Node down;
    Node left;

    public Node(int x, int y, char label) {
        super(x, y, label);
    }

    public void setUp(Node up) {
        this.up = up;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setDown(Node down) {
        this.down = down;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getUp() {
        return up;
    }

    public Node getRight() {
        return right;
    }

    public Node getDown() {
        return down;
    }

    public Node getLeft() {
        return left;
    }
}
