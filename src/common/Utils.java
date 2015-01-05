package common;

/**
 * Created by user on 12/15/2014.
 */
public class Utils {
    public static int getRandom(int maxNumber) {
        return (int)Math.floor(Math.random() * maxNumber) + 1;
    }
}
