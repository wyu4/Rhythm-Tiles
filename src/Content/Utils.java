package Content;

public class Utils {
    public static final String F_BLACK = "\u001B[30m";
    public static final String F_RED = "\u001B[31m";
    public static final String F_GREEN = "\u001B[32m";
    public static final String F_YELLOW = "\u001B[33m";
    public static final String F_BLUE = "\u001B[34m";
    public static final String F_PURPLE = "\u001B[35m";
    public static final String F_CYAN = "\u001B[36m";
    public static final String F_WHITE = "\u001B[37m";

    /**
     * Clamp a numeric value between a minimum and a maximum value
     * @param n Numeric value
     * @param min The minimum value
     * @param max The maximum value
     * @return The clamped value
     */
    public static int clamp(int n, int min, int max) {
        return Math.min(max, Math.max(n, min));
    }

    public static long floor(double n) {
        return Math.round(n-0.5);
    }

    public static long ceil(double n) {
        return Math.round(n+0.5);
    }

    public static void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {

        }
    }
}