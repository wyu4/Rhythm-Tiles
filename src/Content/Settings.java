package Content;

import Content.RTComponents.RTFrame;
import Content.RTComponents.RTTabManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static Content.StringColors.*;

public class Settings {
    private final long startTime;
    private final List<String> logs, errors;
    private int fps;
    private Dimension screenSize;
    private RTFrame window;
    private int[] keybinds;

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
        keybinds = new int[] {
                KeyEvent.VK_Q,
                KeyEvent.VK_W,
                KeyEvent.VK_O,
                KeyEvent.VK_P
        };
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

    /**
     * Get the current set screen size
     * @return {@code Dimension} The screen size
     */
    public Dimension getScreenSize() {
        return screenSize;
    }

    /**
     * Set the new screen size
     * @param width The width of the new screen size
     * @param height The height of the new screen size
     */
    public void setScreenSize(int width, int height) {
        setScreenSize(new Dimension(width, height));
    }

    /**
     * Set the new screen size
     * @param screenSize {@code Dimension} The new screen size
     */
    public void setScreenSize(Dimension screenSize) {
        this.screenSize = screenSize;
        RTTabManager.refreshAllOpenTabs();

        if (window != null) { // Check if the variable window isn't null
            window.setSize(screenSize);
        }
    }

    /**
     * Set the main window of the game. The window is used in methods such as setScreenSize();
     * @param window {@code RTFrame} Main window
     */
    public void setWindow(RTFrame window) {
        this.window = window;
    }

    public void repaintWindow() {
        if (window != null) {
            window.repaint();
        }
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

    /**
     * Get the key bound to a specific column
     * @param i Column number (0-3)
     * @return The key event bound to the column number
     */
    public int getKeybind(int i) {
        return keybinds[Math.min(i, keybinds.length-1)];
    }

    @Override
    public String toString() {
        return "Settings{" +
                "fps:" + fps + "," +
                "screensize{w:" + screenSize.getWidth() + ",h:" + getScreenSize().getHeight() +
                "}";
    }
}
