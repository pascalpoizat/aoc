package aoc.aoc2015.days;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import aoc.helpers.Day;

public class Day3 {

    private Map<Place, Integer> visited;

    private Day3() {
        visited = new HashMap<>();
        visited.put(new Place(0, 0), 1);
    }

    public int nbVisited() {
        return visited.keySet().size();
    }

    public record Move(int dx, int dy) {
        public static final Move of(int dx, int dy) {
            return new Move(dx, dy);
        }
    }

    public record Place(int x, int y) {
        public static final Place of(int x, int y) {
            return new Place(x, y);
        }
    }

    public static final Optional<Move> valueOf(Integer move) {
        return switch ((int)move) {
            case '^' -> Optional.of(Move.of(0, 1));
            case 'v' -> Optional.of(Move.of(0, -1));
            case '>' -> Optional.of(Move.of(1, 0));
            case '<' -> Optional.of(Move.of(-1, 0));
            default -> Optional.empty();
        };
    }

    public static final Place move(Place start, Move move) {
        return Place.of(start.x() + move.dx(), start.y() + move.dy());
    }

    public final void visit(Place place) {
        visited.computeIfAbsent(place, k -> 0);
        visited.put(place, visited.get(place) + 1);
    }

    public static final List<Move> moves(List<String> ls) {
        return ls.get(0).chars().boxed()
                .map(Day3::valueOf)
                .flatMap(Optional::stream)
                .toList();
    }

    public static final Day day3a = ls -> {
        Day3 d = new Day3();
        Place here = Place.of(0, 0);
        List<Move> moves = moves(ls);
        for (Move m : moves) {
            here = move(here, m);
            d.visit(here);
        }
        return String.format("%d", d.nbVisited());
    };

    public static final Day day3b = ls -> {
        Day3 d = new Day3();
        Place hereSanta = Place.of(0, 0);
        Place hereBot = Place.of(0, 0);
        List<Move> moves = moves(ls);
        boolean santaTurn = true;
        for (Move m : moves) {
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
