package aoc.helpers.contextual;

import java.util.function.Function;

@FunctionalInterface
public interface EvaluatorGenerator<C, T> extends ContextGenerator<C, Function<T, Integer>> {
}
