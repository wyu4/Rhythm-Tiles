import Content.MapPlayer;
import Content.RTComponents.*;
import Content.Resources;
import Content.Settings;
import javafx.embed.swing.JFXPanel;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MapBuilder extends RTFrame implements KeyListener, ActionListener {
    private final Settings settings;
    private final RTPanel contentPanel, bottomPanel;
    private final RTImageIcon exitIcon, pauseIcon, playIcon, saveIcon, restartIcon;
    private final RTButton exitButton, pauseButton, playButton, savebutton, restartButton;
    private final BorderLayout frameLayout;
    private final GridLayout contentLayout;
    private final GridBagLayout bottomLayout;

    private List<List<Double>> tiles;
    private String name, description, audioPath;
    private double spawnToGoal;
    private final RTAudio audio;

    private boolean recording;

    public MapBuilder() {
        tiles = new ArrayList<>();
        tiles.add(new ArrayList<>());
        tiles.add(new ArrayList<>());
        tiles.add(new ArrayList<>());
        tiles.add(new ArrayList<>());

        recording = false;

        exitIcon = new RTImageIcon(Resources.Images.EXIT);
        pauseIcon = new RTImageIcon(Resources.Images.PAUSE);
        playIcon = new RTImageIcon(Resources.Images.PLAY);
        saveIcon = new RTImageIcon(Resources.Images.SAVE);
        restartIcon = new RTImageIcon(Resources.Images.RESTART);

        settings = new Settings(120);
        contentPanel = new RTPanel("ContentPanel");
        bottomPanel = new RTPanel("BottomPanel");
        exitButton = new RTButton("Exit");
        pauseButton = new RTButton("Pause");
        playButton = new RTButton("Play");
        savebutton = new RTButton("Save");
        restartButton = new RTButton("Restart");

        frameLayout = new BorderLayout();
        contentLayout = new GridLayout(1, 3);
        bottomLayout = new GridBagLayout();

        refresh();

        GridBagConstraints bottomConstraints = new GridBagConstraints();
        bottomConstraints.gridy = 0;
        bottomConstraints.gridx = 0;
        bottomConstraints.insets = new Insets(
                (int)(settings.getScreenSize().getWidth()/100),
                (int)(settings.getScreenSize().getWidth()/100),
                (int)(settings.getScreenSize().getWidth()/100),
                (int)(settings.getScreenSize().getWidth()/100)
                );
        bottomConstraints.gridheight = 1;
        bottomConstraints.gridwidth = 1;
        bottomConstraints.fill = GridBagConstraints.CENTER;

        // Adding components

        bottomPanel.add(exitButton, bottomConstraints);

        bottomConstraints.gridx = 1;
        bottomPanel.add(pauseButton, bottomConstraints);

        bottomConstraints.gridx = 2;
        bottomPanel.add(playButton, bottomConstraints);

        bottomConstraints.gridx = 3;
        bottomPanel.add(savebutton, bottomConstraints);

        bottomConstraints.gridx = 4;
        bottomPanel.add(restartButton, bottomConstraints);

        exitButton.addActionListener(this);
        pauseButton.addActionListener(this);
        playButton.addActionListener(this);
        savebutton.addActionListener(this);
        restartButton.addActionListener(this);

        add(bottomPanel, BorderLayout.SOUTH);
        add(contentPanel, BorderLayout.CENTER);
        addKeyListener(this);

        revalidate();
        repaint();

        // Getting map configs
        name = getStringInput("Choose a name");
        description = getStringInput("Write a description");
        audioPath = getPath("Choose audio");
        spawnToGoal = getDoubleInput("Input the time (seconds) that each tile takes to travel from the spawn to the goal.");
        double rate = getDoubleInput("Input the playback rate (only applied in the editor).");

        audio = new RTAudio(audioPath);

        recording = true;
        audio.setRate(rate);
        audio.play();
    }

    public void refresh() {
        settings.setWindow(this);
        settings.setScreenSize(Toolkit.getDefaultToolkit().getScreenSize());

        setName("Builder-Frame");
        setBackground(new Color(0, 0, 0, 0));
        setVisible(true);
        setLayout(frameLayout);

        contentPanel.setBackground(new Color(0, 0, 0));
        contentPanel.setPreferredSize(new Dimension(
                (int) settings.getScreenSize().getWidth(), (int) (settings.getScreenSize().getHeight()*0.8)
        ));
        contentPanel.setAlpha(0.5f);
        contentPanel.setLayout(contentLayout);
        contentPanel.setVisible(true);

        bottomPanel.setBackground(new Color(0, 0, 0));
        bottomPanel.setPreferredSize(new Dimension(
                (int) settings.getScreenSize().getWidth(), (int) (settings.getScreenSize().getHeight()*0.2)
        ));
        bottomPanel.setAlpha(0.75f);
        bottomPanel.setLayout(bottomLayout);
        bottomPanel.setVisible(true);

        exitButton.setIcon(exitIcon);
        exitButton.setBackground(new Color(32, 32, 32));
        exitButton.setBorder(null);
        exitButton.setSize(
                (int) (settings.getScreenSize().getHeight()*0.2), (int) (settings.getScreenSize().getHeight()*0.2)
        );
        exitButton.setPreferredSize(exitButton.getSize());
        exitIcon.resizeIcon(
                (int) (settings.getScreenSize().getHeight()*0.2), (int) (settings.getScreenSize().getHeight()*0.2)
        );

        pauseButton.setIcon(pauseIcon);
        pauseButton.setBackground(new Color(32, 32, 32));
        pauseButton.setBorder(null);
        pauseButton.setSize(
                (int) (settings.getScreenSize().getHeight()*0.2), (int) (settings.getScreenSize().getHeight()*0.2)
        );
        pauseButton.setPreferredSize(pauseButton.getSize());
        pauseIcon.resizeIcon(
                (int) (settings.getScreenSize().getHeight()*0.2), (int) (settings.getScreenSize().getHeight()*0.2)
        );

        playButton.setIcon(playIcon);
        playButton.setBackground(new Color(32, 32, 32));
        playButton.setBorder(null);
        playButton.setSize(
                (int) (settings.getScreenSize().getHeight()*0.2), (int) (settings.getScreenSize().getHeight()*0.2)
        );
        playButton.setPreferredSize(playButton.getSize());
        playIcon.resizeIcon(
                (int) (settings.getScreenSize().getHeight()*0.2), (int) (settings.getScreenSize().getHeight()*0.2)
        );

        savebutton.setIcon(saveIcon);
        savebutton.setBackground(new Color(32, 32, 32));
        savebutton.setBorder(null);
        savebutton.setSize(
                (int) (settings.getScreenSize().getHeight()*0.2), (int) (settings.getScreenSize().getHeight()*0.2)
        );
        savebutton.setPreferredSize(savebutton.getSize());
        saveIcon.resizeIcon(
                (int) (settings.getScreenSize().getHeight()*0.2), (int) (settings.getScreenSize().getHeight()*0.2)
        );

        restartButton.setIcon(restartIcon);
        restartButton.setBackground(new Color(32, 32, 32));
        restartButton.setBorder(null);
        restartButton.setSize(
                (int) (settings.getScreenSize().getHeight()*0.2), (int) (settings.getScreenSize().getHeight()*0.2)
        );
        restartButton.setPreferredSize(restartButton.getSize());
        restartIcon.resizeIcon(
                (int) (settings.getScreenSize().getHeight()*0.2), (int) (settings.getScreenSize().getHeight()*0.2)
        );
    }

    public void pause() {
        recording = false;
        audio.pause();
    }

    public void play() {
        recording = true;
        audio.play();
    }

    public void restart() {
        audio.pause();
        tiles = new ArrayList<>();
        tiles.add(new ArrayList<>());
        tiles.add(new ArrayList<>());
        tiles.add(new ArrayList<>());
        tiles.add(new ArrayList<>());
        audio.restart();
        audio.play();
    }

    public void save() {

        sendMessage("Saved.");
    }

    public void sendMessage(String message) {
        settings.log(message);
        JOptionPane.showMessageDialog(this, message);
    }

    public String getStringInput(String message) {
        String input = JOptionPane.showInputDialog(contentPanel, message);
        if (input == null) {
            closeFrame();
            return "";
        }
        return input;
    }

    public int getIntInput(String message) {
        Integer input = null;
        String rawInput = getStringInput(message);
        try {
            input = Integer.parseInt(rawInput);
        } catch (NumberFormatException e) {
            input = getIntInput(message);
        }
        return input;
    }

    public double getDoubleInput(String message) {
        Double input = null;
        String rawInput = getStringInput(message);
        try {
            input = Double.parseDouble(rawInput);
        } catch(NumberFormatException e) {
            input = getDoubleInput(message);
        }
        return input;
    }

    public String getPath(String message) {
        JFileChooser fileChooser = getFileChooser("File Chooser", message);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(".mp3, .wav (Audio)", "mp3", "wav"));

        String filePath = ""; // Variable used to store the file path

        // Repeatedly prompt a file path until the received path possesses the suffix ".mp4"
        while (!filePath.endsWith(".mp3") && !filePath.endsWith(".wav")) {
            int returnVal = fileChooser.showOpenDialog(contentPanel); // Prompt file chooser

            if (returnVal == JFileChooser.APPROVE_OPTION) { // If user clicked on the choose button
                File file = fileChooser.getSelectedFile(); // Get the selected file
                filePath = file.getPath(); // Set filePath to the selected path for the loop condition
            } else { // User pressed on another button (ex: the close or cancel button)
                closeFrame();
            }
        }

        return filePath;
    }

    private JFileChooser getFileChooser(String title, String message) {
        JFileChooser fileChooser = new JFileChooser(); // Create a new File Chooser
        fileChooser.setName(title);
        fileChooser.setDialogTitle(message);
        fileChooser.setCurrentDirectory(new File("Resources")); // Set the current directory to the project directory
        fileChooser.setToolTipText("Select a file");
        fileChooser.setApproveButtonText("Choose");
        fileChooser.setApproveButtonToolTipText(message);
        fileChooser.setAcceptAllFileFilterUsed(false);
        return fileChooser;
    }

    public static void main(String[] args) {
        new JFXPanel();
        System.out.println("Launching map builder...");
        new MapBuilder();
    }

    public static void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println("Error running the delay block: " + e.getCause());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (recording) {
            if (e.getKeyCode() == settings.getKeybind(0)) {
                tiles.getFirst().add(audio.getCurrentPosition());
                System.out.println("Logged 0: " + audio.getCurrentPosition());
                return;
            } else if (e.getKeyCode() == settings.getKeybind(1)) {
                tiles.get(1).add(audio.getCurrentPosition());
                System.out.println("Logged 1: " + audio.getCurrentPosition());
                return;
            } else if (e.getKeyCode() == settings.getKeybind(2)) {
                tiles.get(2).add(audio.getCurrentPosition());
                System.out.println("Logged 2: " + audio.getCurrentPosition());
                return;
            } else if (e.getKeyCode() == settings.getKeybind(3)) {
                tiles.get(3).add(audio.getCurrentPosition());
                System.out.println("Logged 3: " + audio.getCurrentPosition());
                return;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            settings.log("User quit.");
            closeFrame();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (recording) {
                pause();
            } else {
                play();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            settings.log("User saved and quit.");
            save();
            closeFrame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(exitButton)) {
            pause();
            closeFrame();
        } else if (source.equals(pauseButton)) {
            pause();
        } else if (source.equals(playButton)) {
            play();
        } else if (source.equals(savebutton)) {
            pause();
            save();
        } else if (source.equals(restartButton)) {
            restart();
        } else {
            sendMessage("Invalid action was performed.");
            closeFrame();
        }
    }
}
