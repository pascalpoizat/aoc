package aoc2022.helpers;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface FileReader<T> extends Function<List<String>, Optional<T>> {
    Optional<T> apply(List<String> input);
}
