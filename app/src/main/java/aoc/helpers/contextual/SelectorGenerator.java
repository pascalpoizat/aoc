package aoc.helpers.contextual;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@FunctionalInterface
public interface SelectorGenerator<C,T> {
    Function<List<T>, Optional<Integer>> generate(C c);
}
