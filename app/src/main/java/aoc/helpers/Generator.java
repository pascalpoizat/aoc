package aoc.helpers;

import java.util.function.UnaryOperator;

public class Generator<T> {
    private final UnaryOperator<T> next;
    private T current;

    public Generator(T zero, UnaryOperator<T> next) {
        this.next = next;
        this.current = zero;
    }

    public T next() {
        T rtr = current;
        current = next.apply(current);
        return rtr;
    }
}
