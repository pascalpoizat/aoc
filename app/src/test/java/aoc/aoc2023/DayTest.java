package aoc.aoc2023;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;

import aoc.aoc2023.days.*;
import aoc.helpers.Day;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class DayTest {
    private final Day day;
    private final String filename;
    private final String result;

    public DayTest(Day day, String filename, String result) {
        this.day = day;
        this.filename = filename;
        this.result = result;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> values() {
        return Arrays.asList(new Object[][] {
                { Day1.day1a, "/aoc2023/input1.txt", "54338" },
                { Day1.day1b, "/aoc2023/input1.txt", "53389" }
        });
    }

    @Test
    public void testDay() {
        assertEquals(result, day.apply(filename).orElse("0"));
    }

}
