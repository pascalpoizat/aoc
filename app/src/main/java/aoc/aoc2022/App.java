/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package aoc.aoc2022;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import aoc.aoc2022.days.*;
import aoc.helpers.AoCApp;
import aoc.helpers.Day;

import io.vavr.Tuple;
import io.vavr.Tuple2;

public class App implements AoCApp {
    private final Map<String, Tuple2<String, Day>> days = Map.of(
            "1a", Tuple.of("/aoc2022/input1.txt", Day1.day1a),
            "1b", Tuple.of("/aoc2022/input1.txt", Day1.day1b),
            "2a", Tuple.of("/aoc2022/input2.txt", Day2.day2a),
            "2b", Tuple.of("/aoc2022/input2.txt", Day2.day2b),
            "3a", Tuple.of("/aoc2022/input3.txt", Day3.day3a),
            "3b", Tuple.of("/aoc2022/input3.txt", Day3.day3b),
            "4a", Tuple.of("/aoc2022/input4.txt", Day4.day4a),
            "4b", Tuple.of("/aoc2022/input4.txt", Day4.day4b));

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
