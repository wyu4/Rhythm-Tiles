import Content.GameManager;
import Content.RTContainers.RTFrame;
import Content.RTContainers.RTPanel;
import Content.RTContainers.RTScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameTest extends RTFrame implements KeyListener {
    private final JScrollPane scrollPane;
    private final JTextArea view;

    public GameTest() {
        setSize(500, 1000);
        setLayout(new BorderLayout());
        setUndecorated(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.BLACK);
        addKeyListener(this);

        view = new JTextArea("View");
        view.setFont(new Font("Arial", Font.PLAIN, 25));
        view.setBackground(Color.WHITE);
        view.setBounds(0, 0, 500, 200);

        scrollPane = new JScrollPane(view);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
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
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}