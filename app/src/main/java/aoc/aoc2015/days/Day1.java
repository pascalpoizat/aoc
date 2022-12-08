package aoc.aoc2015.days;

import java.util.List;

import aoc.helpers.Day;

public class Day1 {

    private Day1() {
    }

    public static int charToStep(int c) {
        return switch (c) {
            case '(' -> 1;
            case ')' -> -1;
            default -> 0;
        };
    }

    public static final Day day1a = ls -> {
        int floor = ls.get(0).chars().map(Day1::charToStep).sum();
        return String.format("%s", floor);
    };

    public static final Day day1b = ls -> {
        List<Integer> steps = ls.get(0).chars().map(Day1::charToStep).boxed().toList();
        int floor = 0;
        int rank = 0;
        for (int step : steps) {
            rank++;
            floor += step;
            if (floor < 0) {
                return String.format("%d", rank);
            }
        }
        return "none";
    };
}