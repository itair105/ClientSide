package common;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by user on 1/3/2015.
 */
public class TreeStringComparator implements Comparator<String>, Serializable {
    public int compare(String s1, String s2) {
        return s1.compareTo(s2);
    }
}
