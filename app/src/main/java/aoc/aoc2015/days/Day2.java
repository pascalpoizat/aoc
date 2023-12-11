package aoc.aoc2015.days;

import aoc.helpers.Day;
import aoc.helpers.ListCreator;
import io.vavr.collection.List;

import static aoc.helpers.Readers.*;

import java.util.Optional;
import java.util.function.Function;

public class Day2 {

    private Day2() {
    }

    public record Box(int l, int w, int h) {
        public int smallestSideSurface() {
            return List.of(l * w, l * h, w * h).min().getOrElse(0);
        }

        public int shortestPerimeter() {
            return List.of(2 * l + 2 * w, 2 * l + 2 * h, 2 * w + 2 * h).min().getOrElse(0);
        }

        public int surface() {
            return 2 * l * w + 2 * w * h + 2 * h * l;
        }

        public int paperSurface() {
            return surface() + smallestSideSurface();
        }

        public int volume() {
            return l * w * h;
        }

        public int ribbonSize() {
            return volume() + shortestPerimeter();
        }
    }

    public static final ListCreator<Box> boxCreator = ls -> {
        if (ls != null && ls.size() == 3) {
            Optional<Integer> l = integerReader.apply(ls.get(0));
            Optional<Integer> w = integerReader.apply(ls.get(1));
            Optional<Integer> h = integerReader.apply(ls.get(2));
            if (l.isPresent() && w.isPresent() && h.isPresent()) {
                return Optional.of(new Box(l.get(), w.get(), h.get()));
            }
        }
        return Optional.empty();
    };

    public static final Day day2(Function<Box, Integer> f) {
        return ls -> ls.stream()
                .map(regex("(\\d+)x(\\d+)x(\\d+)", boxCreator))
                .flatMap(Optional::stream)
                .map(f)
                .reduce(0, Integer::sum)
                .toString();
    }

    public static final Day day2a = day2(Box::paperSurface);

    public static final Day day2b = day2(Box::ribbonSize);

}