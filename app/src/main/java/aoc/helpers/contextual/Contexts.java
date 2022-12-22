package aoc.helpers.contextual;

public class Contexts {

    private Contexts() {
    }
    
    public static final <C, S> SelectorGenerator<C, S> minSolution(PredicateGenerator<C, S> cp,
            EvaluatorGenerator<C, S> cv) {
        return c -> ss -> ss.parallelStream().filter(cp.generate(c)).map(cv.generate(c)).min(Integer::compareTo);
    }

    public static final <C, S> SelectorGenerator<C, S> maxSolution(PredicateGenerator<C, S> cp,
            EvaluatorGenerator<C, S> cv) {
        return c -> ss -> ss.parallelStream().filter(cp.generate(c)).map(cv.generate(c)).max(Integer::compareTo);
    }
    
}
