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
    private final GridBagLayout rightLayout, leftCenterLayout;
    private final GridBagConstraints rightContraints, leftCenterConstraints;

    private final RTPanel leftPanel, rightPanel, leftTopPanel, leftCenterPanel;
    private final RTButton backButton;
    private final MapPlayer previewPlayer;

    public LevelSelection(Settings settings, ActionListener actionListener) {
        // Instance variables //
        this.settings = settings;
        this.actionListener = actionListener;

        // New objects //
        mainLayout = new GridLayout(1, 2);
        leftCenterLayout = new GridBagLayout();
        leftLayout = new BorderLayout();
        rightLayout = new GridBagLayout();
        rightContraints = new GridBagConstraints();
        leftCenterConstraints = new GridBagConstraints();

        leftPanel = new RTPanel(getClass().getName()+"-LeftPanel");
        leftTopPanel = new RTPanel(getClass().getName()+"-LeftTopPanel", 0);
        leftCenterPanel = new RTPanel(getClass().getName()+"-LeftCenterPanel");
        rightPanel = new RTPanel(getClass().getName()+"-RightPanel");
        backButton = new RTButton(getClass().getName()+"-Back");
        previewPlayer = new MapPlayer(settings, true);

        // Set properties
        refreshTab();

        RTTabManager.registerRTTab(this);

        // Adding components
        leftTopPanel.add(backButton);

        leftCenterPanel.add(previewPlayer, leftCenterConstraints);

        leftPanel.add(leftTopPanel, BorderLayout.NORTH);
        leftPanel.add(leftCenterPanel, BorderLayout.CENTER);



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
        previewPlayer.refresh();
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

        // Left Center Panel
        leftCenterPanel.setLayout(leftCenterLayout);
        leftCenterPanel.setAlpha(0.1f);
        leftCenterPanel.setBackground(new Color(55, 81, 70));

        leftCenterConstraints.fill = GridBagConstraints.VERTICAL;
        leftCenterConstraints.weightx = 0.5;
        leftCenterConstraints.gridwidth = 1;
        leftCenterConstraints.gridheight = 1;
        leftCenterConstraints.gridx = 0;
        leftCenterConstraints.gridy = 0;

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

        // Preview player
        previewPlayer.refresh();

        backButton.setIcon(backIcon);
    }
}
