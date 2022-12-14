package aoc.aoc2015.days;


import aoc.helpers.Day;

public class Day10 {

    public static final CharSequence encode(CharSequence word) {
        StringBuilder rtr = new StringBuilder();
        int i = 0;
        int size = word.length();
        while (i < size) {
            char target = word.charAt(i);
            int j = i+1;
            int n = 1;
            while (j < size && word.charAt(j) == target) {
                j++;
                n++;
            }
            rtr.append(String.format("%d%c", n, target));
            i = j;
        }
        return rtr.toString();
    }

    public static final CharSequence turn(int remain, CharSequence values) {
        if (remain == 0) return values;
        else return turn(remain - 1, encode(values));
    }
   
    public static final Day day10a = ls -> {       
        if (ls == null || ls.size() < 1)
            return "";
        return String.format("%d", turn(40, ls.get(0)).length());
    };

    public static final Day day10b = ls -> {
        if (ls == null || ls.size() < 1)
            return "";
        return String.format("%d", turn(50, ls.get(0)).length());
    };
}
