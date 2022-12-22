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

import aoc.helpers.contextual.EvaluatorGenerator;
import aoc.helpers.contextual.PredicateGenerator;
import aoc.helpers.contextual.SelectorGenerator;
import static aoc.helpers.contextual.Contexts.*;

import io.vavr.Tuple;
import io.vavr.Tuple4;

public class Day13 {

    private Day13() {
    }

    // <Model> ::= <Line>*
    // <Line> ::= <Name> would <Verb> <Value> happiness units by sitting next to
    // <Name>.
    // <Name> ::= id
    // <Value> ::= integer
    // <Verb> ::= "gain" | "lose"

    // We first try with a generate-and-test approach (ugly)
    // If not working we will see with tree based approach

    // We can model preferences using a graph (reusing things made for Day 9)

    public static final EvaluatorGenerator<Graph<Integer, String, Integer>, List<Integer>> valueOf = g -> people -> {
        int rtr = 0;
        int size = people.size();
        for (int i = 0; i < size; i++) {
            int person = people.get(i);
            int previous = people.get(previous(size, i));
            int next = people.get(next(size, i));
            if (g.hasEdge(person, previous)) {
                rtr = rtr + g.label(person, previous).get();
            }
            if (g.hasEdge(person, next)) {
                rtr = rtr + g.label(person, next).get();
            }
        }
        return rtr;
    };

    public static final ListCreator<Tuple4<String, String, Integer, String>> preferenceCreator = ls -> {
        if (ls != null && ls.size() == 4) {
            Optional<String> person = id.apply(ls.get(0));
            Optional<String> action = id.apply(ls.get(1));
            Optional<Integer> value = integer.apply(ls.get(2));
            Optional<String> other = id.apply(ls.get(3));
            if (person.isPresent() && action.isPresent() && value.isPresent() && other.isPresent()) {
                return Optional.ofNullable(Tuple.of(person.get(), action.get(), value.get(), other.get()));
            }
        }
        return Optional.empty();
    };

    public static final LineReader<Tuple4<String, String, Integer, String>> preferenceReader = regex(
            "([^ ]+) would ([^ ]+) ([\\d]+) happiness units by sitting next to ([^.]+).",
            preferenceCreator);

    public static final Function<SelectorGenerator<Graph<Integer, String, Integer>, List<Integer>>, Day> dayA = f -> ls -> {
        Generator<Integer> gen = new Generator<>(0, x -> x + 1);
        Graph<Integer, String, Integer> g = new Graph<>(gen, true, false);
        // load
        ls.stream()
                .map(preferenceReader)
                .flatMap(Optional::stream)
                .forEach(p -> {
                    int node = g.getOrCreateNode(p._1());
                    int other = g.getOrCreateNode(p._4());
                    int value = switch (p._2()) {
                        case "gain" -> p._3();
                        case "lose" -> -p._3();
                        default -> 0;
                    };
                    g.createEdge(node, other, value);
                });
        // work
        return String.format("%d", f.generate(g).apply(permutations(new ArrayList<>(g.nodes()))).orElse(0));
    };

    public static final Function<SelectorGenerator<Graph<Integer, String, Integer>, List<Integer>>, Day> dayB = f -> ls -> {
        Generator<Integer> gen = new Generator<>(0, x -> x + 1);
        Graph<Integer, String, Integer> g = new Graph<>(gen, true, false);
        // load
        ls.stream()
                .map(preferenceReader)
                .flatMap(Optional::stream)
                .forEach(p -> {
                    int node = g.getOrCreateNode(p._1());
                    int other = g.getOrCreateNode(p._4());
                    int value = switch (p._2()) {
                        case "gain" -> p._3();
                        case "lose" -> -p._3();
                        default -> 0;
                    };
                    g.createEdge(node, other, value);
                });
        // update
        int me = g.getOrCreateNode("Me");
        for(int i = 0; i < g.nodes().size(); i++) {
            if (i != me) {
                g.createEdge(me, i, 0);
                g.createEdge(i, me, 0);
            }
        }
        // work
        return String.format("%d", f.generate(g).apply(permutations(new ArrayList<>(g.nodes()))).orElse(0));
    };

    public static final Day day13a = dayA.apply(maxSolution(PredicateGenerator.all(), valueOf));

    public static final Day day13b = dayB.apply(maxSolution(PredicateGenerator.all(), valueOf));

}
