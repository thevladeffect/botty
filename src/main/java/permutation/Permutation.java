package permutation;

import java.util.Arrays;

public class Permutation {

    public static Boolean check(String first, String second) {
        if (first.length() != second.length()) return false;

        char[] f = first.toCharArray();
        char[] s = second.toCharArray();

        Arrays.sort(f);
        Arrays.sort(s);

        return Arrays.equals(f, s);
    }
}
