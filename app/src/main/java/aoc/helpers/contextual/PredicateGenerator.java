package aoc.helpers.contextual;

import java.util.function.Predicate;

@FunctionalInterface
public interface PredicateGenerator<C, T> extends ContextGenerator<C, Predicate<T>> {
}
