package aoc.helpers.contextual;

import java.util.function.Predicate;

@FunctionalInterface
public interface PredicateGenerator<C, T>  {
    Predicate<T> generate(C context);
}
