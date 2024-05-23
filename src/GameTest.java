
import Content.RTContainers.RTFrame;
import Content.RTContainers.RTImageIcon;
import Content.RTContainers.RTPanel;
import Content.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameTest implements KeyListener, ActionListener {
    private Settings settings;
    private RTFrame mainFrame;
    private RTPanel contentPanel;
    private Timer clock;

    private Long lastFrame, actualDelta;

    // The game test constructor (I want everything to run in a non-static method)
    public GameTest() {
        settings = new Settings(60);

        mainFrame = new RTFrame();
        contentPanel = new RTPanel();

        mainFrame.setBounds(100, 100, 500, 500);
        mainFrame.setBackground(new Color(0, 0, 0, 0));
        mainFrame.setLayout(null);
        mainFrame.addKeyListener(this);

        contentPanel.setLayout(new GridLayout(1, 1));
        contentPanel.setBackground(new Color(0, 0, 0, 0));

        mainFrame.setContentPane(contentPanel);

        RTImageIcon testImage = new RTImageIcon("src/Content/Resources/TestImage.png", 0.5f);
        JLabel testLabel = new JLabel(testImage);
        testLabel.setBackground(new Color(0, 0, 0, 0));

        contentPanel.add(testLabel);
        mainFrame.setVisible(true);

        clock = new Timer((int) settings.calculateDesiredDelta(), this);

        settings.log("Loaded test in " + settings.getTimeElapsedSec() + " seconds.");

        // Program
        clock.start();
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    // Main method (calls constructor)
    public static void main(String[] args) {
        System.out.println("Launching game tester class...");
        new GameTest();
    }

    // Delay block, pauses the thread for a certain amount of milliseconds
    public void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            settings.error("Error running the delay block: " + e.getCause());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        long currentTime = settings.getTimeElapsedMillis();

        if (e.getSource().equals(clock)) {
            if (lastFrame == null) {
                lastFrame = currentTime;
                return;
            }

            if (mainFrame.getWidth() > 2000) {
                mainFrame.setSize(500, 500);
            } else {
                mainFrame.setSize(mainFrame.getWidth() + 10, mainFrame.getHeight());
            }

            actualDelta = (currentTime - lastFrame);
            lastFrame = currentTime;
        }
    }
}
