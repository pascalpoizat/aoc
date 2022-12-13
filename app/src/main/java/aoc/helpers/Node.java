package aoc.helpers;

import java.util.List;

public class Node<T> implements Tree<T> {
    private T value;
    private List<Tree<T>> children;

    public Node(T value, List<Tree<T>> children) {
        this.value = value;
        this.children = children;
    }

    public List<Tree<T>> children() {
        return children;
    }

    @Override
    public T value() {
        return value;
    }
    
}
