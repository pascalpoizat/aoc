package aoc.aoc2015.days;

import java.util.List;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

import aoc.helpers.Day;

public class Day5 {

    private Day5() {
    }

    public static final List<String> FORBIDDEN = List.of("ab", "cd", "pq", "xy");

    public static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";

    public static final IntPredicate isVoyel = c -> ((c=='a') || (c=='e') || (c=='i') || (c=='o') || (c=='u'));

    // |{x in word | x is a voyel}| >= 3
    public static final Predicate<String> threeVoyels = word -> word.chars().filter(isVoyel)
            .count() >= 3;

    // exists x in LETTERS such that xx in word
    public static final Predicate<String> twiceInRow = word -> LETTERS.chars()
            .anyMatch(c -> word.contains(String.format("%c%c", c, c)));

    // not exists w in FORBIDDEN such that w in word
    public static final Predicate<String> noForbidden = word -> FORBIDDEN.stream().noneMatch(word::contains);

    // exits x in LETTERS, exists y in LETTERS such that xy...xy in s
    public static final Predicate<String> containsPair = word -> {
        boolean rtr = false;
        for(int i = 0; i < word.length()-3; i++) {
            String prefix = word.substring(i, i+2);
            String suffix = word.substring(i+2);
            if(suffix.contains(prefix)) {
                rtr = true;
                break;
            }
        }
        return rtr;
    };

    // exists x in LETTERS, exists y in LETTERs such that xyx in s
    public static final Predicate<String> containsRepetition = word -> {
        boolean rtr = false;
        for(int i = 0; i < word.length()-2; i++) {
            if(word.charAt(i) == word.charAt(i+2)) {
                rtr = true;
                break;
            }
        }
        return rtr;
    };

    public static final Predicate<String> isNice = threeVoyels.and(twiceInRow).and(noForbidden);
    public static final Predicate<String> isNiceV2 = containsPair.and(containsRepetition);

    public static final Function<Predicate<String>, Day> day = p -> ls -> {
        long nb = ls.stream().filter(p).count();
        return String.format("%d", nb);
    };

    public static final Day day5a = day.apply(isNice);
    public static final Day day5b = day.apply(isNiceV2);
}
