package aoc.aoc2015.days;

import aoc.helpers.Day;
import aoc.helpers.LineReader;
import aoc.helpers.ListCreator;

import static aoc.helpers.Readers.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Day6 {

    private static final int SIZE = 1000;

    private boolean[][] table = new boolean[SIZE][SIZE];
    private int[][] table2 = new int[SIZE][SIZE];

    private Day6() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                table[i][j] = false;
                table2[i][j] = 0;
            }
        }
    }

    public enum Order {
        TURNON, TURNOFF, TOGGLE;
    }

    public record Coordinate(int x, int y) {
        public static final Coordinate of(int x, int y) {
            return new Coordinate(x, y);
        }
    }

    public record Instruction(Order order, Coordinate from, Coordinate to) {
    }

    public static final Optional<Order> readOrder(String s) {
        return switch (s.strip()) {
            case "turn on" -> Optional.of(Order.TURNON);
            case "turn off" -> Optional.of(Order.TURNOFF);
            case "toggle" -> Optional.of(Order.TOGGLE);
            default -> Optional.empty();
        };
    }

    public static final ListCreator<Coordinate> readCoordinate = ls -> {
        if (ls.size() == 2) {
            Optional<Integer> x = integer.apply(ls.get(0));
            Optional<Integer> y = integer.apply(ls.get(1));
            if (x.isPresent() && y.isPresent()) {
                return Optional.ofNullable(Coordinate.of(x.get(), y.get()));
            }
        }
        return Optional.empty();
    };

    public static final ListCreator<Instruction> createInstruction = ls -> {
        if (ls.size() == 5) {
            Optional<Order> order = readOrder(ls.get(0));
            Optional<Coordinate> from = readCoordinate.fromList(List.of(ls.get(1), ls.get(2)));
            Optional<Coordinate> to = readCoordinate.fromList(List.of(ls.get(3), ls.get(4)));
            if (order.isPresent() && from.isPresent() && to.isPresent()) {
                Coordinate c1 = from.get();
                Coordinate c2 = to.get();
                if (c1.x() < 0 || c1.x() >= SIZE || c1.y() < 0 || c1.y() >= SIZE) {
                    return Optional.empty();
                }
                if (c2.x() < 0 || c2.x() >= SIZE || c2.y() < 0 || c2.y() >= SIZE) {
                    return Optional.empty();
                }
                return Optional.ofNullable(new Instruction(order.get(), from.get(), to.get()));
            }
        }
        return Optional.empty();
    };

    public void performOrder(Order o, int x, int y) {
        switch (o) {
            case TURNON:
                table[x][y] = true;
                break;
            case TURNOFF:
                table[x][y] = false;
                break;
            case TOGGLE:
                table[x][y] = !table[x][y];
                break;
            default:
                break;
        }
    }

    public void performOrder2(Order o, int x, int y) {
        switch (o) {
            case TURNON:
                table2[x][y] = table2[x][y] + 1;
                break;
            case TURNOFF:
                table2[x][y] = (table2[x][y] <= 1) ? 0 : table2[x][y] - 1;
                break;
            case TOGGLE:
                table2[x][y] = table2[x][y] + 2;
                break;
            default:
                break;
        }
    }

    public void perform(Instruction ins) {
        for (int i = ins.from().x(); i <= ins.to().x(); i++) {
            for (int j = ins.from().y(); j <= ins.to().y(); j++) {
                performOrder(ins.order(), i, j);
            }
        }
    }

    public void perform2(Instruction ins) {
        for (int i = ins.from().x(); i <= ins.to().x(); i++) {
            for (int j = ins.from().y(); j <= ins.to().y(); j++) {
                performOrder2(ins.order(), i, j);
            }
        }
    }

    public int countLit() {
        int rtr = 0;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (table[i][j])
                    rtr++;
        return rtr;
    }

    public int countLit2() {
        int rtr = 0;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                rtr += table2[i][j];
        return rtr;
    }

    // turn on 887,9 through 959,629
    public static final LineReader<Instruction> readInstruction = regex("([a-z ]+)(\\d+),(\\d+) through (\\d+),(\\d+)",
            createInstruction);

    public static final Day day6a = ls -> {
        Day6 d = new Day6();
        Stream<Instruction> ins = ls.stream()
                .map(readInstruction)
                .flatMap(Optional::stream);
        ins.forEach(d::perform);
        return String.format("%d", d.countLit());
    };

    public static final Day day6b = ls -> {
        Day6 d = new Day6();
        Stream<Instruction> ins = ls.stream()
                .map(readInstruction)
                .flatMap(Optional::stream);
        ins.forEach(d::perform2);
        return String.format("%d", d.countLit2());
    };

}