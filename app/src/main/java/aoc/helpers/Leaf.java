package aoc.helpers;

public class Leaf<T> implements Tree<T> {
    private final T value;

    public Leaf(T value) {
        this.value = value;
    }

    @Override
    public T value() {
        return value;
    }
    
}
