package Content;

import Content.RTContainers.Layers.InvisibleLayer;
import Content.RTContainers.Layers.MainBackgroundLayer;
import Content.RTContainers.RTButton;
import Content.RTContainers.RTFrame;
import Content.RTContainers.RTPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends RTPanel implements ActionListener {
    private static final int BG_OPACITY = 100; // For testing purposes, value between 0-255 allows panel visibility

    private final Settings settings;
    private final RTFrame mainFrame;

    private final RTPanel bottomPanel, centerPanel, rightPanel;
    private final RTButton playButton, infoButton, quitButton;
    private boolean userQuitted;

    public MainMenu(Settings settings, RTFrame mainFrame) {
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
        setBackground(new Color(0, 0, 0, BG_OPACITY));

        // Bottom Panel
        bottomPanel.setPreferredSize(new Dimension(getWidth(), getHeight()/10));
        bottomPanel.setBackground(new Color(0, 255, 0, BG_OPACITY));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Center Panel
        centerPanel.setPreferredSize(new Dimension((getWidth()/3)*2, getHeight()));
        centerPanel.setBackground(new Color(255, 0, 0, BG_OPACITY));
        centerPanel.setLayout(null);

        // Right Panel
        rightPanel.setPreferredSize(new Dimension(getWidth()/3, getHeight()));
        rightPanel.setBackground(new Color(0, 0, 255, BG_OPACITY));
        rightPanel.setLayout(new GridLayout(3, 1));

        // Play Button
        playButton.addActionListener(this);

        // Info Button
        infoButton.addActionListener(this);

        // Quit Button
        quitButton.addActionListener(this);

        // Adding components //
        // Content Panel
        add(bottomPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        // Right Panel
        rightPanel.add(playButton);
        rightPanel.add(infoButton);
        rightPanel.add(quitButton);

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isVisible() && !userQuitted) { // Only perform actions if the main menu is visible

            if (e.getSource().equals(quitButton)) { // User pressed the quit button
                userQuitted = true;
                mainFrame.closeFrame();
            }

        }
    }
}
