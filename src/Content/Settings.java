package Content;

import java.util.ArrayList;
import java.util.List;

import static Content.StringColors.*;

public class Settings {

    private final long STARTTIME;
    private int fps;
    private final List<String> LOGS, ERRORS;

    /**
     * Create a new Settings object
     */
    public Settings() {
        this(60);
    }

    /**
     * Create a new Settings object
     * @param fps The desired FPS
     */
    public Settings(int fps) {
        STARTTIME = System.currentTimeMillis();
        this.fps = fps; // Setting the FPS

        LOGS = new ArrayList<String>();
        ERRORS = new ArrayList<String>();
    }

    /**
     * Get the current FPS
     * @return The user-set FPS
     */
    public int getFps() {
        return fps;
    }

    /**
     * Calculate the delta based on FPS
     * @return int (1000 / FPS)
     */
    public double calculateDelta() {
        return (1000.0 / fps);
    }

    /**
     * Set the FPS
     * @param fps The desired FPS
     */
    public void setFps(int fps) {
        this.fps = Math.abs(fps); // Make sure that fps is always positive
    }

    /**
     * Get the time elapsed since the creation of this object.
     * @return The time elapsed in milliseconds
     */
    public long getTimeElapsedMillis() {
        return System.currentTimeMillis() - STARTTIME;
    }

    /**
     * Get the time elapsed since the creation of this object.
     * @return The time elapsed in milliseconds
     */
    public double getTimeElapsedSec() {
        return (getTimeElapsedMillis() / 1000.0);
    }

    /**
     * Prints and saves a message to the log list (getLogs()).
     * @param message Message
     */
    public void log(String message) {
        System.out.println(toColorPattern(FontColors.GREEN) + "(Settings): " + message + toColorPattern(FontColors.WHITE));
        LOGS.add(message);
    }

    /**
     * Prints and saves a message into the error list (getErrors()).
     * @param message Message
     */
    public void error(String message) {
        System.out.println(toColorPattern(FontColors.RED) + "(Settings): " + message + toColorPattern(FontColors.WHITE));
        ERRORS.add(message);
    }

    /**
     * Get an array of all the logs
     * @return Array containing all logs printed via log()
     */
    public String[] getLogs() {
        String[] array = new String[LOGS.size()];
        LOGS.toArray(array);
        return array;
    }

    /**
     * Get an array of all the errors
     * @return Array containing all errors printed via error()
     */
    public String[] getErrors() {
        String[] array = new String[ERRORS.size()];
        ERRORS.toArray(array);
        return array;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "fps:" + fps +
                "}";
    }
}
