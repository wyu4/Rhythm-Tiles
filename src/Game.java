import Content.MainMenu;
import Content.RTContainers.RTFrame;
import Content.RTContainers.RTPanel;
import Content.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game implements ActionListener {
    private final Timer clock;
    private final RTFrame frame;
    private final RTPanel contentPane;

    public Game() {
        System.out.println("Starting game...");

        // Creating objects //
        Settings settings = new Settings(60);
        clock = new Timer((int) settings.calculateDelta(), this);
        frame = new RTFrame();
        contentPane = new RTPanel();

        MainMenu mainMenu = new MainMenu(settings, frame);

        // Config main stuff //
        // Main frame
        frame.setName("WindowFrame");
        frame.setContentPane(contentPane);
        frame.setBackground(new Color(0, 0, 0, 0));

        // Content pane
        contentPane.setName("WindowContentPane");

        // Main menu


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
        }
    }
}
