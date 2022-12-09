package aoc.aoc2015.days;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import aoc.helpers.Day;
import aoc.helpers.Pair;

public class Day3 {

    private Map<Pair<Integer, Integer>, Integer> visited;

    private Day3() {
        visited = new HashMap<>();
        visited.put(Pair.of(0,0), 1);
    }

    public int nbVisited() {
        return visited.keySet().size();
    }

    public static final Optional<Pair<Integer, Integer>> valueOf(int move) {
        return switch (move) {
            case '^' -> Optional.of(Pair.of(0, 1));
            case 'v' -> Optional.of(Pair.of(0, -1));
            case '>' -> Optional.of(Pair.of(1, 0));
            case '<' -> Optional.of(Pair.of(-1, 0));
            default -> Optional.empty();
        };
    }

    public static final Pair<Integer, Integer> move(Pair<Integer, Integer> start, Pair<Integer, Integer> move) {
        return Pair.of(start.fst() + move.fst(), start.snd() + move.snd());
    }

    public final void visit(Pair<Integer, Integer> place) {
        visited.computeIfAbsent(place, k -> 0);
        visited.put(place, visited.get(place) + 1);
    }

    public static final List<Pair<Integer, Integer>> moves(List<String> ls) {
        return ls.get(0).chars().boxed()
        .map(Day3::valueOf)
        .flatMap(Optional::stream)
        .toList();
    }

    public static final Day day3a = ls -> {
        Day3 d = new Day3();
        Pair<Integer, Integer> here = Pair.of(0, 0);
        List<Pair<Integer, Integer>> moves = moves(ls);
        for (Pair<Integer, Integer> m: moves) {
            here = move(here, m);
            d.visit(here);
        }
        return String.format("%d", d.nbVisited());
    };

    public static final Day day3b = ls -> {
        Day3 d = new Day3();
        Pair<Integer, Integer> hereSanta = Pair.of(0, 0);
        Pair<Integer, Integer> hereBot = Pair.of(0, 0);
        List<Pair<Integer, Integer>> moves = moves(ls);
        boolean santaTurn = true;
        for (Pair<Integer, Integer> m: moves) {
            if (santaTurn) {
                hereSanta = move(hereSanta, m);
                d.visit(hereSanta);
                santaTurn = false;
            } else {
                hereBot = move(hereBot, m);
                d.visit(hereBot);
                santaTurn = true;
            }
        }
        return String.format("%d", d.nbVisited());
    };

}
