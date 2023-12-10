package aoc.aoc2022.days;

import static aoc.helpers.Readers.integerReader;
import static aoc.helpers.Readers.split2Reader;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import aoc.helpers.Day;
import aoc.helpers.LineReader;

import io.vavr.Tuple;
import io.vavr.Tuple2;

public class Day4 {

    private Day4() {
    }

    public static class Range {
        private final int start;
        private final int end;

        public Range(Integer start, Integer end) {
            this.start = start;
            this.end = end;
        }

        //  X-----------X
        //     X---X
        public boolean covers(Range other) {
            return this.start <= other.start && this.end >= other.end;
        }

        //  X-----------X
        //      X----------X
        public boolean overlaps(Range other) {
            return (this.start <= other.start && this.end >= other.start)
                        || (other.start <= this.start && other.end >= this.start);
        }

        @Override
        public String toString() {
            return String.format("%d - %d", start, end);
        }
    }

    public static final LineReader<Range> rangeReader = split2Reader("-", integerReader,
            integerReader, Day4.Range::new);

    public static final LineReader<Tuple2<Range, Range>> reader = split2Reader(",", rangeReader, rangeReader, Tuple::of);

    public static final Predicate<Tuple2<Range,Range>> aCoversB = p -> p._1().covers(p._2());
    public static final Predicate<Tuple2<Range,Range>> bCoversA = p -> p._2().covers(p._1());
    public static final Predicate<Tuple2<Range,Range>> overlaps = p -> p._1().overlaps(p._2());

    public static final Function<Predicate<Tuple2<Range,Range>>,Day> day = p -> ls -> {
        final long count = ls.stream()
                .map(reader)
                .flatMap(Optional::stream)
                .filter(p)
                .count();
        return String.format("%d", count);
    };

    public static final Day day4a = day.apply(aCoversB.or(bCoversA));
    public static final Day day4b = day.apply(overlaps);

}
