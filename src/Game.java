import Content.LevelSelection;
import Content.MainMenu;
import Content.RTContainers.RTFrame;
import Content.RTContainers.RTPanel;
import Content.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class Game implements ActionListener {
    private final Settings settings;
    private final Timer clock;
    private final RTFrame frame;
    private final RTPanel contentPane;
    private final MainMenu mainMenu;
    private final LevelSelection levelSelection;

    public Game() {
        System.out.println("Starting game...");

        // Creating objects //
        settings = new Settings(60);
        clock = new Timer((int) settings.calculateDesiredDelta(), this);
        frame = new RTFrame();
        contentPane = new RTPanel();

        mainMenu = new MainMenu(settings, frame, this);
        levelSelection = new LevelSelection(settings, frame, this);

        // Config main stuff //
        // Main frame
        frame.setName("WindowFrame");
        frame.setContentPane(contentPane);
        frame.setBackground(new Color(0, 0, 0, 0));

        // Content pane
        contentPane.setName("WindowContentPane");

        // Main menu
        mainMenu.openTab();

        // Adding components
        contentPane.add(mainMenu);

        // Program //
        frame.setVisible(true);
        settings.log("Loaded in " + settings.getTimeElapsedSec() + " seconds.");
    }

    public static void main(String[] args) {
        new Game();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(clock)) {
            contentPane.repaint();
            return;
        }

        // Main menu buttons
        if (e.getSource().equals(mainMenu.getButtons()[0])) { // Play button
            levelSelection.openTab();
        } else if (e.getSource().equals(mainMenu.getButtons()[1])) { // Info button
            frame.closeFrame();
        } else if (e.getSource().equals(mainMenu.getButtons()[2])) { // Quit button
            frame.closeFrame();
        }

    }
}
