package aoc.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import io.vavr.Tuple;
import io.vavr.Tuple2;

public final class Readers {

    private Readers() {
    }

    public static CharSequence toCharSequence(IntStream stream) {
        return stream.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
    }

    public static CharSequence toCharSequence(Stream<Character> stream) {
        return stream.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
    }

    public static CharSequence toCharSequence(String string) {
        return toCharSequence(string.chars());
    }

    /**
     * String reader.
     */
    public static final LineReader<String> stringReader = Optional::ofNullable;

    /**
     * Integer reader.
     */
    public static final LineReader<Integer> integerReader = l -> {
        try {
            return Optional.of(Integer.parseInt(l));
        } catch (Exception x) {
            return Optional.empty();
        }
    };

    /**
     * Split line reader (version that splits in 2).
     * t:T REGEX u:T -> f(t,u):V.
     * 
     * @param <T>   type of the object read from the first part of the line.
     * @param <U>   type of the object read from the second part of the line.
     * @param <V>   type of the object for the line.
     * @param regex separator used to split the lines in two.
     * @param readT {@link LineReader} used to read the first part.
     * @param readU {@link LineReader} used to read the second part.
     * @param f     function to get the object for the line from the ones for the
     *              two parts.
     */
    public static <T, U, V> LineReader<V> split2Reader(String regex, LineReader<T> readT,
            LineReader<U> readU, BiFunction<T, U, V> f) {
        return l -> {
            if (regex == null || readT == null || readU == null || f == null || l == null)
                return Optional.empty();
            String[] parts = l.split(regex);
            if (parts.length == 2) {
                Optional<T> t = readT.apply(parts[0]);
                Optional<U> u = readU.apply(parts[1]);
                if (t.isPresent() && u.isPresent()) {
                    return Optional.ofNullable(f.apply(t.get(), u.get()));
                }
            }
            return Optional.empty();
        };
    }

    /**
     * Split line reader (version that splits in n).
     * x1 REGEX ... REGEX xn -> f(x1,...,xn):T.
     */
    public static <T> LineReader<T> splitNReader(String regex, ListCreator<T> f) {
        return l -> {
            if (regex == null || f == null || l == null)
                return Optional.empty();
            return f.fromList(List.of(l.split(regex)));
        };
    }

    /**
     * Regex reader. Reads from one regex with n groups.
     * REGEX with groups g1 ... gn -> f(g1,...,gn).
     */
    public static <T> LineReader<T> regex(String regex, ListCreator<T> f) {
        return l -> {
            if (regex == null || regex.equals("") || f == null) {
                return Optional.empty();
            }
            try {
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(l);
                if (matcher.matches()) {
                    List<String> groups = new ArrayList<>();
                    for (int i = 1; i <= matcher.groupCount(); i++) {
                        groups.add(matcher.group(i));
                    }
                    return f.fromList(groups);
                } else {
                    return Optional.empty();
                }
            } catch (Exception e) {
                return Optional.empty();
            }
        };
    }

    /**
     * Regex reader. Finds all occurrences of a regex.
     * REGEX (instance 1) ... REGEX (instance n) -> f(instance 1, ... instance n).
     */
    public static <T> LineReader<T> findAll(String regex, ListCreator<T> f) {
        return l -> {
            if (regex == null || regex.equals("") || f == null) {
                return Optional.empty();
            }
            try {
                List<String> instances = new ArrayList<>();
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(l);
                while (matcher.find()) {
                    instances.add(matcher.group());
                }
                return f.fromList(instances);
            } catch (Exception e) {
                return Optional.empty();
            }
        };
    }

    public static FileReader<Tuple2<List<String>, List<String>>> splitUntil(Predicate<String> p, boolean mandatory,
            boolean keep) {
        return ls -> {
            if (p == null || ls == null)
                return Optional.empty();
            List<String> before = new ArrayList<>();
            List<String> after = new ArrayList<>();
            int i = 0;
            boolean found = false;
            while (i < ls.size()) {
                if (p.test(ls.get(i))) {
                    found = true;
                    if (keep) {
                        before.add(ls.get(i));
                    }
                    i++;
                    break;
                }
                before.add(ls.get(i));
                i++;
            }
            while (i < ls.size()) {
                after.add(ls.get(i));
                i++;
            }
            if (mandatory && !found) {
                return Optional.empty();
            } else {
                return Optional.of(Tuple.of(before, after));
            }
        };
    }

    public static final LineReader<Integer> indexedPartReader(String prefix, String separator) {
        return s -> regex(String.format("%s%s([\\d]+)", prefix, separator),
                ls -> integerReader.apply(ls.get(0)))
                .apply(s);
    }

    public static final <T> LineReader<List<T>> listReader(String separator, LineReader<T> reader) {
        return splitNReader(separator,
                l -> {
                    List<T> rtr = l.stream().map(reader).flatMap(Optional::stream).toList();
                    return rtr.isEmpty() ? Optional.empty() : Optional.of(rtr);
                });
    }

}
