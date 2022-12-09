package aoc.aoc2022.days;

import static aoc.helpers.Readers.integer;
import static aoc.helpers.Readers.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import aoc.helpers.Day;
import aoc.helpers.LineReader;
import aoc.helpers.ListCreator;

public class Day5 {

    private Day5() {
    }

    public record Crate(String name) {
    }

    public record Move(int qty, int from, int to) {
    }

    public static class MoveCreator implements ListCreator<Move> {
        private MoveCreator() {
        }

        public static final MoveCreator instance = new MoveCreator();

        public static final MoveCreator instance() {
            return instance;
        }

        @Override
        public Optional<Move> fromList(List<String> ls) {
            if (ls != null && ls.size() == 3) {
                Optional<Integer> q = integer.apply(ls.get(0));
                Optional<Integer> f = integer.apply(ls.get(1));
                Optional<Integer> t = integer.apply(ls.get(2));
                if (q.isPresent() && f.isPresent() && t.isPresent()) {
                    return Optional.ofNullable(new Move(q.get(), f.get(), t.get()));
                }
            }
            return Optional.empty();
        }
    }

    public class Board {
        private int size;
        private List<Stack<Crate>> piles;

        public Board(int size) {
            this.size = size;
            piles = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                piles.add(new Stack<>());
            }
        }

        public boolean execute(Move m) {
            boolean rtr = true;
            for (int i = 1; i == m.qty(); i++) {
                if (m.from() < 1 || m.to() < 1 || m.from() > size || m.to() > size) {
                    rtr = false;
                    break;
                }
                try {
                    Crate c = piles.get(m.from()).pop();
                    piles.get(m.to()).push(c);
                } catch (Exception e) {
                    rtr = false;
                    break;
                }
            }
            return rtr;
        }
    }

    public static final LineReader<Move> move = regex("move (\\d+) from (\\d+) to (\\d+)", MoveCreator.instance());

    public static final Day day5a = null;
    public static final Day day5b = null;

}
