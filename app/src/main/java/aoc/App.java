package aoc;

import java.util.Map;
import java.util.Optional;

import aoc.helpers.AoCApp;
import aoc.helpers.Day;

public class App {

    public static final String MISSING_ARGUMENTS = "Missing argument (needed: year + day/subday)";
    public static final String UNKNOWN_EDITION = "Unknown edition";
    public static final String UNKNOWN_DAY = "Unknown day";
    public static final String NO_FILE = "Could not load file";
    public static final String NO_DAY = "Could not load day";
    public static final String COMPUTATION_ERROR = "Error while performing computation (possibly while parsing a line)";

    public static final Map<String, AoCApp> apps = Map.of(
            "2015", new aoc.aoc2015.App(),
            "2022", new aoc.aoc2022.App());

    public static void main(String[] args) {
        if (args.length >= 2) {
            String edition = args[0];
            String day = args[1];
            if (apps.containsKey(args[0])) {
                AoCApp app = apps.get(edition);
                if (app.days().contains(day)) {
                    Optional<Day> worker = app.day(day);
                    Optional<String> file = app.file(day);
                    if (file.isPresent()) {
                        if (worker.isPresent()) {
                            System.out.println(worker.get().apply(file.get()).orElse(COMPUTATION_ERROR));
                        } else {
                            System.out.println(NO_DAY);
                        }
                    } else {
                        System.out.println(NO_FILE);
                    }
                } else {
                    System.out.println(UNKNOWN_DAY);
                }
            } else {
                System.out.println(UNKNOWN_EDITION);
            }
        } else {
            System.out.println(MISSING_ARGUMENTS);
        }
    }
}