package aoc.aoc2023;

import org.junit.Test;

import aoc.aoc2023.days.*;

import static org.junit.Assert.*;

import java.util.List;

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

}
