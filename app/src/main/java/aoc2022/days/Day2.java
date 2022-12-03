package aoc2022.days;

import java.util.Optional;

import aoc2022.helpers.Day;

public class Day2 {

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

    public static final Play playToLose(Play play) {
        return switch (play) {
            case ROCK -> Play.SCISSORS;
            case PAPER -> Play.ROCK;
            case SCISSORS -> Play.PAPER;
        };
    }

    public static final Play playToWin(Play play) {
        return switch (play) {
            case ROCK -> Play.PAPER;
            case PAPER -> Play.SCISSORS;
            case SCISSORS -> Play.ROCK;
        };
    }

    public static Optional<Play> playFromString(String play) {
        return switch (play.strip()) {
            case "A", "X" -> Optional.of(Play.ROCK);
            case "B", "Y" -> Optional.of(Play.PAPER);
            case "C", "Z" -> Optional.of(Play.SCISSORS);
            default -> Optional.empty();
        };
    }

    public static Optional<Result> resultFromString(String result) {
        return switch (result.strip()) {
            case "X" -> Optional.of(Result.LOST);
            case "Y" -> Optional.of(Result.TIE);
            case "Z" -> Optional.of(Result.WON);
            default -> Optional.empty();
        };
    }

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

    public static int score(Play adversary, Play me) {
        return me.value() + play(adversary, me).value();
    }

    public static final Day day2a = ls -> {
        int score = 0;
        for (String line : ls) {
            String plays[] = line.split(" ");
            Optional<Play> adversary = playFromString(plays[0]);
            Optional<Play> me = playFromString(plays[1]);
            if (adversary.isPresent() && me.isPresent()) {
                score = score + score(adversary.get(), me.get());
            }
        }
        return String.format("%d", score);
    };

    public static final Day day2b = ls -> {
        int score = 0;
        for (String line : ls) {
            String plays[] = line.split(" ");
            Optional<Play> adversary = playFromString(plays[0]);
            Optional<Result> result = resultFromString(plays[1]);
            if (adversary.isPresent() && result.isPresent()) {
                Play me = choose(adversary.get(), result.get());
                score = score + score(adversary.get(), me);
            }
        }
        return String.format("%d", score);
    };

}
