package Content;

import Content.RTContainers.Interfaces.RTTab;
import Content.RTContainers.RTFrame;
import Content.RTContainers.RTPanel;
import Content.RTContainers.RTTabRegisterer;

import java.awt.event.ActionListener;

public class LevelSelection extends RTPanel implements RTTab {
    private final Settings settings;
    private final RTFrame mainFrame;

    public LevelSelection(Settings settings, RTFrame mainFrame, ActionListener actionListener) {
        this.settings = settings;
        this.mainFrame = mainFrame;

        RTTabRegisterer.registerRTTab(this);
    }

    @Override
    public void openTab() {
        RTTabRegisterer.closeAllTabs();
        setVisible(true);
    }

    @Override
    public void closeTab() {
        setVisible(false);
    }

    @Override
    public boolean isOpen() {
        return isVisible();
    }
}
