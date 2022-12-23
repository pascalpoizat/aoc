package aoc.aoc2015.days;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import aoc.helpers.Day;

import aoc.helpers.LineReader;
import aoc.helpers.ListCreator;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple4;

import static aoc.helpers.Readers.*;

public class Day15 {

    static final int MAX = 100;
    static final int OBJECTIVE = 500;

    private Day15() {
    }

    public static class ListGenerator {

        private final int max;
        private List<Integer> current;

        public ListGenerator(int n, int max) {
            this.max = max;
            this.current = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                this.current.add(0);
            }
        }

        public static Optional<List<Integer>> next(List<Integer> l, int i, int max) {
            if (l == null || i < 0 || i >= l.size()) {
                return Optional.empty();
            }
            List<Integer> newL = new ArrayList<>(l);
            if (l.get(i) >= max) {
                newL.set(i, 0);
                return next(newL, i + 1, max);
            }
            newL.set(i, l.get(i) + 1);
            return Optional.of(newL);
        }

        private Optional<List<Integer>> next() {
            return next(current, 0, max);
        }

        public Optional<List<Integer>> generate() {
            Optional<List<Integer>> rtr = Optional.ofNullable(current);
            Optional<List<Integer>> newL = next();
            // no next possible
            current = newL.orElse(null);
            return rtr;
        }
    }

    public static final BiFunction<Integer, Integer, Supplier<Optional<List<Integer>>>> sgen =
            (n, max) -> new ListGenerator(n, max)::generate;

    public record Ingredient(String name, int capacity, int durability, int flavor, int texture, int calories) {
    }

    public static final Function<Tuple2<Ingredient, Integer>, Tuple4<Integer, Integer, Integer, Integer>> compute = t -> Tuple
            .of(t._1().capacity * t._2(), t._1().durability * t._2(), t._1().flavor * t._2(), t._1().texture * t._2());

    public static int score(List<Tuple2<Ingredient, Integer>> doses) {
        Tuple4<Integer, Integer, Integer, Integer> t = doses.stream().map(compute).reduce(Tuple.of(0, 0, 0, 0),
                (t1, t2) -> Tuple.of(t1._1() + t2._1(), t1._2() + t2._2(), t1._3() + t2._3(), t1._4() + t2._4()));
        int capacity = t._1() < 0 ? 0 : t._1();
        int durability = t._2() < 0 ? 0 : t._2();
        int flavor = t._3() < 0 ? 0 : t._3();
        int texture = t._4() < 0 ? 0 : t._4();
        return capacity * durability * flavor * texture;
    }

    public static int calories(List<Tuple2<Ingredient, Integer>> doses) {
        return doses.stream().map(t -> t._1().calories * t._2()).reduce(0, Integer::sum);
    }

    public static ListCreator<Ingredient> ingredientCreator = ls -> {
        if (ls != null && ls.size() == 6) {
            Optional<String> name = id.apply(ls.get(0));
            Optional<Integer> capacity = integer.apply(ls.get(1));
            Optional<Integer> durability = integer.apply(ls.get(2));
            Optional<Integer> flavor = integer.apply(ls.get(3));
            Optional<Integer> texture = integer.apply(ls.get(4));
            Optional<Integer> calories = integer.apply(ls.get(5));
            if (name.isPresent() && capacity.isPresent() && durability.isPresent() && flavor.isPresent() && texture.isPresent() && calories.isPresent()) {
                return Optional.of(new Ingredient(name.get(), capacity.get(), durability.get(), flavor.get(), texture.get(), calories.get()));
            }
        }
        return Optional.empty();
    };

    public static LineReader<Ingredient> ingredientReader = regex("([^:]+): capacity (-?[\\d]+), durability (-?[\\d]+), flavor (-?[\\d]+), texture (-?[\\d]+), calories (-?[\\d]+)", ingredientCreator);

    public static <A, B> List<Tuple2<A, B>> zip(List<A> as, List<B> bs) {
        List<Tuple2<A, B>> rtr = new ArrayList<>();
        for (int i = 0; i < Math.min(as.size(), bs.size()); i++) {
            rtr.add(Tuple.of(as.get(i), bs.get(i)));
        }
        return rtr;
    }

    public static final Day day15a = ls -> {
        List<Ingredient> ingredients = ls.stream().map(ingredientReader).flatMap(Optional::stream).toList();
        System.out.println(ingredients);
        Supplier<Optional<List<Integer>>> supplier = sgen.apply(ingredients.size(), MAX);
        return Stream.generate(supplier)
                .takeWhile(Optional::isPresent)
                .flatMap(Optional::stream)
                .filter(l -> (l.stream().reduce(0, Integer::sum)) == MAX)
                .map(l -> zip(ingredients, l))
                .map(Day15::score)
                .max(Integer::compareTo)
                .orElse(0)
                .toString();
    };

    public static final Day day15b = ls -> {
        List<Ingredient> ingredients = ls.stream().map(ingredientReader).flatMap(Optional::stream).toList();
        System.out.println(ingredients);
        Supplier<Optional<List<Integer>>> supplier = sgen.apply(ingredients.size(), MAX);
        return Stream.generate(supplier)
                .takeWhile(Optional::isPresent)
                .flatMap(Optional::stream)
                .filter(l -> (l.stream().reduce(0, Integer::sum)) == MAX)
                .map(l -> zip(ingredients, l))
                .filter(l -> calories(l) == OBJECTIVE)
                .map(Day15::score)
                .max(Integer::compareTo)
                .orElse(0)
                .toString();
    };

}