package aoc.helpers;

import java.util.ArrayList;
import java.util.List;

public class Maths {

    private Maths() {
    }

    public static int previous(int max, int rank) {
        return (rank <= 0) ? max - 1 : rank - 1;
    }

    public static int next(int max, int rank) {
        return (rank >= max - 1) ? 0 : rank + 1;
    }

    public static void swap(List<Integer> values, int i, int j) {
        Integer aux = values.get(i);
        values.set(i, values.get(j));
        values.set(j, aux);
    }
    
    // https://en.wikipedia.org/wiki/Heap%27s_algorithm
    public static List<List<Integer>> permutations(List<Integer> values) {
        List<List<Integer>> rtr = new ArrayList<>();
        List<Integer> a = new ArrayList<>(values);
        int n = values.size();
        int[] c = new int[n];
        for (int i = 0; i < n; i++) {
            c[i] = 0;
        }
        rtr.add(new ArrayList<>(a));
        int i = 1;
        while (i < n) {
            if (c[i] < i) {
                if (i % 2 == 0) {
                    swap(a, 0, i);
                } else {
                    swap(a, c[i], i);
                }
                rtr.add(new ArrayList<>(a));
                c[i] += 1;
                i = 1;
            } else {
                c[i] = 0;
                i += 1;
            }
        }
        return rtr;
    }
   
}
