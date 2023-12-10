package aoc.aoc2022.days;

import static aoc.helpers.Readers.split2Reader;

import java.util.Optional;

import aoc.helpers.Day;
import aoc.helpers.LineReader;

import io.vavr.Tuple;
import io.vavr.Tuple2;

public class Day2 {

    private Day2() {
    }

    public enum Play {
        ROCK, PAPER, SCISSORS;

        int value() {
            return switch (this) {
                case ROCK -> 1;
                case PAPER -> 2;
                case SCISSORS -> 3;
            };
        }

    }

    public enum Result {
        LOST, TIE, WON;

        int value() {
            return switch (this) {
                case LOST -> 0;
                case TIE -> 3;
                case WON -> 6;
            };
        }
    }

    public static Play playToLose(Play play) {
        return switch (play) {
            case ROCK -> Play.SCISSORS;
            case PAPER -> Play.ROCK;
            case SCISSORS -> Play.PAPER;
        };
    }

    public static Play playToWin(Play play) {
        return switch (play) {
            case ROCK -> Play.PAPER;
            case PAPER -> Play.SCISSORS;
            case SCISSORS -> Play.ROCK;
        };
    }

    public static final LineReader<Play> readPlay = play -> switch (play.strip()) {
        case "A", "X" -> Optional.of(Play.ROCK);
        case "B", "Y" -> Optional.of(Play.PAPER);
        case "C", "Z" -> Optional.of(Play.SCISSORS);
        default -> Optional.empty();
    };

    public static final LineReader<Result> readResult = result -> switch (result.strip()) {
        case "X" -> Optional.of(Result.LOST);
        case "Y" -> Optional.of(Result.TIE);
        case "Z" -> Optional.of(Result.WON);
        default -> Optional.empty();
    };

    public static Result play(Play adversary, Play me) {
        if (me == adversary)
            return Result.TIE;
        if (adversary == Play.ROCK && me == Play.PAPER)
            return Result.WON;
        if (adversary == Play.PAPER && me == Play.SCISSORS)
            return Result.WON;
        if (adversary == Play.SCISSORS && me == Play.ROCK)
            return Result.WON;
        return Result.LOST;
    }

    public static Play choose(Play adversary, Result objective) {
        if (objective == Result.TIE)
            return adversary;
        if (objective == Result.WON)
            return playToWin(adversary);
        return playToLose(adversary);
    }

    private static Tuple2<Play, Play> chooseAsTuple(Tuple2<Play,Result> t) {
        return Tuple.of(t._1(), choose(t._1(), t._2()));
    }

    public static int score(Play adversary, Play me) {
        return me.value() + play(adversary, me).value();
    }

    public static int score(Tuple2<Play, Play> play) {
        return score(play._1(), play._2());
    }

    public static final LineReader<Tuple2<Play, Play>> split1 = split2Reader(" ", readPlay,
            readPlay, Tuple::of);

    public static final LineReader<Tuple2<Play, Play>> split2 = l -> split2Reader(" ", readPlay,
            readResult, Tuple::of).apply(l).map(Day2::chooseAsTuple);

    public static final Day day2a = ls -> {
        int score = ls.stream()
                .map(split1)
                .flatMap(Optional::stream)
                .map(Day2::score)
                .reduce(0, Integer::sum);
        return String.format("%d", score);
    };

    public static final Day day2b = ls -> {
        int score = ls.stream()
                .map(split2)
                .flatMap(Optional::stream)
                .map(Day2::score)
                .reduce(0, Integer::sum);
        return String.format("%d", score);
    };

}
