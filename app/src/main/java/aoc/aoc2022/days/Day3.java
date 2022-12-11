package aoc.aoc2022.days;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import aoc.helpers.Day;
import aoc.helpers.Pair;

public class Day3 {

    private Day3() {
    }

    public static final int priority(char i) {
        int rtr;
        int l = i;
        if (l >= 'a' && l <= 'z') {
            rtr = (l - 'a' + 1);
        } else if (l >= 'A' && l <= 'Z') {
            rtr = (l - 'A' + 27);
        } else {
            rtr = 0;
        }
        return rtr;
    }

    public static final Pair<String, String> split(String line) {
        int middle = line.length() / 2;
        return Pair.of(line.substring(0, middle), line.substring(middle, line.length()));
    }

    public static final Optional<Character> findDuplicate(String seq1, String seq2) {
        return findDuplicates(seq1, seq2).findFirst();
    }

    // ugly
    public static final Stream<Character> findDuplicates(String seq1, String seq2) {
        return seq1.chars().filter(c1 -> seq2.chars().anyMatch(c2 -> c1 == c2)).mapToObj(x -> (char) x);
    }

    public static final Optional<Character> findDuplicate(List<String> seqs) {
        if (seqs.size() < 2) { // if 0 or 1 sequence, nothing to do
            return Optional.empty();
        } else {
            String rtr = findDuplicates(seqs.get(0), seqs.get(1))
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
            for (int i = 2; i < seqs.size(); i++) {
                rtr = findDuplicates(rtr, seqs.get(i))
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
            }
            return rtr.chars().mapToObj(x -> (char) x).findFirst();
        }
    }

    public static final List<List<String>> regroup(List<String> seqs, int size) {
        List<List<String>> rtr = new ArrayList<>();
        List<String> current = new ArrayList<>();
        int nb = 0;
        for (String seq : seqs) {
            if (nb == size) {
                rtr.add(current);
                current = new ArrayList<>();
                current.add(seq);
                nb = 1;
            } else {
                current.add(seq);
                nb++;
            }
        }
        if (!current.isEmpty())
            rtr.add(current);
        return rtr;
    }

    // lots of assumptions here, strings have even size, there is always a unique
    // common char
    public static final Day day3a = ls -> {
        int value = ls.stream()
                .map(Day3::split)
                .map(seqs -> findDuplicate(seqs.fst(), seqs.snd()))
                .flatMap(Optional::stream)
                .map(Day3::priority)
                .reduce(0, (x, y) -> x + y);
        return String.format("%d", value);
    };

    public static final Day day3b = ls -> {
        int value = regroup(ls, 3).stream()
                .map(Day3::findDuplicate)
                .flatMap(Optional::stream)
                .map(Day3::priority)
                .reduce(0, (x, y) -> x + y);
        return String.format("%d", value);
    };

}
