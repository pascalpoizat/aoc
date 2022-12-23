package aoc.aoc2015;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import aoc.helpers.JsonObjectMapper;

import aoc.aoc2015.days.Day5;
import aoc.aoc2015.days.Day6;
import aoc.aoc2015.days.Day14.Reindeer;
import aoc.aoc2015.days.Day15.Ingredient;
import aoc.aoc2015.days.Day15.ListGenerator;
import aoc.aoc2015.days.Day4;
import aoc.aoc2015.days.Day2.Box;
import aoc.aoc2015.days.Day6.Coordinate;
import aoc.aoc2015.days.Day6.Instruction;
import aoc.aoc2015.days.Day6.Order;
import static aoc.aoc2015.days.Day10.encode;
import static aoc.aoc2015.days.Day11.requirement1;
import static aoc.aoc2015.days.Day11.requirement2;
import static aoc.aoc2015.days.Day11.requirement3;
import static aoc.aoc2015.days.Day11.nextPassword;
import static aoc.aoc2015.days.Day15.score;

import aoc.aoc2015.days.Day12;
import static aoc.aoc2015.days.Day12.hasStringValue;

import io.vavr.Tuple;
import io.vavr.Tuple2;

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

    @Test
    public void testRequirements() {
        assertTrue(requirement1.test("hijklmmn"));
        assertFalse(requirement2.test("hijklmmn"));
        assertTrue(requirement3.test("abbceffg"));
        assertFalse(requirement1.test("abbceffg"));
        assertFalse(requirement3.test("abbcegjk"));
    }

    @Test
    public void testNextPassword() {
        assertTrue(requirement1.test("abcdffaa"));
        assertTrue(requirement2.test("abcdffaa"));
        assertTrue(requirement3.test("abcdffaa"));
        assertTrue(requirement1.test("ghjaabcc"));
        assertTrue(requirement2.test("ghjaabcc"));
        assertTrue(requirement3.test("ghjaabcc"));
        assertEquals("abcdffaa", nextPassword("abcdefgh"));
        assertEquals("ghjaabcc", nextPassword("ghijklmn"));
    }

    @Test
    public void testHasStringValue() {
        try {
            ObjectMapper mapper = JsonObjectMapper.instance().mapper();
            JsonNode node1 = mapper.readTree("[1, \"red\", 5]");
            JsonNode node2 = mapper.readTree("[1,{\"c\":\"red\",\"b\":2},3]");
            JsonNode node3 = mapper.readTree("{\"c\":\"red\",\"b\":2}");
            assertFalse(hasStringValue.apply("red").test(node1));
            assertFalse(hasStringValue.apply("red").test(node2));
            assertTrue(hasStringValue.apply("red").test(node3));
        } catch (Exception e) {
            fail();
        }
    }

    public static String helperValue(Predicate<JsonNode> p, String node) {
        return Day12.day.apply(p).compute(new ArrayList<>(List.of(node)));
    }

    @Test
    public void testValue() {
        try {
            String node1 = "[1,2,3]";
            String node2 = "{\"a\":2,\"b\":4}";
            String node3 = "[[[3]]]";
            String node4 = "{\"a\":{\"b\":4},\"c\":-1}";
            String node5 = "{\"a\":[-1,1]}";
            String node6 = "[-1,{\"a\":1}]";
            String node7 = "[]";
            String node8 = "{}";
            String node9 = "[1, \"red\", 5]";
            String node10 = "[1,{\"c\":\"red\",\"b\":2},3]";
            String node11 = "{\"c\":\"red\",\"b\":2}";
            Predicate<JsonNode> p1 = n -> true;
            Predicate<JsonNode> p2 = hasStringValue.apply("red").negate();
            assertEquals("6", helperValue(p1, node1));
            assertEquals("6", helperValue(p1, node2));
            assertEquals("3", helperValue(p1, node3));
            assertEquals("3", helperValue(p1, node4));
            assertEquals("0", helperValue(p1, node5));
            assertEquals("0", helperValue(p1, node6));
            assertEquals("0", helperValue(p1, node7));
            assertEquals("0", helperValue(p1, node8));
            assertEquals("6", helperValue(p2, node1));  
            assertEquals("6", helperValue(p2, node9));  
            assertEquals("4", helperValue(p2, node10));  
            assertEquals("0", helperValue(p2, node11));  
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testFlightDistance() {
        Reindeer comet = new Reindeer(14, 10, 127);
        Reindeer dancer = new Reindeer(16, 11, 162);
        assertEquals(1120, comet.distanceAt(1000));
        assertEquals(1056, dancer.distanceAt(1000));
    }

    @Test
    public void testCuisine() {
        Ingredient butterscotch = new Ingredient("Butterscotch", -1,-2,6,3,8);
        Ingredient cinnamon = new Ingredient("Cinnamon", 2,3,-2,-1,3);
        List<Tuple2<Ingredient, Integer>> doses = List.of(Tuple.of(butterscotch, 44), Tuple.of(cinnamon, 56));
        assertEquals(62842880, score(doses));
    }

    @Test
    public void testListGenerator() {
        // i too big
        Optional<List<Integer>> res1 = ListGenerator.next(List.of(0,0,0,0), 4, 100);
        assertFalse(res1.isPresent());
        // item is 100 + last
        Optional<List<Integer>> res2 = ListGenerator.next(List.of(0,0,0,100), 3, 100);
        assertFalse(res2.isPresent());
        // item is 100 + not last
        Optional<List<Integer>> res3 = ListGenerator.next(List.of(0,0,100,0), 2, 100);
        assertTrue(res3.isPresent());
        assertEquals(List.of(0,0,0,1), res3.get());
        // item is less than 100
        Optional<List<Integer>> res4 = ListGenerator.next(List.of(0,0,99,0), 2, 100);
        assertTrue(res4.isPresent());
        assertEquals(List.of(0,0,100,0), res4.get());
    }

}
