package aoc.aoc2015.days;

import aoc.helpers.Day;
import aoc.helpers.LineReader;
import aoc.helpers.ListCreator;
import aoc.helpers.Pair;
import static aoc.helpers.Readers.*;

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class Day8 {

    public static final ListCreator<String> createString = ls -> Optional.of(ls.get(0));

    public static final LineReader<String> readString = regex("(.*)", createString);

    public static final Function<String, Pair<Integer, Integer>> computeSizes = s -> {
        int size1 = s.length();
        int size2 = 0;
        int i = 0;
        while (i < size1) {
            // \" or \\ -> size2++ and i+=2
            // \x -> size2++ and i+=4
            // otherwise size2++ and i+=1
            // and remove 2 at the end for the starting and ending "
            if (s.charAt(i) == '\\' && (i+1 < size1)) {
                if (s.charAt(i+1) == '\"' || s.charAt(i+1) == '\\') {
                    i+=2;
                } else if (s.charAt(i+1) == 'x') {
                    i+=4;
                } else {
                    i+=1;
                }
            } else {
                i+=1;
            }
            size2++;
        }
        size2-=2;
        return Pair.of(size1, size2);
    };

    public static final Function<String, Pair<Integer, Integer>> computeSizes2 = s -> {
        // \ -> size2+=2 and i++
        // " -> size2+=2 and i++
        // otherwise size2++ and i++
        // and add 2 at the end for the starting and ending " encoded as \" each
        int size1 = s.length();
        int size2 = 0;
        int i = 0;
        while(i < size1) {
            if (s.charAt(i) == '\\' || s.charAt(i) == '\"') {
                size2+=2;
            } else {
                size2++;
            }
            i++;
        }
        size2+=2;
        return Pair.of(size1, size2);
    };

    public static final Pair<Integer, Integer> zero = Pair.of(0, 0);

    public static final BinaryOperator<Pair<Integer, Integer>> computeSums = (p1, p2) -> Pair.of(p1.fst() + p2.fst(),
            p1.snd() + p2.snd());

    public static final Day day8a = ls -> {
        Pair<Integer, Integer> sums = ls.stream()
                .map(readString)
                .flatMap(Optional::stream)
                .map(computeSizes)
                .reduce(zero, computeSums);
        return String.format("%d", sums.fst() - sums.snd());
    };

    public static final Day day8b = ls -> {
        Pair<Integer, Integer> sums = ls.stream()
                .map(readString)
                .flatMap(Optional::stream)
                .map(computeSizes2)
                .reduce(zero, computeSums);
        return String.format("%d", sums.snd() - sums.fst());
    };

}
