package aoc.aoc2015;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Optional;

import aoc.aoc2015.days.*;
import aoc.helpers.AoCApp;
import aoc.helpers.Day;

import io.vavr.Tuple;
import io.vavr.Tuple2;

public class App implements AoCApp {
    private final Map<String, Tuple2<String, Day>> days;

    public App() {
        days = new HashMap<>();
        days.put("1a", Tuple.of("/aoc2015/input1.txt", Day1.day1a));
        days.put("1b", Tuple.of("/aoc2015/input1.txt", Day1.day1b));
        days.put("2a", Tuple.of("/aoc2015/input2.txt", Day2.day2a));
        days.put("2b", Tuple.of("/aoc2015/input2.txt", Day2.day2b));
        days.put("3a", Tuple.of("/aoc2015/input3.txt", Day3.day3a));
        days.put("3b", Tuple.of("/aoc2015/input3.txt", Day3.day3b));
        days.put("4a", Tuple.of("/aoc2015/input4.txt", Day4.day4a));
        days.put("4b", Tuple.of("/aoc2015/input4.txt", Day4.day4b));
        days.put("5a", Tuple.of("/aoc2015/input5.txt", Day5.day5a));
        days.put("5b", Tuple.of("/aoc2015/input5.txt", Day5.day5b));
        days.put("6a", Tuple.of("/aoc2015/input6.txt", Day6.day6a));
        days.put("6b", Tuple.of("/aoc2015/input6.txt", Day6.day6b));
        days.put("7a", Tuple.of("/aoc2015/input7.txt", Day7.day7a));
        days.put("7b", Tuple.of("/aoc2015/input7.txt", Day7.day7b));
        days.put("8a", Tuple.of("/aoc2015/input8.txt", Day8.day8a));
        days.put("8b", Tuple.of("/aoc2015/input8.txt", Day8.day8b));
        days.put("9a", Tuple.of("/aoc2015/input9.txt", Day9.day9a));
        days.put("9b", Tuple.of("/aoc2015/input9.txt", Day9.day9b));
        days.put("10a", Tuple.of("/aoc2015/input10.txt", Day10.day10a));
        days.put("10b", Tuple.of("/aoc2015/input10.txt", Day10.day10b));
        days.put("11a", Tuple.of("/aoc2015/input11.txt", Day11.day11a));
        days.put("11b", Tuple.of("/aoc2015/input11.txt", Day11.day11b));
        days.put("12a", Tuple.of("/aoc2015/input12.txt", Day12.day12a));
        days.put("12b", Tuple.of("/aoc2015/input12.txt", Day12.day12b));
        days.put("13a", Tuple.of("/aoc2015/input13.txt", Day13.day13a));
        days.put("13b", Tuple.of("/aoc2015/input13.txt", Day13.day13b));
        days.put("14a", Tuple.of("/aoc2015/input14.txt", Day14.day14a));
        days.put("14b", Tuple.of("/aoc2015/input14.txt", Day14.day14b));
        days.put("15a", Tuple.of("/aoc2015/input15.txt", Day15.day15a));
        days.put("15b", Tuple.of("/aoc2015/input15.txt", Day15.day15b));
        days.put("16a", Tuple.of("/aoc2015/input16.txt", Day16.day16a));
        days.put("16b", Tuple.of("/aoc2015/input16.txt", Day16.day16b));
        days.put("17a", Tuple.of("/aoc2015/input17.txt", Day17.day17a));
        days.put("17b", Tuple.of("/aoc2015/input17.txt", Day17.day17b));
        days.put("18a", Tuple.of("/aoc2015/input18.txt", Day18.day18a));
        days.put("18b", Tuple.of("/aoc2015/input18.txt", Day18.day18b));
    }

    @Override
    public Set<String> days() {
        return days.keySet();
    }

    @Override
    public Optional<String> file(String day) {
        return Optional.ofNullable(days.get(day)._1());
    }

    @Override
    public Optional<Day> day(String day) {
        return Optional.ofNullable(days.get(day)._2());
    }
}