package aoc.aoc2015.days;

import aoc.helpers.Day;
import aoc.helpers.JsonObjectMapper;
import aoc.helpers.ListCreator;

import static aoc.helpers.Readers.integer;
import static aoc.helpers.Readers.findAll;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;

public class Day12 {

    private Day12() {
    }

    public static final ListCreator<Integer> somme = ls -> {
        if (ls == null)
            return Optional.empty();
        return Optional.of(ls.stream()
                .map(integer)
                .flatMap(Optional::stream)
                .reduce(0, Integer::sum));
    };

    public static int valueForNode(JsonNode node) {
        if (node.isNumber()) {
            return node.asInt();
        } else {
            return 0;
        }
    }

    public static final Function<String, Predicate<JsonNode>> hasStringValue = s -> n -> {
        boolean rtr = false;
        if (n.isObject()) {
            Iterator<Entry<String, JsonNode>> it = n.fields();
            while (it.hasNext()) {
                Entry<String, JsonNode> e = it.next();
                JsonNode sn = e.getValue();
                if (sn.isTextual() && sn.textValue().equals(s)) {
                    return true;
                }
            }
        }
        return rtr;
    };

    // DO NOT USE ON A STRUCTURE WITH CYCLES!
    public static int traverse(List<JsonNode> nodes, Function<JsonNode, Integer> f, Predicate<JsonNode> p,
                               int acc) {
        // no nodes: return accumulator
        if (nodes == null || nodes.isEmpty()) {
            return acc;
        }
        // else, IF PREDICATE YIELDS deal with node and move the rest
        JsonNode node = nodes.get(0);
        int accNew = acc;
        if (p.test(node)) {
            accNew = accNew + f.apply(node);
            Iterator<JsonNode> it = node.elements();
            while (it.hasNext()) {
                nodes.add(it.next());
            }
        }
        return traverse(nodes.subList(1, nodes.size()), f, p, accNew);
    }

    // easy version: treat JSON as pure text and search numbers
    public static final Day day12aV1 = ls -> {
        if (ls == null || ls.isEmpty())
            return "";
        return String.format("%d", findAll("(-?\\d+)", somme).apply(ls.get(0)).orElse(0));
    };

    public static final Function<Predicate<JsonNode>, Day> day = p -> ls -> {
        if (ls == null || ls.isEmpty())
            return "missing parameter";
        try {
            ObjectMapper mapper = JsonObjectMapper.instance().mapper();
            JsonNode node = mapper.readTree(ls.get(0));
            List<JsonNode> nodes = new ArrayList<>();
            nodes.add(node);
            return String.format("%d", traverse(nodes, Day12::valueForNode, p, 0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "JSON issue";
    };

    // complex version: we read JSON
    public static final Day day12a = day.apply(n -> true);

    // for quizz b we have to read JSON
    public static final Day day12b = day.apply(hasStringValue.apply("red").negate());
}
