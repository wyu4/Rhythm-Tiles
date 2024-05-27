package Content;

import Content.RTContainers.Interfaces.RTTab;
import Content.RTContainers.RTButton;
import Content.RTContainers.RTFrame;
import Content.RTContainers.RTPanel;
import Content.RTContainers.RTTabManager;

import java.awt.*;
import java.awt.event.ActionListener;

public class LevelSelection extends RTPanel implements RTTab {
    private final Settings settings;
    private final ActionListener actionListener;

    private final GridLayout mainLayout;
    private final BorderLayout leftLayout;
    private final RTPanel leftPanel, rightPanel;
    private final RTButton backButton;

    public LevelSelection(Settings settings, ActionListener actionListener) {
        // Instance variables //
        this.settings = settings;
        this.actionListener = actionListener;

        // New objects //
        mainLayout = new GridLayout(1, 2);
        leftLayout = new BorderLayout();

        leftPanel = new RTPanel(getClass().getName()+"-LeftPanel");
        rightPanel = new RTPanel(getClass().getName()+"-RightPanel");
        backButton = new RTButton(getClass().getName()+"-Back");

        // Set properties
        refreshTab();

        RTTabManager.registerRTTab(this);
    }

    @Override
    public void openTab() {
        RTTabManager.closeAllTabs();
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

    @Override
    public void refreshTab() {
        settings.log("Refreshing LevelSelection...");

        // Main Panel
        setName("LevelSelection-MainPanel");
        setLayout(mainLayout);
        setLocation(0, 0);
        setSize(settings.getScreenSize());
        setBackground(new Color(0, 0, 0, 0));

        // Left Panel
        leftPanel.setBackground(new Color(0, 0, 0));
        leftPanel.setLayout(leftLayout);


        // Back Button
        backButton.addActionListener(actionListener);
    }
}
