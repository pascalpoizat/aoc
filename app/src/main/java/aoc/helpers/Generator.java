package aoc.helpers;

import java.util.function.UnaryOperator;

public class Generator<T> {
    private T zero;
    private UnaryOperator<T> next;
    private T current;

    public Generator(T zero, UnaryOperator<T> next) {
        this.zero = zero;
        this.next = next;
        this.current = this.zero;
    }

    public T next() {
        T rtr = current;
        current = next.apply(current);
        return rtr;
    }
}
