package aoc.aoc2015.days;

import java.util.Optional;

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

    public static final LineReader<Tuple3<String, String, Integer>> edgeReader = regex("([^ ]+) to ([^ ]+) = ([\\d]*)",
            edgeCreator);


    public static final Day day9a = ls -> {
        Generator<Integer> gen = new Generator<>(0, x->x+1);
        Graph<Integer,String,Integer> g = new Graph<>(gen, true);
        // load
        ls.stream()
                .map(edgeReader)
                .flatMap(Optional::stream)
                .forEach(t -> {
                    g.createEdge(g.getOrCreateNode(t._1()), g.getOrCreateNode(t._2()), t._3());
                });
        // add node
        Integer idAdd = g.createNode("XXX");
        g.nodes().stream().filter(n -> !n.equals(idAdd)).forEach(
            n -> {
                g.createEdge(idAdd, n, 0);
                g.createEdge(n, idAdd, 0);}
        );
        // print out
        System.out.println(g);
        // work

        return "TODO:";
    };

    public static final Day day9b = ls -> {
        return "TODO:";
    };

}
