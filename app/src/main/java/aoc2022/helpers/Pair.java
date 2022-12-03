package aoc2022.helpers;

import java.util.function.BiFunction;

public class Pair<T, U> {
    private T _1;
    private U _2;

    public Pair(T _1, U _2) {
        this._1 = _1;
        this._2 = _2;
    }

    public T _1() {
        return _1;
    }

    public U _2() {
        return _2;
    }

    public <V> Pair<V, U> map1(BiFunction<T, U, V> f) {
        return new Pair<>(f.apply(_1(), _2()), _2());
    }

    public <V> Pair<T, V> map2(BiFunction<T, U, V> f) {
        return new Pair<>(_1(), f.apply(_1(), _2()));
    }
}
