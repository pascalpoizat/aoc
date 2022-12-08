package aoc.aoc2015;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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

}
