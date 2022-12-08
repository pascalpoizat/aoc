package aoc.helpers;

import java.util.function.BiFunction;

public class Pair<T, U> {
    private T fst;
    private U snd;

    public Pair(T fst, U snd) {
        this.fst = fst;
        this.snd = snd;
    }

    public static final <T, U> Pair<T, U> of(T fst, U snd) {
        return new Pair<>(fst, snd);
    }

    public T fst() {
        return fst;
    }

    public U snd() {
        return snd;
    }

    public <V> Pair<V, U> map1(BiFunction<T, U, V> f) {
        return Pair.of(f.apply(fst(), snd()), snd());
    }

    public <V> Pair<T, V> map2(BiFunction<T, U, V> f) {
        return Pair.of(fst(), f.apply(fst(), snd()));
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", fst(), snd());
    }
}
