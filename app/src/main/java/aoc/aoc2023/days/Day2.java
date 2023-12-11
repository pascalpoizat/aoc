package aoc.aoc2023.days;

import static aoc.helpers.Readers.indexedPartReader;
import static aoc.helpers.Readers.integerReader;
import static aoc.helpers.Readers.regex;
import static aoc.helpers.Readers.split2Reader;
import static aoc.helpers.Readers.splitNReader;

import java.util.Optional;

import aoc.helpers.Day;
import aoc.helpers.LineReader;
import aoc.helpers.ListCreator;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;

public class Day2 {

    private Day2() {}

    public enum Color {
        RED, GREEN, BLUE;
    }

    public static record Reveal(Map<Color, Integer> values) {
        public boolean worksWithAvailableMap(Map<Color, Integer> am) {
            return values.forAll(t -> am.containsKey(t._1()) && am.get(t._1()).get() >= t._2());
        }
    }

    public static class Game {
        private int number;
        private List<Reveal> reveals;

        public Game(int number, List<Reveal> reveals) {
            this.number = number;
            this.reveals = List.ofAll(reveals);
        }

        public int number() {
            return number;
        }

        public boolean worksWithAvailableMap(Map<Color, Integer> am) {
            return reveals.forAll(r -> r.worksWithAvailableMap(am));
        }

        public Map<Color, Integer> minimalWorkingMap() {
            Map<Color, Integer> minimal = HashMap.of(Color.RED, 0, Color.GREEN, 0, Color.BLUE, 0);
            for(Reveal r : reveals) {
                for (Tuple2<Color, Integer> t : r.values) {
                    if (minimal.get(t._1()).get() < t._2()) {
                        minimal = minimal.put(t._1(), t._2());
                    }
                }
            }
            return minimal;
        }

        public int power() {
            Map<Color, Integer> minimal = minimalWorkingMap();
            return minimal.valuesIterator().product().intValue();
        }

    }

    // format
    // Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red

    public static final ListCreator<Tuple2<Color, Integer>> coupleCreator = ls -> {
        if (ls == null || ls.size() != 2) {
            return Optional.empty();
        }
        Optional<Integer> qty = integerReader.apply(ls.get(0));
        Optional<Color> color = switch (ls.get(1)) {
            case "red" -> Optional.of(Color.RED);
            case "green" -> Optional.of(Color.GREEN);
            case "blue" -> Optional.of(Color.BLUE);
            default -> Optional.empty();
        };
        if (qty.isPresent() && color.isPresent()) {
            return Optional.of(Tuple.of(color.get(), qty.get()));
        } else {
            return Optional.empty();
        }
    };

    public static final ListCreator<Reveal> revealCreator = ls -> {
        Map<Color, Integer> values = List.ofAll(ls)
                .map(regex(" ([\\d]+) ([^ ]+)", coupleCreator))
                .filter(Optional::isPresent)
                .toMap(t -> t.get());
        return Optional.of(new Reveal(values));
    };

    public static final ListCreator<List<Reveal>> revealListCreator = ls -> Optional
            .of(List.ofAll(ls.stream().map(splitNReader(",", revealCreator)).flatMap(Optional::stream).toList()));

    public static final LineReader<List<Reveal>> recordReader = splitNReader(";", revealListCreator);

    public static final LineReader<Game> gameReader = split2Reader(":", indexedPartReader("Game", " "), recordReader, Game::new);

    public static final Map<Color, Integer> available = HashMap.of(Color.RED, 12, Color.GREEN, 13, Color.BLUE, 14);

    public static final Day day2a = ls -> String.format("%d", 
        ls.stream().map(gameReader)
        .flatMap(Optional::stream)
        .filter(g -> g.worksWithAvailableMap(available))
        .map(Game::number)
        .reduce(0, (x,y) -> x+y)
    );

    public static final Day day2b = ls -> String.format("%d",
        ls.stream().map(gameReader)
        .flatMap(Optional::stream)
        .map(Game::power)
        .reduce(0, (x,y) -> x+y)
    );

}
