package aoc.helpers;

import java.util.List;
import java.util.Optional;

@FunctionalInterface
public interface ListCreator<T> {
    Optional<T> fromList(List<String> ls);
}
