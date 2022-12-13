package aoc.aoc2015.days;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import aoc.helpers.Generator;
import aoc.helpers.Graph;
import aoc.helpers.LineReader;
import aoc.helpers.ListCreator;
import static aoc.helpers.Readers.*;

import aoc.helpers.Day;
import io.vavr.Tuple;
import io.vavr.Tuple3;

public class Day9 {

    private Day9() {
    }

    // the problem is finding the shortest Hamiltonian path
    // use bitmasking and dynamic programming ?
    // use extra node connected to anyone and then travelling salesman ?
    // use ugly solution for small graphs ? -> YES :-)

    public static final void swap(List<Integer> values, int i, int j) {
        Integer aux = values.get(i);
        values.set(i, values.get(j));
        values.set(j, aux);
    }

    // https://en.wikipedia.org/wiki/Heap%27s_algorithm
    public static final List<List<Integer>> permutations(List<Integer> values) {
        List<List<Integer>> rtr = new ArrayList<>();
        List<Integer> A = new ArrayList<>(values);
        int n = values.size();
        int[] c = new int[n];
        for (int i = 0; i < n; i++) {
            c[i] = 0;
        }
        rtr.add(new ArrayList<>(A));
        int i = 1;
        while (i < n) {
            if (c[i] < i) {
                if (i % 2 == 0) {
                    swap(A, 0, i);
                } else {
                    swap(A, c[i], i);
                }
                rtr.add(new ArrayList<>(A));
                c[i] += 1;
                i = 1;
            } else {
                c[i] = 0;
                i += 1;
            }
        }
        return rtr;
    }

    public static final Integer pathValue(Graph<Integer, String, Integer> g, List<Integer> path) {
        int value = 0;
        int size = path.size();
        for (int i = 0; i < size - 1; i++) {
            value = value + g.label(path.get(i), path.get(i + 1)).orElse(0);
        }
        return value;
    }

    public static final boolean isHamiltonian(Graph<Integer, String, Integer> g, List<Integer> path) {
        // due to the way we build path (using permutations) we only have to check that
        // for each (path_i, path_i+1) in path (i in 0..size-2) we have a corresponding
        // edge in g
        boolean rtr = true;
        int size = path.size();
        for (int i = 0; i < size - 1; i++) {
            if (!g.hasEdge(path.get(i), path.get(i + 1))) {
                return false;
            }
        }
        return rtr;
    }

    public static final Optional<Integer> shortestPath(Graph<Integer,String,Integer> g, List<List<Integer>> paths) {
        return paths.parallelStream()
                .filter(p -> isHamiltonian(g, p))
                .map(p -> pathValue(g, p))
                .min((x, y) -> x.compareTo(y));
    }

    public static final Optional<Integer> longestPath(Graph<Integer,String,Integer> g, List<List<Integer>> paths) {
        return paths.parallelStream()
                .filter(p -> isHamiltonian(g, p))
                .map(p -> pathValue(g, p))
                .max((x, y) -> x.compareTo(y));
    }

    public static final ListCreator<Tuple3<String, String, Integer>> edgeCreator = ls -> {
        if (ls != null && ls.size() == 3) {
            Optional<String> from = id.apply(ls.get(0));
            Optional<String> to = id.apply(ls.get(1));
            Optional<Integer> dist = integer.apply(ls.get(2));
            if (from.isPresent() && to.isPresent() && dist.isPresent()) {
                return Optional.ofNullable(Tuple.of(from.get(), to.get(), dist.get()));
            }
        }
        return Optional.empty();
    };

    public static final String pretty(Graph<Integer, String, Integer> g, List<Integer> path) {
        return path.stream()
                        .map(g::label)
                        .flatMap(Optional::stream)
                        .collect(Collectors.joining(" -> "));
    }

    public static final LineReader<Tuple3<String, String, Integer>> edgeReader = regex("([^ ]+) to ([^ ]+) = ([\\d]*)",
            edgeCreator);

    public static final Day day9a = ls -> {
        Generator<Integer> gen = new Generator<>(0, x -> x + 1);
        Graph<Integer, String, Integer> g = new Graph<>(gen, true);
        // load
        ls.stream()
                .map(edgeReader)
                .flatMap(Optional::stream)
                .forEach(t -> {
                    g.createEdge(g.getOrCreateNode(t._1()), g.getOrCreateNode(t._2()), t._3());
                    g.createEdge(g.getOrCreateNode(t._2()), g.getOrCreateNode(t._1()), t._3());
                });
        // work
        return String.format("%d", shortestPath(g, permutations(new ArrayList<>(g.nodes()))).orElse(0));
    };

    public static final Day day9b = ls -> {
        Generator<Integer> gen = new Generator<>(0, x -> x + 1);
        Graph<Integer, String, Integer> g = new Graph<>(gen, true);
        // load
        ls.stream()
                .map(edgeReader)
                .flatMap(Optional::stream)
                .forEach(t -> {
                    g.createEdge(g.getOrCreateNode(t._1()), g.getOrCreateNode(t._2()), t._3());
                    g.createEdge(g.getOrCreateNode(t._2()), g.getOrCreateNode(t._1()), t._3());
                });
        // work
        return String.format("%d", longestPath(g, permutations(new ArrayList<>(g.nodes()))).orElse(0));
    };

}
