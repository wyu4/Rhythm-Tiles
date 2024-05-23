package Content.RTContainers;

import Content.RTContainers.Interfaces.RTTab;

import java.util.ArrayList;
import java.util.List;

public abstract class RTTabRegisterer {
    private static final List<RTTab> registeredTabs = new ArrayList<RTTab>(); // List of registered registeredTabs

    /**
     * Register a tab.
     * @param tab The current tab interface
     */
    public static void registerRTTab(RTTab tab) {
        if (!isRegistered(tab)) { // Check if the tab isn't already registered
            registeredTabs.add(tab); // Add the tab to the registeredTabs list
        }
    }

    /**
     * Close all registered registeredTabs.
     */
    public static void closeAllTabs() {
        for (RTTab t : registeredTabs) { // Iterate through registeredTabs list
            if (t.isOpen()) { // Check if the tab is open
                t.closeTab(); // Close the tab if it's open
            }
        }
    }

    /**
     * Check if a tab is registered.
     * @param tab The tab to check
     * @return {@code true} if the tab is registered, {@code false} if the tab is not registered.
     */
    public static boolean isRegistered(RTTab tab) {
        return registeredTabs.contains(tab); // Return if the tab appears in the registeredTabs list
    }

    /**
     * Get the current number of registered tabs.
     * @return {@code int} representing the number of registered tabs
     */
    public static int getNumOfRegisteredTabs() {
        return registeredTabs.size();
    }
}
