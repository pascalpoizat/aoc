package aoc2022.days;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import aoc2022.helpers.Day;
import aoc2022.helpers.Pair;

public class Day3 {

    private Day3() {
    }

    public static final int priority(int l) {
        int rtr;
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
        return new Pair<>(line.substring(0, middle), line.substring(middle, line.length()));
    }

    public static final OptionalInt findDuplicate(String seq1, String seq2) {
        return findDuplicates(seq1, seq2).findFirst();
    }

    // ugly
    public static final IntStream findDuplicates(String seq1, String seq2) {
        return seq1.chars().filter(c1 -> seq2.chars().anyMatch(c2 -> c1 == c2));
    }

    public static final OptionalInt findDuplicate(List<String> seqs) {
        if (seqs.size() < 2) { // if 0 or 1 sequence, nothing to do
            return OptionalInt.empty();
        } else {
            String rtr = findDuplicates(seqs.get(0), seqs.get(1)).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
            for (int i = 2; i < seqs.size(); i++) {
                rtr = findDuplicates(rtr, seqs.get(i)).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
            }
            return rtr.chars().findFirst();
        }
    }

    public static final List<List<String>> regroup(List<String> seqs, int size) {
        List<List<String>> rtr = new ArrayList<>();
        List<String> current = new ArrayList<>();
        int nb = 0;
        for (String seq : seqs) {
            if (nb == 3) {
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
                .filter(OptionalInt::isPresent)
                .map(OptionalInt::getAsInt)
                .map(Day3::priority)
                .reduce(0, (x, y) -> x + y);
        return String.format("%d", value);
    };

    public static final Day day3b = ls -> {
        int value = regroup(ls, 3).stream()
                .map(Day3::findDuplicate)
                .filter(OptionalInt::isPresent)
                .map(OptionalInt::getAsInt)
                .map(Day3::priority)
                .reduce(0, (x, y) -> x + y);
        return String.format("%d", value);
    };

}
