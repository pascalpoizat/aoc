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
                { Day6.day6b, "/aoc2015/input6.txt", "14110788"},
                { Day7.day7a, "/aoc2015/input7.txt", "3176"},
                { Day7.day7b, "/aoc2015/input7.txt", "14710"},
                { Day8.day8a, "/aoc2015/input8.txt", "1350"},
                { Day8.day8b, "/aoc2015/input8.txt", "2085"},
                { Day9.day9a, "/aoc2015/input9.txt", "141"},
                { Day9.day9b, "/aoc2015/input9.txt", "736"},
                { Day10.day10a, "/aoc2015/input10.txt", "360154"},
                { Day10.day10b, "/aoc2015/input10.txt", "5103798"},
                { Day11.day11a, "/aoc2015/input11.txt", "vzbxxyzz"},
                { Day11.day11b, "/aoc2015/input11.txt", "vzcaabcc"},
                { Day12.day12a, "/aoc2015/input12.txt", "156366"},
                { Day12.day12b, "/aoc2015/input12.txt", "96852"},
                { Day13.day13a, "/aoc2015/input13.txt", "618"},
                { Day13.day13b, "/aoc2015/input13.txt", "601"},
                { Day14.day14a, "/aoc2015/input14.txt", "2696"},
                { Day14.day14b, "/aoc2015/input14.txt", "1084"},
                { Day15.day15a, "/aoc2015/input15.txt", "13882464"},
                { Day15.day15b, "/aoc2015/input15.txt", "11171160"},
                { Day16.day16a, "/aoc2015/input16.txt", "40"},
                { Day16.day16b, "/aoc2015/input16.txt", "241"},
                { Day17.day17a, "/aoc2015/input17.txt", "654"},
                { Day17.day17b, "/aoc2015/input17.txt", "57"}
        });
    }

    @Test
    public void testDay() {
        assertEquals(result, day.apply(filename).orElse("0"));
    }

}
