package aoc.helpers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static aoc.helpers.Readers.findAll;
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
}
