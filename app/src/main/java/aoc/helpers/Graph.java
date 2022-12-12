package aoc.helpers;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;

import io.vavr.Tuple;
import io.vavr.Tuple2;

public class Graph<T, U, V> {
    private Map<T, U> nodes;
    private Map<T, Map<T, V>> edges;
    boolean uniqueLabel;
    private final Generator<T> g;

    public Graph(Generator<T> g, boolean uniqueLabel) {
        this.nodes = new HashMap<>();
        this.edges = new HashMap<>();
        this.uniqueLabel = uniqueLabel;
        this.g = g;
    }

    public boolean hasNode(T id) {
        return nodes.containsKey(id);
    }

    public boolean hasLabel(U label) {
        return nodes.keySet().stream().anyMatch(id -> nodes.get(id).equals(label));
    }

    public boolean hasEdge(T from, T to) {
        return edges.containsKey(from) && edges.get(from).containsKey(to);
    }

    public Set<T> nodes() {
        return nodes.keySet();
    }

    public Set<Tuple2<T, T>> edges() {
        return edges.keySet().stream()
                .flatMap(from -> edges.get(from).keySet().stream().map(to -> Tuple.of(from, to)))
                .collect(Collectors.toSet());
    }

    public Optional<U> label(T id) {
        return Optional.ofNullable(nodes.get(id));
    }

    public Set<T> fromLabel(U label) {
        return nodes.keySet().stream().filter(n -> nodes.get(n).equals(label)).collect(Collectors.toSet());
    }

    public Optional<V> label(T from, T to) {
        if (hasEdge(from, to)) {
            return Optional.ofNullable(edges.get(from).get(to));
        } else {
            return Optional.empty();
        }
    }

    public T createNode(U label) {
        if (uniqueLabel && hasLabel(label)) {
            return fromLabel(label).stream().toList().get(0);
        }
        T id = g.next();
        nodes.put(id, label);
        edges.put(id, new HashMap<>());
        return id;
    }

    public T getOrCreateNode(U label) {
        return fromLabel(label).stream().findFirst().orElse(createNode(label));
    }

    public boolean createEdge(T from, T to, V value) {
        if (!hasNode(from) || !hasNode(to) || hasEdge(from, to)) {
            return false;
        }
        edges.get(from).computeIfAbsent(to, k -> value);
        return true;
    }

    @Override
    public String toString() {
        return this.edges().stream()
                .map(e -> {
                    T from = e._1();
                    T to = e._2();
                    Optional<U> labelFrom = label(from);
                    Optional<U> labelTo = label(to);
                    Optional<V> labelEdge = label(from, to);
                    if (labelFrom.isPresent() && labelTo.isPresent() && labelEdge.isPresent())
                        return String.format("%s to %s = %s",
                                labelFrom.get(),
                                labelTo.get(),
                                labelEdge.get());
                    else
                        return String.format("%s to %s = %s", from, to, "?");
                })
                .collect(Collectors.joining("\n"));
    }
}
