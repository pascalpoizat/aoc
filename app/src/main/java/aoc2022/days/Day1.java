package aoc2022.days;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import aoc2022.helpers.Day;

public class Day1 {

    private static final List<Integer> day1Helper(List<String> ls) {
        List<Integer> elves = new ArrayList<>();
        int current = 0;
        for (String e : ls) {
            if (e.trim().length() == 0) { // empty line
                elves.add(current);
                current = 0;
            } else { // calories
                int val = 0;
                try {
                    val = Integer.parseInt(e);
                } catch (Exception x) {
                }
                current += val;
            }
        }
        elves.sort(Comparator.reverseOrder());
        return elves;
    }

    public static final Day day1a = ls -> {
        return String.format("%d", day1Helper(ls).get(0));
    };

    public static final Day day1b = ls -> {
        return String.format("%d", day1Helper(ls).stream().limit(3).reduce(0, (x, y) -> x + y));
    };

}
