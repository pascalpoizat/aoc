package aoc.aoc2022;

import org.junit.Test;

import aoc.aoc2022.days.*;
import aoc.aoc2022.days.Day5.Move;
import aoc.aoc2022.days.Day5.MoveCreator;
import aoc.helpers.Day;
import aoc.helpers.Pair;

import static aoc.helpers.Readers.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class AppTest {

    private static final Day dummyDay = x -> "";

    @Test
    public void nullFile() {
        assertEquals(Optional.empty(), dummyDay.apply(null));
    }

    @Test
    public void fileNotFound() {
        assertEquals(Optional.empty(), dummyDay.apply("This-file-does-not-exist"));
    }

    @Test
    public void untilEmptyList() {
        List<String> ls = List.of();
        Predicate<String> p = s -> s.strip().equals("");
        // not present - mandatory : empty
        Optional<Pair<List<String>, List<String>>> res1 = splitUntil(p, true, false).apply(ls);
        assertTrue(res1.isEmpty());
        // not present - not mandatory : ([], [])
        Optional<Pair<List<String>, List<String>>> res2 = splitUntil(p, false, false).apply(ls);
        assertTrue(res2.isPresent());
        assertEquals(res2.get().fst(), List.of());
        assertEquals(res2.get().snd(), List.of());
    }

    @Test
    public void untilNotEmptyList() {
        List<String> ls = List.of("aaa", "bbb", "ccc");
        Predicate<String> pa = s -> s.strip().equals("aaa");
        Predicate<String> pb = s -> s.strip().equals("bbb");
        Predicate<String> pc = s -> s.strip().equals("ccc");
        Predicate<String> pd = s -> s.strip().equals("ddd");
        // not present - mandatory : empty
        Optional<Pair<List<String>, List<String>>> resd1 = splitUntil(pd, true, false).apply(ls);
        assertTrue(resd1.isEmpty());
        // not present - not mandatory : (whole list, [])
        Optional<Pair<List<String>, List<String>>> resd2 = splitUntil(pd, false, false).apply(ls);
        assertTrue(resd2.isPresent());
        assertEquals(resd2.get().fst(), List.of("aaa", "bbb", "ccc"));
        assertEquals(resd2.get().snd(), List.of());
        // present - mandatory - do not keep : (before without, after)
        Optional<Pair<List<String>, List<String>>> resa1 = splitUntil(pa, true, false).apply(ls);
        Optional<Pair<List<String>, List<String>>> resb1 = splitUntil(pb, true, false).apply(ls);
        Optional<Pair<List<String>, List<String>>> resc1 = splitUntil(pc, true, false).apply(ls);
        assertTrue(resa1.isPresent());
        assertEquals(resa1.get().fst(), List.of());
        assertEquals(resa1.get().snd(), List.of("bbb", "ccc"));
        assertTrue(resb1.isPresent());
        assertEquals(resb1.get().fst(), List.of("aaa"));
        assertEquals(resb1.get().snd(), List.of("ccc"));
        assertTrue(resc1.isPresent());
        assertEquals(resc1.get().fst(), List.of("aaa", "bbb"));
        assertEquals(resc1.get().snd(), List.of());
        // present - mandatory -- keep
        Optional<Pair<List<String>, List<String>>> resa2 = splitUntil(pa, true, true).apply(ls);
        Optional<Pair<List<String>, List<String>>> resb2 = splitUntil(pb, true, true).apply(ls);
        Optional<Pair<List<String>, List<String>>> resc2 = splitUntil(pc, true, true).apply(ls);
        assertTrue(resa2.isPresent());
        assertEquals(resa2.get().fst(), List.of("aaa"));
        assertEquals(resa2.get().snd(), List.of("bbb", "ccc"));
        assertTrue(resb2.isPresent());
        assertEquals(resb2.get().fst(), List.of("aaa", "bbb"));
        assertEquals(resb2.get().snd(), List.of("ccc"));
        assertTrue(resc2.isPresent());
        assertEquals(resc2.get().fst(), List.of("aaa", "bbb", "ccc"));
        assertEquals(resc2.get().snd(), List.of());
    }

    @Test
    public void badReadInteger() {
        assertTrue(integer.apply("xxx").isEmpty());
    }

    @Test
    public void okReadInteger() {
        Optional<Integer> res = integer.apply("123");
        assertTrue(res.isPresent());
        assertEquals(123, (int) res.get());
    }

    @Test
    public void map1Pair() {
        int n = 456;
        Pair<String, Integer> p = new Pair<>("double", n);
        BiFunction<String, Integer, Integer> f = (s, i) -> switch (s) {
            case "double" -> i * 2;
            case "add1" -> i + 1;
            default -> 0;
        };
        Pair<Integer, Integer> p2 = p.map1(f);
        assertEquals(n * 2, (int) p2.fst());
        assertEquals(p.snd(), p2.snd());
    }

    @Test
    public void map2Pair() {
        int n = 456;
        Pair<String, Integer> p = new Pair<>("add1", n);
        BiFunction<String, Integer, Integer> f = (s, i) -> switch (s) {
            case "double" -> i * 2;
            case "add1" -> i + 1;
            default -> 0;
        };
        Pair<String, Integer> p2 = p.map2(f);
        assertEquals(p.fst(), p2.fst());
        assertEquals(n + 1, (int) p2.snd());
    }

    @Test
    public void scoreRockPaperScissors() {
        String plays[][] = { { "A", "Y" }, { "B", "X" }, { "C", "Z" } };
        int score = 0;
        for (int play = 0; play < plays.length; play++) {
            Day2.Play adversary = Day2.readPlay.apply(plays[play][0]).get();
            Day2.Play me = Day2.readPlay.apply(plays[play][1]).get();
            score += Day2.score(adversary, me);
        }
        assertEquals(15, score);
    }

    @Test
    public void priorites() {
        // from AoC example
        assertEquals(16, Day3.priority('p'));
        assertEquals(38, Day3.priority('L'));
        assertEquals(42, Day3.priority('P'));
        assertEquals(22, Day3.priority('v'));
        assertEquals(20, Day3.priority('t'));
        assertEquals(19, Day3.priority('s'));
        // boundaries
        assertEquals(1, Day3.priority('a'));
        assertEquals(26, Day3.priority('z'));
        assertEquals(27, Day3.priority('A'));
        assertEquals(52, Day3.priority('Z'));
    }

    @Test
    public void regroup1() {
        List<String> seqs = List.of();
        List<List<String>> res = Day3.regroup(seqs, 3);
        assertEquals(0, res.size());
    }

    @Test
    public void regroup2() {
        List<String> seqs = List.of("a", "b");
        List<List<String>> res = Day3.regroup(seqs, 3);
        assertEquals(1, res.size());
        assertEquals(2, res.get(0).size());
        assertEquals(List.of("a", "b"), res.get(0));
    }

    @Test
    public void regroup3() {
        List<String> seqs = List.of("a", "b", "c", "d", "e", "f", "g");
        List<List<String>> res = Day3.regroup(seqs, 3);
        assertEquals(3, res.size());
        assertEquals(3, res.get(0).size());
        assertEquals(3, res.get(1).size());
        assertEquals(1, res.get(2).size());
        assertEquals(List.of("a", "b", "c"), res.get(0));
        assertEquals(List.of("d", "e", "f"), res.get(1));
        assertEquals(List.of("g"), res.get(2));
    }

    @Test
    public void findDuplicate1() {
        List<List<String>> in = List.of(
                List.of("vJrwpWtwJgWrhcsFMMfFFhFp", "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL", "PmmdzqPrVvPwwTWBwg"),
                List.of("wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn", "ttgJtRGJQctTZtZT", "CrZsJsPPZsGzwwsLwLmpwMDw"));
        OptionalInt res1 = Day3.findDuplicate(in.get(0));
        OptionalInt res2 = Day3.findDuplicate(in.get(1));
        assertTrue(res1.isPresent());
        assertTrue(res2.isPresent());
        assertEquals('r', res1.getAsInt());
        assertEquals('Z', res2.getAsInt());
    }

    @Test
    public void testRegex1() {
        Optional<Move> m = regex("move (\\d+) from (\\d+) to (\\d+)", MoveCreator.instance()).apply("xxxx");
        assertTrue(m.isEmpty());
    }

    @Test
    public void testRegex2() {
        Optional<Move> m = regex("move (\\d+) from (\\d+) to (\\d+)", MoveCreator.instance())
                .apply("move 1 from 2 to xx");
        assertTrue(m.isEmpty());
    }

    @Test
    public void testRegex3() {
        Optional<Move> m = regex("move (\\d+) from (\\d+) to (\\d+)", MoveCreator.instance())
                .apply("move 1 from 2 to 3");
        assertTrue(m.isPresent());
        assertEquals(1, m.get().qty());
        assertEquals(2, m.get().from());
        assertEquals(3, m.get().to());
    }

    @Test
    public void day1a() {
        assertEquals("69501", Day1.day1a.apply("/aoc2022/input1.txt").orElse("0"));
    }

    @Test
    public void day1b() {
        assertEquals("202346", Day1.day1b.apply("/aoc2022/input1.txt").orElse("0"));
    }

    @Test
    public void day2a() {
        assertEquals("9651", Day2.day2a.apply("/aoc2022/input2.txt").orElse("0"));
    }

    @Test
    public void day2b() {
        assertEquals("10560", Day2.day2b.apply("/aoc2022/input2.txt").orElse("0"));
    }

    @Test
    public void day3a() {
        assertEquals("7831", Day3.day3a.apply("/aoc2022/input3.txt").orElse("0"));
    }

    @Test
    public void day3b() {
        assertEquals("2683", Day3.day3b.apply("/aoc2022/input3.txt").orElse("0"));
    }

    @Test
    public void day4a() {
        assertEquals("528", Day4.day4a.apply("/aoc2022/input4.txt").orElse("0"));
    }

    @Test
    public void day4b() {
        assertEquals("881", Day4.day4b.apply("/aoc2022/input4.txt").orElse("0"));
    }
}
