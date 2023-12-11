package aoc.aoc2023;

import org.junit.Test;

import aoc.aoc2023.days.*;
import aoc.aoc2023.days.Day4.Card;
import aoc.helpers.LineReader;

import static aoc.aoc2023.days.Day4.readCard;
import static aoc.helpers.Readers.indexedPartReader;
import static aoc.helpers.Readers.integerReader;
import static aoc.helpers.Readers.listReader;
import static aoc.helpers.Readers.split2Reader;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;

public class OtherTest {

    @Test
    public void valueOf() {
        List<Tuple2<Integer, String>> tests = List.of(
                Tuple.of(0, "0"),
                Tuple.of(1, "1"),
                Tuple.of(2, "2"),
                Tuple.of(3, "3"),
                Tuple.of(4, "4"),
                Tuple.of(5, "5"),
                Tuple.of(6, "6"),
                Tuple.of(7, "7"),
                Tuple.of(8, "8"),
                Tuple.of(9, "9"));
        for (Tuple2<Integer, String> t : tests) {
            assertEquals(t._1().intValue(), Day1.valueOf(t._2()));
        }
    }

    @Test
    public void indexFromStart() {
        assertEquals(Option.of(Tuple.of(0, "1")), Day1.indexFromStart("1abc2", "1"));
        assertEquals(Option.none(), Day1.indexFromStart("treb7uchet", "1"));
        assertEquals(Option.of(Tuple.of(4, "2")), Day1.indexFromStart("1abc2", "2"));
        assertEquals(Option.of(Tuple.of(4, "7")), Day1.indexFromStart("treb7uchet", "7"));
        assertEquals(Option.of(Tuple.of(0, "7")), Day1.indexFromStart("76four7nineeight", "7"));
        assertEquals(Option.of(Tuple.of(1, "6")), Day1.indexFromStart("76four7nineeight", "6"));
    }

    @Test
    public void indexFromEnd() {
        assertEquals(Option.of(Tuple.of(4, "1")), Day1.indexFromEnd("1abc2", "1"));
        assertEquals(Option.none(), Day1.indexFromEnd("treb7uchet", "1"));
        assertEquals(Option.of(Tuple.of(0, "2")), Day1.indexFromEnd("1abc2", "2"));
        assertEquals(Option.of(Tuple.of(5, "7")), Day1.indexFromEnd("treb7uchet", "7"));
        assertEquals(Option.of(Tuple.of(9, "7")), Day1.indexFromEnd("76four7nineeight", "7"));
        assertEquals(Option.of(Tuple.of(14, "6")), Day1.indexFromEnd("76four7nineeight", "6"));
    }

    @Test
    public void valueFromStart() {
        assertEquals(Option.of(1), Day1.valueFromStart(Day1.targets1, "1abc2"));
        assertEquals(Option.of(3), Day1.valueFromStart(Day1.targets1, "pqr3stu8vwx"));
        assertEquals(Option.of(1), Day1.valueFromStart(Day1.targets1, "a1b2c3d4e5f"));
        assertEquals(Option.of(7), Day1.valueFromStart(Day1.targets1, "treb7uchet"));
        assertEquals(Option.of(7), Day1.valueFromStart(Day1.targets1, "76four7nineeight"));
    }

    @Test
    public void valueFromEnd() {
        assertEquals(Option.of(2), Day1.valueFromEnd(Day1.targets1, "1abc2"));
        assertEquals(Option.of(8), Day1.valueFromEnd(Day1.targets1, "pqr3stu8vwx"));
        assertEquals(Option.of(5), Day1.valueFromEnd(Day1.targets1, "a1b2c3d4e5f"));
        assertEquals(Option.of(7), Day1.valueFromEnd(Day1.targets1, "treb7uchet"));
        assertEquals(Option.of(7), Day1.valueFromEnd(Day1.targets1, "76four7nineeight"));
    }

    @Test
    public void valueFromString1() {
        assertEquals(Option.of(12), Day1.valueFromString(Day1.targets1, "1abc2"));
        assertEquals(Option.of(38), Day1.valueFromString(Day1.targets1, "pqr3stu8vwx"));
        assertEquals(Option.of(15), Day1.valueFromString(Day1.targets1, "a1b2c3d4e5f"));
        assertEquals(Option.of(77), Day1.valueFromString(Day1.targets1, "treb7uchet"));
        assertEquals(Option.of(77), Day1.valueFromString(Day1.targets1, "76four7nineeight"));
    }

    @Test
    public void valueFromString2() {
        assertEquals(Option.of(29), Day1.valueFromString(Day1.targets2, "two1nine"));
        assertEquals(Option.of(83), Day1.valueFromString(Day1.targets2, "eightwothree"));
        assertEquals(Option.of(13), Day1.valueFromString(Day1.targets2, "abcone2threexyz"));
        assertEquals(Option.of(24), Day1.valueFromString(Day1.targets2, "xtwone3four"));
        assertEquals(Option.of(42), Day1.valueFromString(Day1.targets2, "4nineeightseven2"));
        assertEquals(Option.of(14), Day1.valueFromString(Day1.targets2, "zoneight234"));
        assertEquals(Option.of(76), Day1.valueFromString(Day1.targets2, "7pqrstsixteen"));
        assertEquals(Option.of(77), Day1.valueFromString(Day1.targets2, "yysevenyy"));
        assertEquals(Option.of(77), Day1.valueFromString(Day1.targets2, "yyseven"));
        assertEquals(Option.of(77), Day1.valueFromString(Day1.targets2, "sevenyy"));
        assertEquals(Option.of(77), Day1.valueFromString(Day1.targets2, "seveno"));
    }

    @Test
    public void cardFromString() {
        String prefix = "Card   3";
        String lists = "  1 21 53 59 44 | 69 82 63 72 16 21 14  1";
        List<Integer> list1 = List.of(1, 21, 53, 59, 44);
        List<Integer> list2 = List.of(69, 82, 63, 72, 16, 21, 14, 1);
        Card expectedCard = new Card(3, list1, list2);
        //
        LineReader<Integer> read1 = indexedPartReader("Card", "[ ]+");
        LineReader<Tuple2<List<Integer>, List<Integer>>> read2 = split2Reader("\\|",
                    listReader(" ", integerReader),
                    listReader(" ", integerReader),
                    Tuple2::new);
        LineReader<Card> read3 = readCard;
        //
        Optional<Integer> id = read1.apply(prefix);
        assertTrue(id.isPresent());
        assertEquals((Integer)3, id.get());
        //
        Optional<Tuple2<List<Integer>, List<Integer>>> tuple = read2.apply(lists);
        assertTrue(tuple.isPresent());
        assertEquals(list1, tuple.get()._1());
        assertEquals(list2, tuple.get()._2());
        //
        Optional<Card> card = read3.apply(prefix + ":" + lists);
        assertTrue(card.isPresent());
        assertEquals(expectedCard, card.get());
    }

}
