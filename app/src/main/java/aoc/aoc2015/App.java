package aoc.aoc2015;

import java.util.Map;
import java.util.Set;
import java.util.Optional;

import aoc.aoc2015.days.*;
import aoc.helpers.AoCApp;
import aoc.helpers.Day;
import aoc.helpers.Pair;

public class App implements AoCApp {
    private Map<String, Pair<String, Day>> days = Map.of(
            "1a", Pair.of("/aoc2015/input1.txt", Day1.day1a),
            "1b", Pair.of("/aoc2015/input1.txt", Day1.day1b),
            "2a", Pair.of("/aoc2015/input2.txt", Day2.day2a),
            "2b", Pair.of("/aoc2015/input2.txt", Day2.day2b),
            "3a", Pair.of("/aoc2015/input3.txt", Day3.day3a),
            "3b", Pair.of("/aoc2015/input3.txt", Day3.day3b),
            "4a", Pair.of("/aoc2015/input4.txt", Day4.day4a),
            "4b", Pair.of("/aoc2015/input4.txt", Day4.day4b)
    );

    @Override
    public Set<String> days() {
        return days.keySet();
    }

    @Override
    public Optional<String> file(String day) {
        return Optional.ofNullable(days.get(day).fst());
    }

    @Override
    public Optional<Day> day(String day) {
        return Optional.ofNullable(days.get(day).snd());
    }
}