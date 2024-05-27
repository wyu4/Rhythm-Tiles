package Content;

import Content.RTContainers.Interfaces.RTTab;
import Content.RTContainers.RTButton;
import Content.RTContainers.RTPanel;
import Content.RTContainers.RTTabManager;

import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenu extends RTPanel implements RTTab {
    public static final int PLAY_BUTTON = 0;
    public static final int INFO_BUTTON = 1;
    public static final int QUIT_BUTTON = 2;

    private final Settings settings;
    private final ActionListener actionListener;

    private final BorderLayout mainLayout;
    private final RTPanel bottomPanel, centerPanel, rightPanel;
    private final RTButton playButton, infoButton, quitButton;

    public MainMenu(Settings settings, ActionListener actionListener) {
        // Instance variables //
        this.settings = settings;
        this.actionListener = actionListener;

        // New objects //
        mainLayout = new BorderLayout();

        bottomPanel = new RTPanel(getClass().getName()+"-BottomPanel");
        centerPanel = new RTPanel(getClass().getName()+"-CenterPanel");
        rightPanel = new RTPanel(getClass().getName()+"-RightPanel");

        playButton = new RTButton(getClass().getName()+"-Play", "Play");
        infoButton = new RTButton(getClass().getName()+"-Info", "Info");
        quitButton = new RTButton(getClass().getName()+"-Quit", "Quit");

        // Set properties
        refreshTab();

        // Adding components //
        // Content Panel
        add(bottomPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        // Right Panel
        rightPanel.add(playButton);
        rightPanel.add(infoButton);
        rightPanel.add(quitButton);

        // Register the tab
        RTTabManager.registerRTTab(this);
        setVisible(false);

        repaint();
    }

    public RTButton[] getButtons() {
        return new RTButton[] {playButton, infoButton, quitButton};
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
        settings.log("Refreshing main menu...");
        // Config GUI //
        // Content Panel
        setName("MainMenu-ContentPanel");
        setLocation(0, 0);
        setSize(settings.getScreenSize());
        setLayout(mainLayout);
        setBackground(new Color(0, 0, 0, 0));

        // Bottom Panel
        bottomPanel.setPreferredSize(new Dimension(getWidth(), getHeight()/10));
        bottomPanel.setBackground(new Color(40, 40, 51));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Center Panel
        centerPanel.setPreferredSize(new Dimension((getWidth()/3)*2, getHeight()));
        centerPanel.setBackground(new Color(0, 0, 0));
        centerPanel.setLayout(null);

        // Right Panel
        rightPanel.setPreferredSize(new Dimension(getWidth()/3, getHeight()));
        rightPanel.setBackground(new Color(0, 0, 0));
        rightPanel.setLayout(new GridLayout(3, 1));

        // Play Button
        playButton.addActionListener(actionListener);
        playButton.setBackground(new Color(30, 47, 64));

        // Info Button
        infoButton.addActionListener(actionListener);
        infoButton.setBackground(new Color(49, 67, 85));

        // Quit Button
        quitButton.addActionListener(actionListener);
        quitButton.setBackground(new Color(30, 47, 64));

        repaint();
        revalidate();
    }
}
