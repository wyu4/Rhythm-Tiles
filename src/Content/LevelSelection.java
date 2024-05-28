package Content;

import Content.RTComponents.*;
import Content.RTComponents.Interfaces.RTTab;

import java.awt.*;
import java.awt.event.ActionListener;

public class LevelSelection extends RTPanel implements RTTab {
    private final Settings settings;
    private final ActionListener actionListener;

    private final GridLayout mainLayout;
    private final BorderLayout leftLayout;
    private final GridBagLayout rightLayout;
    private final GridBagConstraints rightContraints;

    private final RTPanel leftPanel, rightPanel, leftTopPanel;
    private final RTButton backButton;

    public LevelSelection(Settings settings, ActionListener actionListener) {
        // Instance variables //
        this.settings = settings;
        this.actionListener = actionListener;

        // New objects //
        mainLayout = new GridLayout(1, 2);
        leftLayout = new BorderLayout();
        rightLayout = new GridBagLayout();
        rightContraints = new GridBagConstraints();

        leftPanel = new RTPanel(getClass().getName()+"-LeftPanel");
        leftTopPanel = new RTPanel(getClass().getName()+"-LeftTopPanel", 0);
        rightPanel = new RTPanel(getClass().getName()+"-RightPanel");
        backButton = new RTButton(getClass().getName()+"-Back");

        // Set properties
        refreshTab();

        RTTabManager.registerRTTab(this);

        // Adding components
        leftTopPanel.add(backButton);
        leftPanel.add(leftTopPanel, BorderLayout.NORTH);

        add(leftPanel);
        add(rightPanel);

        repaint();
        revalidate();
    }

    public RTButton getBackButton() {
        return backButton;
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

        // Left Top Panel
        leftTopPanel.setLayout(null);
        leftTopPanel.setPreferredSize(new Dimension(0, (int) settings.getScreenSize().getHeight()/15));

        // Right Panel
        rightPanel.setBackground(new Color(20, 0, 37));
        rightPanel.setLayout(rightLayout);

        // Back Button
        RTImageIcon backIcon = new RTImageIcon(Resources.Images.BACK);

        backButton.addActionListener(actionListener);
        backButton.setSize((int) leftTopPanel.getPreferredSize().getHeight(), (int) leftTopPanel.getPreferredSize().getHeight());
        backButton.setBackground(new Color(0, 0, 0, 0));
        backButton.setBorder(null);
        backButton.setContentAreaFilled(false);

        backIcon.resizeIcon(backButton.getSize());

        backButton.setIcon(backIcon);
    }
}
