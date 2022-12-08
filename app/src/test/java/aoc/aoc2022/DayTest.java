package aoc.aoc2022;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;

import aoc.aoc2022.days.*;
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
                { Day1.day1a, "/aoc2022/input1.txt", "69501" },
                { Day1.day1b, "/aoc2022/input1.txt", "202346" },
                { Day2.day2a, "/aoc2022/input2.txt", "9651" },
                { Day2.day2b, "/aoc2022/input2.txt", "10560" },
                { Day3.day3a, "/aoc2022/input3.txt", "7831" },
                { Day3.day3b, "/aoc2022/input3.txt", "2683" },
                { Day4.day4a, "/aoc2022/input4.txt", "528" },
                { Day4.day4b, "/aoc2022/input4.txt", "881" }
        });
    }

    @Test
    public void testDay() {
        assertEquals(result, day.apply(filename).orElse("0"));
    }

}
