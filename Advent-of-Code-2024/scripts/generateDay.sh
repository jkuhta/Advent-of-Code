#!/bin/bash

DAY="$1"

# Set the Java package and class paths
JAVA_PACKAGE="main.java.com.jkuhta.aoc2024"
JAVA_CLASS="Day${DAY}"
JAVA_PATH="../src/${JAVA_PACKAGE//.//}/${JAVA_CLASS}.java"

# Set the path to the input file
TXT_PATH="../src/main/resources/day${DAY}-input.txt"

# Create the Java file with the necessary code
echo "package ${JAVA_PACKAGE};

import ${JAVA_PACKAGE}.utils.FileUtils;

import java.io.IOException;

public class ${JAVA_CLASS} {
    public static void main(String[] args) throws IOException {
        String input = FileUtils.readFile(\"${DAY}\");
        System.out.println(\"Part 1: \" + solvePart1(input));
        System.out.println(\"Part 2: \" + solvePart2(input));
    }

    public static int solvePart1(String input) {
        return 0;
    }

    public static int solvePart2(String input) {
        return 0;
    }
}" > "$JAVA_PATH"

# Create the input file
touch "$TXT_PATH"

# Print confirmation message
echo "Java file ${JAVA_PATH} and txt file ${TXT_PATH} have been created."