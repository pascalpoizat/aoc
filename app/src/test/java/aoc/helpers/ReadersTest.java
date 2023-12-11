package aoc.helpers;

import org.junit.Test;

import io.vavr.Tuple;
import io.vavr.Tuple2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static aoc.helpers.Readers.findAll;
import static aoc.helpers.Readers.indexedPartReader;
import static aoc.helpers.Readers.integerReader;
import static aoc.helpers.Readers.listReader;
import static aoc.helpers.ListCreator.same;

import java.util.List;
import java.util.Optional;

public class ReadersTest {

    @Test
    public void testFindAll() {
        Optional<List<String>> res = findAll("(-?\\d+)", same).apply("[0, 1, {\"a\": 99, \"b\": 1}, -1]");
        assertTrue(res.isPresent());
        assertEquals(List.of("0", "1", "99", "1", "-1"), res.get());
    }

    @Test
    public void testIndexedPartReader() {
        Optional<Integer> res = indexedPartReader("Card", "[ ]+").apply("Card   6");
        assertTrue(res.isPresent());
        assertEquals((Integer)6, res.get());
    }

    @Test
    public void testListReader() {
        Tuple2<String, List<Integer>> t1 = Tuple.of("41 48 83 86 17", List.of(41, 48, 83, 86, 17));
        Tuple2<String, List<Integer>> t2 = Tuple.of("83 86  6 31 17  9 48 53", List.of(83, 86, 6, 31, 17, 9, 48, 53));
        Tuple2<String, List<Integer>> t3 = Tuple.of("  1 21 53 59 44", List.of(1, 21, 53, 59, 44));
        Tuple2<String, List<Integer>> t4 = Tuple.of("  1 21 53 59 44 ", List.of(1, 21, 53, 59, 44));
        Tuple2<String, List<Integer>> t5 = Tuple.of(" 69 82 63 72 16 21 14  1", List.of(69, 82, 63, 72, 16, 21, 14, 1));
        List<Tuple2<String, List<Integer>>> tests = List.of(t1, t2, t3, t4, t5);
        for (Tuple2<String, List<Integer>> t : tests) {
            Optional<List<Integer>> res = listReader(" ", integerReader).apply(t._1());
            assertTrue(res.isPresent());
            assertEquals(t._2(), res.get());
        }
    }
}
