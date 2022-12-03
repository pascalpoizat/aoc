package aoc2022.days;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import aoc2022.helpers.Day;

public class Day1 {

    private Day1() {
    }

    private static final List<Integer> day1(List<String> ls) {
        List<Integer> es = new ArrayList<>();
        int current = 0;
        for (String e : ls) {
            if (e.trim().length() == 0) { // empty line
                es.add(current);
                current = 0;
            } else { // calories
                int val = 0;
                try {
                    val = Integer.parseInt(e);
                    current += val;
                } catch (Exception x) {
                    // nothing to do, skip the bad number
                }
            }
        }
        es.sort(Comparator.reverseOrder());
        return es;
    }

    public static final Day day1a = ls -> String.format("%d", day1(ls).get(0));

    public static final Day day1b = ls -> String.format("%d",
            day1(ls).stream().limit(3).reduce(0, (x, y) -> x + y));

}
