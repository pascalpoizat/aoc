package aoc.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ListGenerator {

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

    public static final BiFunction<Integer, Integer, Supplier<Optional<List<Integer>>>> supplier =
            (n, max) -> new ListGenerator(n, max)::generate;

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
