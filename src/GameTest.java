import Content.*;
import Content.RTComponents.RTFrame;
import Content.RTComponents.RTPanel;
import javafx.embed.swing.JFXPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameTest extends RTFrame implements KeyListener {
    private final MapPlayer player;

    private final List<String> keysPressed;

    public GameTest() {
        new JFXPanel(); // Work around "Toolkit not initialized" error

        keysPressed = new ArrayList<>();

        Settings settings = new Settings(120);
        settings.setWindow(this);
        settings.setScreenSize(1280, 720);

        setLayout(new GridLayout(1, 3));
        setLocation(100, 50);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));
        addKeyListener(this);

        player = new MapPlayer(settings, false);
        try {
            Map map = Map.openJSON(new File(Resources.Maps.TEST));
             player.setCurrentMap(map);
        } catch (IOException e) {
            settings.error("Could not open file: " + e.getMessage());
        }

        add(new RTPanel("Empty", 0));
        add(player);
        add(new RTPanel("Empty", 0));

        setVisible(true);

        player.refresh();

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
        int keycode = e.getKeyCode();
        if (!keysPressed.contains(String.valueOf(keycode))) {
            keysPressed.add(String.valueOf(keycode));

            if (keycode == KeyEvent.VK_ESCAPE) {
                closeFrame();
            } else {
                player.handleKeyEvent(e);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keycode = e.getKeyCode();
        keysPressed.remove(String.valueOf(keycode));
    }
}