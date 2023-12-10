package aoc.aoc2015.days;

import aoc.helpers.Day;
import aoc.helpers.LineReader;
import aoc.helpers.ListCreator;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static aoc.helpers.Readers.*;

public class Day16 {

    private Day16() {
    }

    public record Auntie(Integer id, Map<String, Integer> attributes) {
    }

    public static boolean matches(Auntie a, Map<String, Integer> query) {
        boolean rtr = true;
        for (Map.Entry<String, Integer> entry : query.entrySet()) {
            String attribute = entry.getKey();
            if (a.attributes().containsKey(attribute) && !a.attributes().get(attribute).equals(entry.getValue())) {
                rtr = false;
                break;
            }
        }
        return rtr;
    }

    public static boolean matches2(Auntie a, Map<String, Integer> query) {
        boolean rtr = true;
        for (Map.Entry<String, Integer> entry : query.entrySet()) {
            String attribute = entry.getKey();
            if (a.attributes().containsKey(attribute)) {
                int val = a.attributes().get(attribute);
                int req = entry.getValue();
                rtr = switch (attribute) {
                    case "cats", "trees" -> val > req;
                    case "pomeranians", "goldfish" -> val <req;
                    default -> val == req;
                };
                if (!rtr) {
                    break;
                }
            }
        }
        return rtr;
    }

    public static final Map<String, Integer> QUERY = Map.of(
            "children", 3,
            "cats", 7,
            "samoyeds", 2,
            "pomeranians", 3,
            "akitas", 0,
            "vizslas", 0,
            "goldfish", 5,
            "trees", 3,
            "cars", 2,
            "perfumes", 1
    );

    public static final ListCreator<Map.Entry<String, Integer>> entryCreator = ls -> {
        if (ls != null && ls.size() == 2) {
            Optional<String> attribute = stringReader.apply(ls.get(0));
            Optional<Integer> value = integerReader.apply(ls.get(1));
            if (attribute.isPresent() && value.isPresent()) {
                return Optional.of(new AbstractMap.SimpleEntry<>(attribute.get(), value.get()));
            }
        }
        return Optional.empty();
    };

    public static final ListCreator<Map<String, Integer>> attributesCreator = ls -> {
        if (ls != null) {
            Map<String, Integer> attributes = new HashMap<>();
            ls.stream()
                    .map(s -> regex("([^:]+): ([\\d]+)", entryCreator).apply(s))
                    .flatMap(Optional::stream)
                    .forEach(e -> attributes.put(e.getKey(), e.getValue()));
            return Optional.of(attributes);
        }
        return Optional.empty();
    };

    public static final ListCreator<Auntie> auntieCreator = ls -> {
        if (ls != null && ls.size() == 2) {
            Optional<Integer> id = integerReader.apply(ls.get(0));
            Optional<Map<String, Integer>> attributes = splitNReader(", ", attributesCreator).apply(ls.get(1));
            if (id.isPresent() && attributes.isPresent()) {
                return Optional.of(new Auntie(id.get(), attributes.get()));
            }
        }
        return Optional.empty();
    };

    public static final LineReader<Auntie> auntieReader = regex("Sue ([\\d]+): (.+)", auntieCreator);

    public static final Function<Predicate<Auntie>, Day> day = p -> ls -> {
        return ls.stream()
                .map(auntieReader)
                .flatMap(Optional::stream)
                .filter(p)
                .map(Auntie::id)
                .findFirst()
                .orElse(0)
                .toString();
    };

    public static final Day day16a = day.apply(a -> matches(a, QUERY));

    public static final Day day16b = day.apply(a -> matches2(a, QUERY));

}
