package Content.RTComponents.Interfaces;

/**
 * Interface containing the necessary methods to register a tab in the Rhythm-Tiles game.
 */
public interface RTTab {
    /**
     * Open the tab while closing the other tabs.
     */
    void openTab();

    /**
     * Close the tab.
     */
    void closeTab();

    /**
     * Get the current open/close status of the tab
     * @return {@code true} if the tab is open, {@code false} if the tab is closed.
     */
    boolean isOpen();

    /**
     * Refreshes the tab component properties.
     */
    void refreshTab();
}
