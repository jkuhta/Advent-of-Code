package main.java.com.jkuhta.aoc2024.utils;

public class Node extends Point {

    int distance;
    Node up;
    Node right;
    Node down;
    Node left;
    Node prev;

    public Node(int x, int y, char label) {
        super(x, y, label);
    }

    public Node(int x, int y, char label, int value) {
        super(x, y, label, value);
    }

    public Node(int x, int y, int distance) {
        super(x, y);
        this.distance = distance;
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

    public void setPrev(Node prev) {
        this.prev = prev;
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

    public Node getPrev() {
        return prev;
    }

    public int getDistance() {
        return this.distance;
    }
}
