package aoc2022.helpers;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Readers {

    /**
     * Integer reader.
     * INT
     */
    public static final Reader<Integer> integer = l -> {
        try {
            return Optional.ofNullable(Integer.parseInt(l));
        } catch (Exception x) {
            return Optional.empty();
        }
    };

    /**
     * Split line reader.
     * V ::= <T>:t REGEX <U>:u {f(t,u)}
     */
    public static final <T, U, V> Reader<V> split(String regex, Reader<T> x,
            Reader<U> y, BiFunction<T, U, V> f) {
        return l -> {
            if (regex == null || x == null || y == null || f == null || l == null)
                return Optional.empty();
            String[] parts = l.split(regex);
            if (parts.length == 2) {
                Optional<T> t = x.apply(parts[0]);
                Optional<U> u = y.apply(parts[1]);
                if (t.isPresent() && u.isPresent()) {
                    return Optional.ofNullable(f.apply(t.get(), u.get()));
                }
            }
            return Optional.empty();
        };
    }

}
