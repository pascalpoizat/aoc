package aoc.aoc2015.days;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.MD5;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.apache.commons.codec.digest.DigestUtils;

import aoc.helpers.Day;

public class Day4 {

    private Day4() {
    }

    public static String encrypt(String key, String input) {
        return new DigestUtils(MD5).digestAsHex(key+input);
    }

    public static final Function<String, Day> day = prefix -> ls -> {
        String key = ls.get(0);
        Optional<String> msg = Stream.iterate(1, x->x+1)
            .filter(i -> encrypt(key, String.format("%d",i)).startsWith(prefix))
            .findFirst()
            .map(Object::toString);
        return msg.orElse("");
    };

    public static final Day day4a = day.apply("00000");
    public static final Day day4b = day.apply("000000");
}
