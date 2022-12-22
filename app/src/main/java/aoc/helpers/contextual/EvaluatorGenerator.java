package aoc.helpers.contextual;

import java.util.function.Function;

@FunctionalInterface
public interface EvaluatorGenerator<C,T> {
    Function<T, Integer> generate(C context);
}
