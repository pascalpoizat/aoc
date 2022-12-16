package aoc.aoc2015.days;

import java.util.function.Predicate;

import aoc.helpers.Day;

public class Day11 {

    private Day11() {
    }

    // ...(c)(c+1)(c+2)...
    public static final Predicate<CharSequence> requirement1 = cs -> {
        int i = 0;
        while (i < cs.length()-2) {
            char current = cs.charAt(i);
            if (cs.charAt(i+1) == current+1 && cs.charAt(i+2) == current+2) {
                return true;
            }
            i++;
        }
        return false;
    };

    // not ...i..., not ...o..., not ...l...
    public static final Predicate<CharSequence> requirement2 = cs -> {
        String word = cs.toString();
        return !(word.contains("i") || word.contains("o") || word.contains("l"));
    };

    // ...(c)(c)...(c')(c')... with c!=c'
    public static final Predicate<CharSequence> requirement3 = cs -> {
        int i = 0;
        while(i < cs.length()-3) {
            if(cs.charAt(i)==cs.charAt(i+1)) {
                int j = i+2;
                while(j < cs.length()-1) {
                    if(cs.charAt(j)!=cs.charAt(i) && cs.charAt(j)==cs.charAt(j+1)) {
                        return true;
                    }
                    j++;
                }
            }
            i++;
        }
        return false;
    };

    public static final Predicate<CharSequence> requirements = requirement1.and(requirement2).and(requirement3);

    public static final CharSequence next(CharSequence word) {
        char[] w = word.toString().toCharArray();
        int i = w.length-1;
        while (i > 0) {
            if (w[i] != 'z') {
                w[i]++;
                break;
            } else {
                w[i] = 'a';
                i--;
            }
        }
        return new String(w);
    }

    public static final CharSequence nextPassword(CharSequence word) {
        CharSequence rtr = next(word);
        while(!requirements.test(rtr)) {
            rtr = next(rtr);
        }
        return rtr;
    }

    public static final Day day11a = ls -> {
        if (ls == null || ls.isEmpty())
            return "";
        return String.format("%s", nextPassword(ls.get(0)));
    };

    public static final Day day11b = ls -> {
        if (ls == null || ls.isEmpty())
            return "";
        return String.format("%s", nextPassword(nextPassword(ls.get(0))));
    };

}
