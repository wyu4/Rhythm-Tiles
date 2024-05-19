package Content;

public class StringColors {
    private static final String F_BLACK = "\u001B[30m";
    private static final String F_RED = "\u001B[31m";
    private static final String F_GREEN = "\u001B[32m";
    private static final String F_YELLOW = "\u001B[33m";
    private static final String F_BLUE = "\u001B[34m";
    private static final String F_PURPLE = "\u001B[35m";
    private static final String F_CYAN = "\u001B[36m";
    private static final String F_WHITE = "\u001B[37m";

    public enum FontColors {
        BLACK, RED, GREEN, YELLOW, BLUE, PURPLE, CYAN, WHITE
    }

    public static String toColorPattern(FontColors f) {
        return switch (f) {
            case BLACK -> F_BLACK;
            case RED -> F_RED;
            case GREEN -> F_GREEN;
            case YELLOW -> F_YELLOW;
            case BLUE -> F_BLUE;
            case PURPLE -> F_PURPLE;
            case CYAN -> F_CYAN;
            case WHITE -> F_WHITE;
        };
    }
}