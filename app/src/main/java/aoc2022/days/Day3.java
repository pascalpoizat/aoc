package aoc2022.days;

import java.util.List;
import java.util.OptionalInt;

import com.google.common.primitives.Chars;

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

    public static final Pair<CharSequence, CharSequence> split(String line) {
        int middle = line.length() / 2;
        CharSequence seq1 = line.subSequence(0, middle);
        CharSequence seq2 = line.subSequence(middle, line.length());
        return new Pair<>(seq1, seq2);
    }

    // ugly
    public static final OptionalInt findDuplicate(Pair<CharSequence, CharSequence> seqs) {
        CharSequence seq1 = seqs.fst();
        CharSequence seq2 = seqs.snd();
        return seq1.chars().filter(c1 -> seq2.chars().anyMatch(c2 -> c1 == c2)).findFirst();
    }

    // lots of assumptions here, strings have even size, there is always a unique
    // common char
    public static final Day day3a = ls -> {
        int value = ls.stream()
                .map(Day3::split)
                .map(Day3::findDuplicate)
                .filter(OptionalInt::isPresent)
                .map(OptionalInt::getAsInt)
                .map(Day3::priority)
                .reduce(0, (x, y) -> x + y);
        return String.format("%d", value);
    };

}
