package aoc.helpers;

import java.util.List;
import java.util.Optional;

/**
 * {@link ListCreator} is a functional interface with method fromList: List[String] -> T.
 */
@FunctionalInterface
public interface ListCreator<T> {
    public static final ListCreator<List<String>> same = Optional::of;

    Optional<T> fromList(List<String> ls);
}
