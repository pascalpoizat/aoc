package aoc.aoc2015.days;

import java.util.List;
import static aoc.helpers.Maths.*;

public class Day13 {

    // We first try with a generate-and-test approach (ugly)
    // If not working we will see with tree based approach

    private int[][] values;
    private int nbPeople;
    
    public Day13(int nbPeople) {
        this.nbPeople = nbPeople;
        this.values = new int[this.nbPeople][this.nbPeople];
        for (int i = 0; i < this.nbPeople; i++)
            for (int j = 0; j < this.nbPeople; j++)
                this.values[i][j] = 0;
    }

    // solution is a permutation of people's ids
    // e.g., [4, 1, 2, 0, 3] for 5 people
    public static final Integer valueOf(Day13 context, List<Integer> solution) {
        int rtr = 0;
        for (int i = 0; i < context.nbPeople; i++) {
            int person = solution.get(i);
            int previous = solution.get(previous(context.nbPeople, i));
            int next = solution.get(next(context.nbPeople, i));
            rtr += context.values[person][previous];
            rtr += context.values[person][next];
        }
        return rtr;
    }

}
