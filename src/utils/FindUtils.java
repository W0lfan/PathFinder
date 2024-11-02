package utils;

import java.util.ArrayList;

public abstract class FindUtils {
    public static int isInInstructions(ArrayList<String[]> i, String[] k) {
        int $ = 0;
        while ($ < i.size() && i.get($)[0] != k[0]) $++;
        return $ >= i.size() ? -1 : $;
    }

    public static int charIndex(String c) {
        String[] $ = {"R","L","D","U"};
        int i = 0;
        while (i<$.length && $[i] != c) i++;
        return i >= $.length ? -1 : i;
    }
}
