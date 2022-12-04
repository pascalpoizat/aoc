package aoc2022.helpers;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Readers {

    /**
     * Splits a line given a separator regex,applies readers to both parts, and
     * combines the results.
     * If the line cannot be separated in exactly two or any of the readers fail
     * then there is no result.
     * 
     * @param <T>      type of the object returned from the first part of the line
     * @param <U>      type of the object returned from the second part of the line
     * @param <V>      type of the object combined from the parts
     * @param regex    the separator
     * @param f1       reader for type T
     * @param f2       reader for type U
     * @param combiner combiner
     * @param line     line to split
     * @return
     */
    public static final <T, U, V> Optional<V> split(String regex, Reader<T> f1,
            Reader<U> f2, BiFunction<T, U, V> combiner, String line) {
        String[] parts = line.split(regex);
        if (parts.length == 2) {
            Optional<T> t = f1.apply(parts[0]);
            Optional<U> u = f2.apply(parts[1]);
            if (t.isPresent() && u.isPresent()) {
                return Optional.ofNullable(combiner.apply(t.get(), u.get()));
            }
        }
        return Optional.empty();
    }

}
