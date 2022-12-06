package aoc.helpers;

import java.util.Set;
import java.util.Optional;

public interface AoCApp {
    Set<String> days();

    Optional<String> file(String day);

    Optional<Day> day(String day);
}
