package aoc2022.helpers;

import java.util.Optional;
import java.util.function.Function;

public interface LineReader<T> extends Function<String, Optional<T>> {
    Optional<T> apply(String input);
}
