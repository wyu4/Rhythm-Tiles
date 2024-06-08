package Content;

import Content.RTComponents.RTFrame;
import Content.RTComponents.RTPanel;
import Content.RTComponents.RTTabManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameManager extends RTFrame implements ActionListener {
    private final Settings settings;
    private final Timer clock;
    private final RTPanel contentPane;
    private final MainMenu mainMenu;
    private final LevelSelection levelSelection;

    public GameManager() {
        System.out.println("Starting game...");

        // Creating objects //
        // Settings
        settings = new Settings(60);
        settings.setWindow(this);
        settings.setScreenSize(1280, 720);

        clock = new Timer((int) settings.calculateDesiredDelta(), this);
        contentPane = new RTPanel();

        mainMenu = new MainMenu(settings, this);
        levelSelection = new LevelSelection(settings, this);

        // Config main stuff //
        // Main frame
        setName("WindowFrame");
        setContentPane(contentPane);
        setBackground(new Color(0, 0, 0, 0));
        setLocation(100, 50);
        setResizable(false);

        // Content pane
        contentPane.setName("WindowContentPane");

        // Main menu
        mainMenu.openTab();

        // Adding components
        contentPane.add(mainMenu);
        contentPane.add(levelSelection);

        // Program //
        setVisible(true);
        settings.log("Loaded " + RTTabManager.getNumOfRegisteredTabs() + " tabs in " + settings.getTimeElapsedSec() + " seconds. ");
    }

    public void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            settings.error("Error running delay block: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(clock)) {
            contentPane.repaint();
            return;
        }

        // Main menu buttons
        if (mainMenu.isOpen()) {
            if (e.getSource().equals(mainMenu.getButtons()[MainMenu.PLAY_BUTTON])) { // Play button
                levelSelection.openTab();
            } else if (e.getSource().equals(mainMenu.getButtons()[MainMenu.INFO_BUTTON])) { // Info button
                settings.error("The info button has no function yet.");
            } else if (e.getSource().equals(mainMenu.getButtons()[MainMenu.QUIT_BUTTON])) { // Quit button
                settings.log("User is quitting via Main Menu... (session lasted " + settings.getTimeElapsedSec() + " seconds)");
                closeFrame();
            }
        }

        // Level selection buttons
        else if (levelSelection.isOpen()) {
            if (e.getSource().equals(levelSelection.getBackButton())) {
                mainMenu.openTab();
            }
        }
    }
}
