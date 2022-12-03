package aoc2022.helpers;

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
}
