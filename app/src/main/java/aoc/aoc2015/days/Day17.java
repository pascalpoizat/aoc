package aoc.aoc2015.days;

import aoc.helpers.Day;
import io.vavr.Tuple2;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static aoc.helpers.ListGenerator.supplier;
import static aoc.helpers.Maths.zip;
import static aoc.helpers.Readers.integer;

public class Day17 {

    private Day17() {
    }

    public static final int OBJECTIVE = 150;

    public static boolean isValid(List<Integer> containers, List<Boolean> selected, int quantity) {
        List<Tuple2<Integer,Boolean>> selection = zip(containers, selected);
        return selection.stream().filter(Tuple2::_2).map(Tuple2::_1).reduce(0, Integer::sum) == quantity;
    }

    public static Day day17a = ls -> {
        List<Integer> containers = ls.stream().map(integer).flatMap(Optional::stream).toList();
        Supplier<Optional<List<Integer>>> s = supplier.apply(containers.size(), 1);
        long nb = Stream.generate(s)
                .takeWhile(Optional::isPresent)
                .flatMap(Optional::stream)
                .map(l -> l.stream().map(i -> !i.equals(0)).toList())
                .filter(l -> isValid(containers, l, OBJECTIVE))
                .count();
        return String.format("%d", nb);
    };

    public static Day day17b = ls -> {
        List<Integer> containers = ls.stream().map(integer).flatMap(Optional::stream).toList();
        Supplier<Optional<List<Integer>>> s = supplier.apply(containers.size(), 1);
        List<List<Boolean>> selections = Stream.generate(s)
                .takeWhile(Optional::isPresent)
                .flatMap(Optional::stream)
                .map(l -> l.stream().map(i -> !i.equals(0)).toList())
                .filter(l -> isValid(containers, l, OBJECTIVE))
                .toList();
        Function<List<Boolean>, Long> nbTrue = l -> l.stream().filter(b -> b).count();
        Optional<Long> nb = selections.stream()
                .map(nbTrue)
                .min(Long::compareTo);
        return nb.map(n -> selections.stream().map(nbTrue).filter(x -> x.equals(n)).count()).orElse(0L).toString();
    };

}
