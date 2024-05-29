import Content.MapPlayer;
import Content.RTComponents.RTAudio;
import Content.RTComponents.RTFrame;
import Content.RTComponents.RTPanel;
import Content.Settings;
import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameTest extends RTFrame implements KeyListener {
    private final MapPlayer player;

    public GameTest() {
        new JFXPanel(); // Work around "Toolkit not initialized" error

        Settings settings = new Settings(120);
        settings.setWindow(this);
        settings.setScreenSize(1280, 720);

        setLayout(new GridLayout(1, 3));
        setLocation(100, 50);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));
        addKeyListener(this);

        player = new MapPlayer(settings);

        add(new RTPanel("Empty", 0));
        add(player);
        add(new RTPanel("Empty", 0));

        setVisible(true);

        player.init();

        repaint();
        revalidate();
    }

    public static void main(String[] args) {
        System.out.println("Starting tester...");
        new GameTest();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            closeFrame();
        } else {
            player.handleKeyEvent(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}