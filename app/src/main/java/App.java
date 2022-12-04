
import java.util.List;

public class App {

    public static final String MISSING_ARGUMENTS = "Missing argument (needed: year + day/subday)";
    public static final String UNKNOWN_EDITION = "Unknown edition";

    public static void main(String[] args) {
        if(args.length >= 2) {
            switch (args[0]) {
                case "2015" -> aoc2015.App.main(List.of(args[1]));
                case "2022" -> aoc2022.App.main(List.of(args[1]));
                default -> System.out.println(UNKNOWN_EDITION);
            }
        } else {
            System.out.println(MISSING_ARGUMENTS);
        }
    }
}