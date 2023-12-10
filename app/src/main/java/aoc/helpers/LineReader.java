package aoc.helpers;

import java.util.Optional;
import java.util.function.Function;

/**
 * {@link LineReader} is a functional interface with method apply: String -> Optional[T].
 */
public interface LineReader<T> extends Function<String, Optional<T>> {
    Optional<T> apply(String input);
}
