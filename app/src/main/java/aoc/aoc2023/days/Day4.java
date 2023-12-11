package aoc.aoc2023.days;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.Optional;

import static aoc.helpers.Readers.split2Reader;
import static aoc.helpers.Readers.indexedPartReader;
import static aoc.helpers.Readers.integerReader;
import static aoc.helpers.Readers.listReader;
import aoc.helpers.Day;
import aoc.helpers.LineReader;
import io.vavr.Tuple;
import io.vavr.Tuple2;

public class Day4 {

    private Day4() {
    }

    public static record Card(int id, List<Integer> winNumbers, List<Integer> cardNumbers) {

        public static final LineReader<Card> reader = split2Reader(":",
                indexedPartReader("Card", "[ ]+"),
                split2Reader("\\|",
                        listReader(" ", integerReader),
                        listReader(" ", integerReader),
                        Tuple2::new),
                (id, t) -> new Card(id, t._1(), t._2()));

        public long value() {
            return cardNumbers.stream().filter(winNumbers::contains).count();
        }

        public long score() {
            if (value() > 0)
                return (long) Math.pow(2, value() - 1);
            else
                return 0;

        }
    }

    public static final Day day4a = ls -> ls.stream()
            .map(Card.reader)
            .flatMap(Optional::stream)
            .map(Card::score)
            .reduce(0L, Long::sum)
            .toString();

    public static final Day day4b = ls -> {
        ToIntFunction<Card> keyMapper = Card::id;
        Function<Card, Tuple2<Card, Long>> valueMapper = c -> Tuple.of(c, 1L);
        Map<Integer, Tuple2<Card, Long>> cards = new HashMap<>();
        ls.stream()
                .map(Card.reader)
                .flatMap(Optional::stream)
                .map(c -> new SimpleEntry<>(keyMapper.applyAsInt(c), valueMapper.apply(c)))
                .forEach(e -> cards.put(e.getKey(), e.getValue()));
        for (Tuple2<Card, Long> t : cards.values()) {
            int thisCard = t._1().id();
            long nbWin = t._1().value();
            for (int i = 1; i <= nbWin; i++) {
                cards.computeIfPresent(thisCard + i, (k, t2) -> t2.map2(n -> n + t._2()));
            }
        }
        return cards.values().stream().map(Tuple2::_2).reduce(0L, Long::sum).toString();
    };

}
