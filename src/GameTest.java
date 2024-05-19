import Content.RTContainers.Layers.MainBackgroundLayer;
import Content.RTContainers.Layers.Templates.AlphaGradientLayer;
import Content.RTContainers.RTFrame;
import Content.RTContainers.RTPanel;
import Content.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameTest implements KeyListener, ActionListener {
    private Settings settings;
    private RTFrame mainFrame;
    private RTPanel contentPanel, backgroundPanel1, backgroundPanel2;
    private Timer clock;

    // The game test constructor (I want everything to run in a non-static method)
    public GameTest() {
        settings = new Settings(60);

        mainFrame = new RTFrame();
        contentPanel = new RTPanel();
        backgroundPanel1 = new RTPanel();
        backgroundPanel2 = new RTPanel();

        mainFrame.setBounds(100, 100, 500, 500);
        mainFrame.setBackground(new Color(0, 0, 0, 100));
        mainFrame.setLayout(null);
        mainFrame.addKeyListener(this);

        contentPanel.setLayout(new GridLayout(2, 3));
        contentPanel.setBackground(new Color(0, 0, 0, 0));
        contentPanel.setBounds(0, 0, 500, 500);

        backgroundPanel1.setLayout(new BorderLayout());
        backgroundPanel1.setBackground(new Color(0, 0, 0, 0));
        backgroundPanel1.setBounds(0, 0, 500, 500);

        backgroundPanel2.setLayout(null);
        backgroundPanel2.setBackground(new Color(0, 0, 0, 100));
        backgroundPanel2.setBounds(0, 0, 500, 500);

        AlphaGradientLayer mainBgLayer = new AlphaGradientLayer();
        mainBgLayer.setAlpha(0.75f);
        mainBgLayer.setGradientColor1(new Color(0, 196, 255));
        mainBgLayer.setGradientColor2(new Color(0, 255, 0));

        JLayer<JComponent> bgLayer = new JLayer<>(backgroundPanel2, mainBgLayer);

        mainFrame.getContentPane().add(backgroundPanel1);
        backgroundPanel1.add(contentPanel);
        backgroundPanel1.add(bgLayer, BorderLayout.CENTER);

        RTPanel testPanel1 = new RTPanel();
        RTPanel testPanel2 = new RTPanel();
        RTPanel testPanel3 = new RTPanel();

        testPanel1.setPreferredSize(new Dimension(500, 100));
        testPanel1.setBackground(new Color(255, 0 ,0));
        testPanel2.setPreferredSize(new Dimension(100, 500));
        testPanel2.setBackground(new Color(0, 255, 0));
        testPanel3.setPreferredSize(new Dimension(100, 500));
        testPanel3.setBackground(new Color(0, 0, 255));

        contentPanel.add(testPanel1); contentPanel.add(new RTPanel()); contentPanel.add(testPanel3);
        contentPanel.add(new RTPanel()); contentPanel.add(testPanel2); contentPanel.add(new RTPanel());
        // contentPanel.add(bgLayer);

        mainFrame.setVisible(true);

        clock = new Timer((int) settings.calculateDelta(), this);

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
        if (e.getSource().equals(clock)) {
//            mainFrame.setLocation(mainFrame.getLocation().x + 1, 0);
            // mainFrame.repaint();
        }
    }
}
