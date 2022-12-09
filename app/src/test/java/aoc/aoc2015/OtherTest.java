package aoc.aoc2015;

import static org.junit.Assert.*;
import org.junit.Test;

import aoc.aoc2015.days.Day5;
import aoc.aoc2015.days.Day4;
import aoc.aoc2015.days.Day2.Box;

public class OtherTest {
 
    @Test
    public void testBoxSurface() {
        Box b1 = new Box(2,3,4);
        Box b2 = new Box(1,1,10);
        assertEquals(58, b1.paperSurface());
        assertEquals(43, b2.paperSurface());
    }

    @Test
    public void testRibbonSize() {
        Box b1 = new Box(2,3,4);
        Box b2 = new Box(1,1,10);
        assertEquals(34, b1.ribbonSize());
        assertEquals(14, b2.ribbonSize());
    }

    @Test
    public void testEncrypt() {
        assertEquals("000001dbbfa3a5c83a2d506429c7b00e", Day4.encrypt("abcdef","609043"));
        assertEquals("000006136ef2ff3b291c85725f17325c", Day4.encrypt("pqrstuv","1048970"));
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

}
