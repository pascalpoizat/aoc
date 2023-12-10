package aoc.aoc2015.days;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import aoc.helpers.Day;
import aoc.helpers.Generator;
import aoc.helpers.Graph;
import aoc.helpers.LineReader;
import aoc.helpers.ListCreator;
import static aoc.helpers.Readers.*;
import static aoc.helpers.Maths.*;

import aoc.helpers.contextual.SelectorGenerator;
import aoc.helpers.contextual.EvaluatorGenerator;
import aoc.helpers.contextual.PredicateGenerator;
import static aoc.helpers.contextual.Contexts.*;

import io.vavr.Tuple;
import io.vavr.Tuple3;

public class Day9 {

    private Day9() {
    }

    // the problem is finding the shortest Hamiltonian path
    // use bit-masking and dynamic programming ?
    // use extra node connected to anyone and then travelling salesman ?
    // use ugly solution for small graphs ? -> YES :-)

    public static final EvaluatorGenerator<Graph<Integer, String, Integer>, List<Integer>> valueOf = g -> path -> {
        int value = 0;
        int size = path.size();
        for (int i = 0; i < size - 1; i++) {
            value = value + g.label(path.get(i), path.get(i + 1)).orElse(0);
        }
        return value;
    };

    // due to the way we build path (using permutations) we only have to check that
    // for each (path_i, path_i+1) in path (i in 0..size-2) we have a corresponding
    // edge in g
    public static final PredicateGenerator<Graph<Integer, String, Integer>, List<Integer>> isHamiltonian = g -> path -> {
        boolean rtr = true;
        int size = path.size();
        for (int i = 0; i < size - 1; i++) {
            if (!g.hasEdge(path.get(i), path.get(i + 1))) {
                return false;
            }
        }
        return rtr;
    };

    public static final ListCreator<Tuple3<String, String, Integer>> edgeCreator = ls -> {
        if (ls != null && ls.size() == 3) {
            Optional<String> from = stringReader.apply(ls.get(0));
            Optional<String> to = stringReader.apply(ls.get(1));
            Optional<Integer> dist = integerReader.apply(ls.get(2));
            if (from.isPresent() && to.isPresent() && dist.isPresent()) {
                return Optional.of(Tuple.of(from.get(), to.get(), dist.get()));
            }
        }
        return Optional.empty();
    };

    public static final LineReader<Tuple3<String, String, Integer>> edgeReader = regex("([^ ]+) to ([^ ]+) = ([\\d]*)",
            edgeCreator);

    public static final Function<SelectorGenerator<Graph<Integer, String, Integer>, List<Integer>>, Day> day = f -> ls -> {
        Generator<Integer> gen = new Generator<>(0, x -> x + 1);
        Graph<Integer, String, Integer> g = new Graph<>(gen, true, true);
        // load
        ls.stream()
                .map(edgeReader)
                .flatMap(Optional::stream)
                .forEach(t -> {
                    int node = g.getOrCreateNode(t._1());
                    int other = g.getOrCreateNode(t._2());
                    int value = t._3();
                    g.createEdge(node, other, value); // creates 2 edges since bidirectionalCreation
                });  
        // work
        return String.format("%d", f.generate(g).apply(permutations(new ArrayList<>(g.nodes()))).orElse(0));
    };

    public static final Day day9a = day.apply(minSolution(isHamiltonian, valueOf));

    public static final Day day9b = day.apply(maxSolution(isHamiltonian, valueOf));

}
