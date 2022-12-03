package aoc2022.helpers;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import aoc2022.App;

public interface Day extends Function<String, Optional<String>> {
    String computation(List<String> lines);

    @Override
    default Optional<String> apply(String filename) {
        try {
            URI uri = App.class.getResource(filename).toURI();
            Path path = Paths.get(uri);
            Stream<String> stream = Files.lines(path);
            try (stream) {
                return Optional.ofNullable(computation(stream.toList()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
