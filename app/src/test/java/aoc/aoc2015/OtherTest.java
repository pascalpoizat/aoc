package aoc.aoc2015;

import static org.junit.Assert.*;
import org.junit.Test;

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

}
