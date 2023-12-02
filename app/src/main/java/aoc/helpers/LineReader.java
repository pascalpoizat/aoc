package aoc.helpers;

import java.util.Optional;
import java.util.function.Function;

/**
 * {@link LineReader} is a functional interface that can be used to read lines from a source using method {@link #apply(String)}.
 */
public interface LineReader<T> extends Function<String, Optional<T>> {
    Optional<T> apply(String input);
}
