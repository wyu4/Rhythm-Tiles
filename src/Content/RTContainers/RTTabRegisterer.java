package Content.RTContainers;

import Content.RTContainers.Interfaces.RTTab;

import java.util.ArrayList;
import java.util.List;

public abstract class RTTabRegisterer {
    private static List<RTTab> tabs = new ArrayList<RTTab>(); // List of registered tabs

    /**
     * Register a tab.
     * @param tab The current tab interface
     */
    public static void registerRTTab(RTTab tab) {
        if (!isRegistered(tab)) { // Check if the tab isn't already registered
            tabs.add(tab);
        }
    }

    /**
     * Close all registered tabs.
     */
    public static void closeAllTabs() {
        for (RTTab t : tabs) {
            if (t.isOpen()) {
                t.closeTab();
            }
        }
    }

    /**
     * Check if a tab is registered.
     * @param tab The tab to check
     * @return {@code true} if the tab is registered, {@code false} if the tab is not registered.
     */
    public static boolean isRegistered(RTTab tab) {
        return tabs.contains(tab);
    }
}
