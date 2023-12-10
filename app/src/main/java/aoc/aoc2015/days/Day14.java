package aoc.aoc2015.days;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import aoc.helpers.Day;
import aoc.helpers.LineReader;
import aoc.helpers.ListCreator;
import static aoc.helpers.Readers.*;

public class Day14 {

    private Day14() {
    }

    public static class Reindeer {
        private final int speed;
        private final int flyTime;
        private final int restTime;

        public Reindeer(int speed, int flyTime, int restTime) {
            this.speed = speed;
            this.flyTime = flyTime;
            this.restTime = restTime;
        }

        public int distanceAt(int seconds) {
            int rtr = 0;
            int fullPeriods = seconds / (flyTime + restTime);
            rtr = rtr + fullPeriods * speed * flyTime;
            int remainingTime = seconds - (fullPeriods * (flyTime + restTime));
            int remainingFlyTime = Math.min(remainingTime, flyTime);
            rtr = rtr + speed * remainingFlyTime;
            return rtr;
        }
    }

    public static final ListCreator<Reindeer> reindeerCreator = ls -> {
        if (ls != null && ls.size() == 3) {
            Optional<Integer> speed = integerReader.apply(ls.get(0));
            Optional<Integer> flyTime = integerReader.apply(ls.get(1));
            Optional<Integer> restTime = integerReader.apply(ls.get(2));
            if (speed.isPresent() && flyTime.isPresent() && restTime.isPresent()) {
                return Optional.of(new Reindeer(speed.get(), flyTime.get(), restTime.get()));
            }
        }
        return Optional.empty();
    };

    // Rudolph can fly 22 km/s for 8 seconds, but then must rest for 165 seconds.

    public static final LineReader<Reindeer> reindeerReader = regex("[^ ]+ can fly ([\\d]+) km/s for ([\\d]+) seconds, but then must rest for ([\\d]+) seconds.", reindeerCreator);

    public static final Day day14a = ls -> {
        Optional<Integer> value = ls.stream()
            .map(reindeerReader)
            .flatMap(Optional::stream)
            .map(r -> r.distanceAt(2503))
            .max(Integer::compareTo);
        return String.format("%d", value.orElse(0));
    };

    public static final Day day14b = ls -> {
        List<Reindeer> reindeers = ls.stream()
            .map(reindeerReader)
            .flatMap(Optional::stream)
            .toList();
        int nb = reindeers.size();
        List<Integer> distances = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();
        for(int i = 0; i < nb; i++) {
            distances.add(0);
            scores.add(0);
        }
        for(int sec = 1; sec <= 2503; sec++) {
            for(int i = 0; i < nb; i++) {
                distances.set(i, reindeers.get(i).distanceAt(sec));
            }
            Optional<Integer> max = distances.stream().max(Integer::compareTo);
            if (max.isPresent()) {
                for(int i = 0; i < nb; i++) {
                    if (distances.get(i).equals(max.get())) {
                        scores.set(i, scores.get(i)+1);
                    }
                }
            }
        }
        Optional<Integer> value = scores.stream().max(Integer::compareTo);
        return String.format("%d", value.orElse(0));
    };

}
