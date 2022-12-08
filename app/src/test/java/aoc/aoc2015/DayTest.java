package aoc.aoc2015;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;

import aoc.aoc2015.days.*;
import aoc.helpers.Day;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class DayTest {
    private Day day;
    private String filename;
    private String result;

    public DayTest(Day day, String filename, String result) {
        this.day = day;
        this.filename = filename;
        this.result = result;
    }

    @Parameterized.Parameters
    public static Collection values() {
        return Arrays.asList(new Object[][] {
                { Day1.day1a, "/aoc2015/input1.txt", "280" },
                { Day1.day1b, "/aoc2015/input1.txt", "1797" }
        });
    }

    @Test
    public void testDay() {
        assertEquals(result, day.apply(filename).orElse("0"));
    }

}
