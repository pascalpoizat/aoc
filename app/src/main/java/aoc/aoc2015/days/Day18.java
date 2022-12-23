package aoc.aoc2015.days;

import aoc.helpers.Day;

public class Day18 {

    private Day18() {
    }

    public static final char INACTIVE = '.';
    public static final char ACTIVE = '#';
    public static final int STEPS = 100;
    public static final int SIZE = 100;

    public static void setCorners(char[][] current, int nbColumns, int nbLines) {
        current[0][0] = ACTIVE;
        current[0][nbColumns - 1] = ACTIVE;
        current[nbLines - 1][0] = ACTIVE;
        current[nbLines - 1][nbColumns - 1] = ACTIVE;
    }

    public static void copy(char[][] from, char[][] to, int nbColumns, int nbLines) {
        for (int line = 0; line < nbLines; line++) {
            if (nbColumns >= 0) System.arraycopy(from[line], 0, to[line], 0, nbColumns);
        }
    }

    public static int nbNeighbors(char[][] current, int line, int column, int nbColumns, int nbLines, char status) {
        int nb = 0;
        for (int deltay = -1; deltay <= 1; deltay++) {
            for (int deltax = -1; deltax <= 1; deltax++) {
                if (deltay != 0 || deltax != 0) {
                    int linep = line + deltay;
                    int columnp = column + deltax;
                    if (linep >= 0 && linep < nbLines && columnp >= 0 && columnp < nbColumns) {
                        if (current[linep][columnp] == status) {
                            nb++;
                        }
                    }
                }
            }
        }
        return nb;
    }

    public static int nb(char[][] current, int nbColumns, int nbLines, char status) {
        int nb = 0;
        for (int line = 0; line < nbLines; line++) {
            for (int column = 0; column < nbColumns; column++) {
                if (current[line][column] == status) {
                    nb++;
                }
            }
        }
        return nb;
    }

    public static char rules(char[][] current, int line, int column, int nbColumns, int nbLines) {
        if (column < 0 || column > nbColumns || line < 0 || line > nbLines) {
            return INACTIVE;
        }
        int activeNeighbors = nbNeighbors(current, line, column, nbColumns, nbLines, ACTIVE);
        return switch (current[line][column]) {
            case ACTIVE -> (activeNeighbors == 2 || activeNeighbors == 3) ? ACTIVE : INACTIVE;
            case INACTIVE -> (activeNeighbors == 3) ? ACTIVE : INACTIVE;
            default -> INACTIVE;
        };
    }

    public static void step(char[][] current, char[][] next, int nbColumns, int nbLines) {
        for (int line = 0; line < nbLines; line++) {
            for (int column = 0; column < nbColumns; column++) {
                next[line][column] = rules(current, line, column, nbColumns, nbLines);
            }
        }
    }

    public static void draw(char[][] current, int nbColumns, int nbLines) {
        for (int line = 0; line < nbLines; line++) {
            StringBuilder sb = new StringBuilder();
            for (int column = 0; column < nbColumns; column++) {
                sb.append(current[line][column]);
            }
            System.out.println(sb.toString());
        }
        System.out.println();
    }

    public static final Day day18a = ls -> {
        char[][] current = new char[SIZE][SIZE];
        char[][] next = new char[SIZE][SIZE];
        int line = 0;
        for (String l : ls) {
            int column = 0;
            for (Integer c : l.chars().toArray()) {
                current[line][column] = (char) (int) c;
                column++;
                if (column >= SIZE) {
                    break;
                }
            }
            line++;
            if (line >= SIZE) {
                break;
            }
        }
        for (int step = 1; step <= STEPS; step++) {
            step(current, next, SIZE, SIZE);
            copy(next, current, SIZE, SIZE);
        }
        return String.format("%d", nb(current, SIZE, SIZE, ACTIVE));
    };

    public static final Day day18b = ls -> {
        char[][] current = new char[SIZE][SIZE];
        char[][] next = new char[SIZE][SIZE];
        int line = 0;
        for (String l : ls) {
            int column = 0;
            for (Integer c : l.chars().toArray()) {
                current[line][column] = (char) (int) c;
                column++;
                if (column >= SIZE) {
                    break;
                }
            }
            line++;
            if (line >= SIZE) {
                break;
            }
        }
        setCorners(current, SIZE, SIZE);
        for (int step = 1; step <= STEPS; step++) {
            step(current, next, SIZE, SIZE);
            copy(next, current, SIZE, SIZE);
            setCorners(current, SIZE, SIZE);
        }
        return String.format("%d", nb(current, SIZE, SIZE, ACTIVE));
    };

}
