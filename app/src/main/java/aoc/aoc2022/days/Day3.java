package aoc.aoc2022.days;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import aoc.helpers.Day;
import static aoc.helpers.Readers.toCharSequence;

import io.vavr.Tuple;
import io.vavr.Tuple2;

public class Day3 {

    private Day3() {
    }

    public static int priority(Character i) {
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

    public static Tuple2<String, String> split(String line) {
        int middle = line.length() / 2;
        return Tuple.of(line.substring(0, middle), line.substring(middle));
    }

    public static Optional<Character> findDuplicate(String seq1, String seq2) {
        return findDuplicates(seq1, seq2).findFirst();
    }

    public static Stream<Character> findDuplicates(String seq1, String seq2) {
        return seq1.chars().filter(c1 -> seq2.chars().anyMatch(c2 -> c1 == c2)).mapToObj(x -> (char) x);
    }

    public static Optional<Character> findDuplicate(List<String> seqs) {
        if (seqs.size() < 2) { // if 0 or 1 sequence, nothing to do
            return Optional.empty();
        } else {
            String rtr = toCharSequence(findDuplicates(seqs.get(0), seqs.get(1))).toString();
            for (int i = 2; i < seqs.size(); i++) {
                rtr = toCharSequence(findDuplicates(rtr, seqs.get(i))).toString();
            }
            return rtr.chars().mapToObj(x -> (char) x).findFirst();
        }
    }

    public static List<List<String>> regroup(List<String> seqs, int size) {
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
                .map(seqs -> findDuplicate(seqs._1(), seqs._2()))
                .flatMap(Optional::stream)
                .map(Day3::priority)
                .reduce(0, Integer::sum);
        return String.format("%d", value);
    };

    public static final Day day3b = ls -> {
        int value = regroup(ls, 3).stream()
                .map(Day3::findDuplicate)
                .flatMap(Optional::stream)
                .map(Day3::priority)
                .reduce(0, Integer::sum);
        return String.format("%d", value);
    };

}
