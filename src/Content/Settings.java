package Content;

import Content.RTContainers.RTFrame;
import Content.RTContainers.RTPanel;
import Content.RTContainers.RTTabManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Content.StringColors.*;

public class Settings {

    private final long startTime;
    private final List<String> logs, errors;
    private int fps;
    private Dimension screenSize;
    private RTFrame window;

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
        startTime = System.currentTimeMillis();
        this.fps = fps; // Setting the FPS

        logs = new ArrayList<String>();
        errors = new ArrayList<String>();
        screenSize = new Dimension();
    }

    /**
     * Get the current FPS
     * @return The user-set FPS
     */
    public int getFps() {
        return fps;
    }

    /**
     * Calculate the delta in milliseconds based on FPS
     * @return int (1000 / FPS)
     */
    public double calculateDesiredDelta() {
        return (1000.0 / fps);
    }

    public Dimension getScreenSize() {
        return screenSize;
    }

    public int getScreenWidth() {
        return (int) screenSize.getWidth();
    }

    public int getScreenHeight() {
        return (int) screenSize.getHeight();
    }

    public void setScreenSize(int width, int height) {
        setScreenSize(new Dimension(width, height));
    }

    public void setScreenSize(Dimension screenSize) {
        this.screenSize = screenSize;
        RTTabManager.refreshAllOpenTabs();
        window.setSize(screenSize);
    }

    public void setWindow(RTFrame window) {
        this.window = window;
    }

    public RTFrame getWindow() {
        return window;
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
        return System.currentTimeMillis() - startTime;
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
        logs.add(message);
    }

    /**
     * Prints and saves a message into the error list (getErrors()).
     * @param message Message
     */
    public void error(String message) {
        System.out.println(toColorPattern(FontColors.RED) + "(Settings): " + message + toColorPattern(FontColors.WHITE));
        errors.add(message);
    }

    /**
     * Get an array of all the logs
     * @return Array containing all logs printed via log()
     */
    public String[] getLogs() {
        String[] array = new String[logs.size()];
        logs.toArray(array);
        return array;
    }

    /**
     * Get an array of all the errors
     * @return Array containing all errors printed via error()
     */
    public String[] getErrors() {
        String[] array = new String[errors.size()];
        errors.toArray(array);
        return array;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "fps:" + fps +
                "}";
    }
}
