package aoc.helpers;

import java.util.List;
import java.util.Optional;

@FunctionalInterface
public interface ListCreator<T> {
    public static final ListCreator<List<String>> same = Optional::of;

    Optional<T> fromList(List<String> ls);
}
