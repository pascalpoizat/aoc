package aoc.aoc2015.days;

import aoc.helpers.Day;
import aoc.helpers.LineReader;
import aoc.helpers.ListCreator;
import static aoc.helpers.Readers.*;

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import io.vavr.Tuple;
import io.vavr.Tuple2;

public class Day8 {

    private Day8() {
    }

    public static final ListCreator<String> createString = ls -> Optional.of(ls.get(0));

    public static final LineReader<String> readString = regex("(.*)", createString);

    public static final Function<String, Tuple2<Integer, Integer>> computeSizes = s -> {
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
        return Tuple.of(size1, size2);
    };

    public static final Function<String, Tuple2<Integer, Integer>> computeSizes2 = s -> {
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
        return Tuple.of(size1, size2);
    };

    public static final Tuple2<Integer, Integer> zero = Tuple.of(0, 0);

    public static final BinaryOperator<Tuple2<Integer, Integer>> computeSums = (p1, p2) -> Tuple.of(p1._1() + p2._1(),
            p1._2() + p2._2());

    public static final Day day8a = ls -> {
        Tuple2<Integer, Integer> sums = ls.stream()
                .map(readString)
                .flatMap(Optional::stream)
                .map(computeSizes)
                .reduce(zero, computeSums);
        return String.format("%d", sums._1() - sums._2());
    };

    public static final Day day8b = ls -> {
        Tuple2<Integer, Integer> sums = ls.stream()
                .map(readString)
                .flatMap(Optional::stream)
                .map(computeSizes2)
                .reduce(zero, computeSums);
        return String.format("%d", sums._2() - sums._1());
    };

}
