package aoc.helpers.contextual;

import java.util.function.Function;

@FunctionalInterface
public interface ContextGenerator<C,T> extends Function<C,T> {
    @Override
    default T apply(C context) {
        return generate(context);
    }

    T generate(C context);
}
