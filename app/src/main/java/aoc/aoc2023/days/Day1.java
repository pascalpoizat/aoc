package aoc.aoc2023.days;

import java.util.Comparator;

import aoc.helpers.Day;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Option;

public class Day1 {

    public static final List<String> targets1 = List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    public static final List<String> targets2 = List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine");

    public static final int valueOf(String s) {
        return switch (s) {
            case "1", "one" -> 1;
            case "2", "two" -> 2;
            case "3", "three" -> 3;
            case "4", "four" -> 4;
            case "5", "five" -> 5;
            case "6", "six" -> 6;
            case "7", "seven" -> 7;
            case "8", "eight" -> 8;
            case "9", "nine" -> 9;
            default -> 0;
        };
    }

    public static final Option<Tuple2<Integer, String>> indexFromStart(String s, String target) {
        int rtr = s.indexOf(target);
        return (rtr == -1) ? Option.none() : Option.of(Tuple.of(rtr, target));
    }

    public static final Option<Tuple2<Integer, String>> indexFromEnd(String s, String target) {
        // 76four7nineeight + 7
        // thgieenin7ruof67 + 7
        String reverseS = new StringBuilder(s).reverse().toString();
        String reverseT = new StringBuilder(target).reverse().toString();
        int rtr = reverseS.indexOf(reverseT);
        return (rtr == -1) ? Option.none() : Option.of(Tuple.of(rtr, target));
    }

    public static final Comparator<Tuple2<Integer, String>> compare1 = (t1, t2) -> t1._1().compareTo(t2._1());

    public static final Option<Integer> valueFromStart(List<String> targets, String s) {
        return targets
                .flatMap(t -> Day1.indexFromStart(s, t))
                .minBy(Day1.compare1)
                .map(Tuple2::_2)
                .map(Day1::valueOf);
    }

    public static final Option<Integer> valueFromEnd(List<String> targets, String s) {
        return targets
                .flatMap(t -> Day1.indexFromEnd(s, t))
                .minBy(Day1.compare1)
                .map(Tuple2::_2)
                .map(Day1::valueOf);
    }

    public static final Option<Integer> valueFromString(List<String> targets, String s) {
        Option<Integer> n1 = valueFromStart(targets, s);
        Option<Integer> n2 = valueFromEnd(targets, s);
        if (n1.isDefined() && n2.isDefined()) {
            int nombre = n1.get() * 10 + n2.get();
            System.out.println(String.format("%s -> %d", s, nombre));
            return Option.of(nombre);
        } else {
            System.out.println(n1.isDefined());
            System.out.println(n2.isDefined());
            System.out.println(s);
            return Option.none();
        }
    }

    public static final Day day1a = ls -> String.format("%d", ls.stream()
            .map(s -> valueFromString(targets1, s))
            .filter(Option::isDefined)
            .map(Option::get)
            .reduce(0, (x,y) -> x+y));

    public static final Day day1b = ls -> String.format("%d", ls.stream()
            .map(s -> valueFromString(targets2, s))
            .filter(Option::isDefined)
            .map(Option::get)
            .reduce(0, (x,y) -> x+y));

}
