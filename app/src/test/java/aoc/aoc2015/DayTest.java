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
                { Day1.day1b, "/aoc2015/input1.txt", "1797" },
                { Day2.day2a, "/aoc2015/input2.txt", "1588178"},
                { Day2.day2b, "/aoc2015/input2.txt", "3783758"},
                { Day3.day3a, "/aoc2015/input3.txt", "2572"},
                { Day3.day3b, "/aoc2015/input3.txt", "2631"},
                { Day4.day4a, "/aoc2015/input4.txt", "346386"},
                { Day4.day4b, "/aoc2015/input4.txt", "9958218"},
                { Day5.day5a, "/aoc2015/input5.txt", "236"},
                { Day5.day5b, "/aoc2015/input5.txt", "51"},
                { Day6.day6a, "/aoc2015/input6.txt", "377891"},
                { Day6.day6b, "/aoc2015/input6.txt", "14110788"}
        });
    }

    @Test
    public void testDay() {
        assertEquals(result, day.apply(filename).orElse("0"));
    }

}
