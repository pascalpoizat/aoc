package aoc.aoc2015;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;

import aoc.aoc2015.days.Day5;
import aoc.aoc2015.days.Day6;
import aoc.aoc2015.days.Day4;
import aoc.aoc2015.days.Day2.Box;
import aoc.aoc2015.days.Day6.Coordinate;
import aoc.aoc2015.days.Day6.Instruction;
import aoc.aoc2015.days.Day6.Order;
import static aoc.aoc2015.days.Day10.encode;

public class OtherTest {

    @Test
    public void testBoxSurface() {
        Box b1 = new Box(2, 3, 4);
        Box b2 = new Box(1, 1, 10);
        assertEquals(58, b1.paperSurface());
        assertEquals(43, b2.paperSurface());
    }

    @Test
    public void testRibbonSize() {
        Box b1 = new Box(2, 3, 4);
        Box b2 = new Box(1, 1, 10);
        assertEquals(34, b1.ribbonSize());
        assertEquals(14, b2.ribbonSize());
    }

    @Test
    public void testEncrypt() {
        assertEquals("000001dbbfa3a5c83a2d506429c7b00e", Day4.encrypt("abcdef", "609043"));
        assertEquals("000006136ef2ff3b291c85725f17325c", Day4.encrypt("pqrstuv", "1048970"));
    }

    @Test
    public void testNice() {
        assertTrue(Day5.threeVoyels.test("ugknbfddgicrmopn"));
        assertTrue(Day5.twiceInRow.test("ugknbfddgicrmopn"));
        assertTrue(Day5.noForbidden.test("ugknbfddgicrmopn"));
        assertTrue(Day5.isNice.test("ugknbfddgicrmopn"));
        assertTrue(Day5.isNice.test("aaa"));
        assertFalse(Day5.isNice.test("jchzalrnumimnmhp"));
        assertFalse(Day5.isNice.test("haegwjzuvuyypxyu"));
        assertFalse(Day5.isNice.test("dvszwmarrgswjxmb"));
    }

    @Test
    public void testNiceV2() {
        assertTrue(Day5.isNiceV2.test("qjhvhtzxzqqjkmpb"));
        assertTrue(Day5.isNiceV2.test("xxyxx"));
        assertFalse(Day5.isNiceV2.test("uurcxstgmygtbstg"));
        assertFalse(Day5.isNiceV2.test("ieodomkazucvgmuy"));
    }

    @Test
    public void testReadOrder() {
        Optional<Order> o1 = Day6.readOrder(" turn on ");
        Optional<Order> o2 = Day6.readOrder(" turn off ");
        Optional<Order> o3 = Day6.readOrder(" toggle ");
        Optional<Order> o4 = Day6.readOrder(" foo ");
        assertTrue(o1.isPresent() && o1.get() == Order.TURNON);
        assertTrue(o2.isPresent() && o2.get() == Order.TURNOFF);
        assertTrue(o3.isPresent() && o3.get() == Order.TOGGLE);
        assertTrue(o4.isEmpty());
    }

    @Test
    public void testReadInstruction() {
        Optional<Instruction> i1 = Day6.readInstruction.apply("turn on 887,9 through 959,629");
        Instruction exp1 = new Instruction(Order.TURNON, Coordinate.of(887, 9), Coordinate.of(959, 629));
        assertTrue(i1.isPresent());
        assertEquals(exp1, i1.get());
    }

    @Test
    public void testEncode() {
        assertEquals("11", encode("1"));
        assertEquals("21", encode("11"));
        assertEquals("1211", encode("21"));
        assertEquals("111221", encode("1211"));
        assertEquals("312211", encode("111221"));
    }

}
