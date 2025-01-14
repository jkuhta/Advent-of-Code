package main.java.com.jkuhta.aoc2024.utils;

public class Point {
    private final int x;
    private final int y;
    char label;
    int value;
    String labelString;

    public Point(int x, int y, char label) {
        this.x = x;
        this.y = y;
        this.label = label;
        this.value = 0;
    }

    public Point(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.label = '\0';
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.value = 0;
        this.label = '\0';
    }

    public Point(int x, int y, int value, String labelString) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.labelString = labelString;
    }

    public Point(int x, int y, char label, int value) {
        this.x = x;
        this.y = y;
        this.label = label;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getLabel() {
        return label;
    }

    public int getValue() {
        return value;
    }

    public String getLabelString() {
        return labelString;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setLabel(char label) {
        this.label = label;
    }

    public void setLabelString(String labelString) {
        this.labelString = labelString;
    }

    public int manhattanDistanceTo(Point other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(x);
        result = 31 * result + Integer.hashCode(y);
        return result;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
