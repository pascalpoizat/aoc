package aoc.aoc2015.days;

import aoc.helpers.Day;
import aoc.helpers.ListCreator;

import static aoc.helpers.Readers.*;

import java.util.List;
import java.util.Optional;

public class Day2 {

    private Day2() {
    }

    public record Box(int l, int w, int h) {
        public int smallestSideSurface() {
            int s1 = l * w;
            int s2 = l * h;
            int s3 = w * h;
            if (s1 <= s2 && s1 <= s3)
                return s1;
            if (s2 <= s1 && s2 <= s3)
                return s2;
            return s3;
        }

        public int shortestPerimeter() {
            int s1 = 2 * l + 2 * w;
            int s2 = 2 * l + 2 * h;
            int s3 = 2 * w + 2 * h;
            if (s1 <= s2 && s1 <= s3)
                return s1;
            if (s2 <= s1 && s2 <= s3)
                return s2;
            return s3;
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

    public static class BoxCreator implements ListCreator<Box> {

        @Override
        public Optional<Box> fromList(List<String> ls) {
            if (ls != null && ls.size() == 3) {
                Optional<Integer> l = integer.apply(ls.get(0));
                Optional<Integer> w = integer.apply(ls.get(1));
                Optional<Integer> h = integer.apply(ls.get(2));
                if (l.isPresent() && w.isPresent() && h.isPresent()) {
                    return Optional.ofNullable(new Box(l.get(), w.get(), h.get()));
                } else {
                    return Optional.empty();
                }
            } else {
                return Optional.empty();
            }
        }

    }

    public static final Day day2a = ls -> {
        int surface = ls.stream()
                .map(regex("(\\d+)x(\\d+)x(\\d+)", new BoxCreator()))
                .flatMap(Optional::stream)
                .map(Box::paperSurface)
                .reduce(0, (x, y) -> x + y);
        return String.format("%d", surface);
    };

    public static final Day day2b = ls -> {
        int size = ls.stream()
                .map(regex("(\\d+)x(\\d+)x(\\d+)", new BoxCreator()))
                .flatMap(Optional::stream)
                .map(Box::ribbonSize)
                .reduce(0, (x, y) -> x + y);
        return String.format("%d", size);
    };
}