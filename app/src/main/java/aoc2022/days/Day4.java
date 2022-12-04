package aoc2022.days;

import aoc2022.helpers.Day;
import aoc2022.helpers.Pair;
import aoc2022.helpers.Reader;
import static aoc2022.helpers.Readers.integer;
import static aoc2022.helpers.Readers.split;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class Day4 {

    private Day4() {
    }

    public static class Range {
        private int start;
        private int end;

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

    public static final Reader<Range> rangeReader = split("-", integer,
            integer, Day4.Range::new);

    public static final Reader<Pair<Range, Range>> reader = split(",", rangeReader, rangeReader, Pair::new);

    public static final Predicate<Pair<Range,Range>> aCoversB = p -> p.fst().covers(p.snd());
    public static final Predicate<Pair<Range,Range>> bCoversA = p -> p.snd().covers(p.fst());
    public static final Predicate<Pair<Range,Range>> overlaps = p -> p.fst().overlaps(p.snd());

    public static final Function<Predicate<Pair<Range,Range>>,Day> day = p -> ls -> {
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
