package Content;

import Content.RTContainers.Interfaces.RTTab;
import Content.RTContainers.RTButton;
import Content.RTContainers.RTFrame;
import Content.RTContainers.RTPanel;
import Content.RTContainers.RTTabRegisterer;

import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenu extends RTPanel implements RTTab {
    public static final int PLAY_BUTTON = 0;
    public static final int INFO_BUTTON = 1;
    public static final int QUIT_BUTTON = 2;

    private final Settings settings;
    private final RTFrame mainFrame;

    private final RTPanel bottomPanel, centerPanel, rightPanel;
    private final RTButton playButton, infoButton, quitButton;
    private boolean userQuitted;

    public MainMenu(Settings settings, RTFrame mainFrame, ActionListener actionListener) {
        // Instance variables //
        this.settings = settings;
        this.mainFrame = mainFrame;

        // New objects //
        bottomPanel = new RTPanel("MainMenu-BottomPanel");
        centerPanel = new RTPanel("MainMenu-CenterPanel");
        rightPanel = new RTPanel("MainMenu-RightPanel");

        playButton = new RTButton("MainMenu-Play", "Play");
        infoButton = new RTButton("MainMenu-Info", "Info");
        quitButton = new RTButton("MainMenu-Quit", "Quit");

        userQuitted = false;

        // Config GUI //
        // Content Panel
        setName("MainMenu-ContentPanel");
        setBounds(0, 0, mainFrame.getWidth(), mainFrame.getHeight());
        setLayout(new BorderLayout());
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
        RTTabRegisterer.registerRTTab(this);
        setVisible(false);

        repaint();
    }

    public RTButton[] getButtons() {
        return new RTButton[] {playButton, infoButton, quitButton};
    }

    public void quit() {
        userQuitted = true;
        settings.log("User is quitting...");
        mainFrame.closeFrame();
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
