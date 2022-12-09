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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fst == null) ? 0 : fst.hashCode());
        result = prime * result + ((snd == null) ? 0 : snd.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pair other = (Pair) obj;
        if (fst == null) {
            if (other.fst != null)
                return false;
        } else if (!fst.equals(other.fst))
            return false;
        if (snd == null) {
            if (other.snd != null)
                return false;
        } else if (!snd.equals(other.snd))
            return false;
        return true;
    }

}
