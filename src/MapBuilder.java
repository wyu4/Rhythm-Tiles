import Content.Map;
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
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapBuilder extends RTFrame implements KeyListener, ActionListener {
    private final String MAPS_DIR = "Resources\\Maps";

    private final Settings settings;
    private final RTPanel contentPanel, bottomPanel;
    private final RTImageIcon exitIcon, pauseIcon, playIcon, saveIcon, restartIcon;
    private final RTButton exitButton, pauseButton, playButton, saveButton, restartButton;
    private final BorderLayout frameLayout;
    private final GridLayout contentLayout;
    private final GridBagLayout bottomLayout;
    private final Map map;

    private List<String> keysPressed;
    private final RTAudio audio;

    private boolean recording; // Flag holding whether the program is recording inputs.

    /**
     * Create a new map builder
     */
    public MapBuilder() {
        // Create a new map
        map = new Map();

        // Instantiate a new list
        keysPressed = new ArrayList<>();

        recording = false; // Set recording state to false

        // Create new image icons (using subclass)
        exitIcon = new RTImageIcon(Resources.Images.EXIT);
        pauseIcon = new RTImageIcon(Resources.Images.PAUSE);
        playIcon = new RTImageIcon(Resources.Images.PLAY);
        saveIcon = new RTImageIcon(Resources.Images.SAVE);
        restartIcon = new RTImageIcon(Resources.Images.RESTART);

        // Instantiate new settings
        settings = new Settings(120);

        // Instantiate new JComponents (using subclasses)
        contentPanel = new RTPanel("ContentPanel");
        bottomPanel = new RTPanel("BottomPanel");
        exitButton = new RTButton("Exit");
        pauseButton = new RTButton("Pause");
        playButton = new RTButton("Play");
        saveButton = new RTButton("Save");
        restartButton = new RTButton("Restart");

        // Instantiate new layout managers
        frameLayout = new BorderLayout(); // Border layout for the frame
        contentLayout = new GridLayout(1, 3); // Grid layout for the content panel
        bottomLayout = new GridBagLayout(); // Grid bag layout for the bottom panel

        refresh(); // Call the refresh method to apply the component properties

        // Instatiate and configuring new constraints
        GridBagConstraints bottomConstraints = new GridBagConstraints(); // Grid bag constraint for the bottom panel
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

        // Adding components //
        // Buttons into bottom panel using grid bag constraints
        bottomPanel.add(exitButton, bottomConstraints);

        bottomConstraints.gridx = 1;
        bottomPanel.add(pauseButton, bottomConstraints);

        bottomConstraints.gridx = 2;
        bottomPanel.add(playButton, bottomConstraints);

        bottomConstraints.gridx = 3;
        bottomPanel.add(saveButton, bottomConstraints);

        bottomConstraints.gridx = 4;
        bottomPanel.add(restartButton, bottomConstraints);

        // Action listeners into buttons
        exitButton.addActionListener(this);
        pauseButton.addActionListener(this);
        playButton.addActionListener(this);
        saveButton.addActionListener(this);
        restartButton.addActionListener(this);

        // Panels into frame
        add(bottomPanel, BorderLayout.SOUTH);
        add(contentPanel, BorderLayout.CENTER);

        // Key listeners into frame
        addKeyListener(this);

        // Revalidate + repaint to fix any rendering errors
        revalidate();
        repaint();

        // Getting map properties
        map.setName(getStringInput("Choose a name"));
        map.setDescription(getStringInput("Write a description"));
        map.setAudioPath(getRelativePath("Choose audio"));
        map.setSpawnToGoal(getDoubleInput("Input the time (seconds) that each tile takes to travel from the spawn to the goal."));
        double rate = getDoubleInput("Input the playback rate (only applied in the editor).");

        audio = new RTAudio(map.getAudioPath());
        audio.setRate(rate);

        restart();
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

        saveButton.setIcon(saveIcon);
        saveButton.setBackground(new Color(32, 32, 32));
        saveButton.setBorder(null);
        saveButton.setSize(
                (int) (settings.getScreenSize().getHeight()*0.2), (int) (settings.getScreenSize().getHeight()*0.2)
        );
        saveButton.setPreferredSize(saveButton.getSize());
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

    /**
     * Pause the audio and stop recording
     */
    public void pause() {
        recording = false;
        audio.pause();
    }

    /**
     * Start the audio and record
     */
    public void play() {
        recording = true;
        audio.play();
    }

    /**
     * Re-instantiate the tiles list and restart the audio.
     */
    public void restart() {
        pause();
        audio.restart();
        map.resetTiles();
        play();
    }

    /**
     * Export the project properties and tiles to "maps" located inside the Resources directory.
     */
    public void save() {
        String jsonData = map.exportJSON();

        File mapsDir = new File(MAPS_DIR);
        File dataFile = new File(MAPS_DIR + "\\" + map.getName() + ".json");

        if (!mapsDir.exists()) {
            boolean created = mapsDir.mkdir();
            if (!created) {
                sendMessage("Error: could not create Maps directory. Please try again.");
                return;
            }
        }
        if (!dataFile.exists()) {
            boolean created = false;
            try {
                created = dataFile.createNewFile();
            } catch (IOException e) {
                sendMessage("Error: could not create Maps data file. Please try again.");
                return;
            }
        }

        try {
            FileWriter dataFileWriter = new FileWriter(dataFile, false);
            dataFileWriter.write(jsonData);
            dataFileWriter.close();
        } catch (IOException e) {
            sendMessage("Error: could not create or use file writer: " + e.getMessage());
            return;
        }

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

    public String getRelativePath(String message) {
        RTFileChooser fileChooser = new RTFileChooser("File Chooser", message);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(".mp3, .wav (Audio)", "mp3", "wav"));

        String filePath = ""; // Variable used to store the file path

        // Repeatedly prompt a file path until the received path possesses the suffix ".mp4"
        while (!filePath.endsWith(".mp3") && !filePath.endsWith(".wav")) {
            int returnVal = fileChooser.showOpenDialog(contentPanel); // Prompt file chooser

            if (returnVal == JFileChooser.APPROVE_OPTION) { // If user clicked on the choose button
                filePath = fileChooser.getSelectedRelativeFile(); // Set filePath to the selected path for the loop condition
                System.out.println(filePath);
            } else { // User pressed on another button (ex: the close or cancel button)
                closeFrame();
            }
        }

        return filePath;
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
        int key = e.getKeyCode();
        if (recording && !keysPressed.contains(String.valueOf(key))) {
            keysPressed.add(String.valueOf(key));
            double timestamp = audio.getCurrentPosition();
            if (key == settings.getKeybind(0)) {
                map.addTileDown(0, timestamp);
                System.out.println("Logged down 0: " + timestamp);
                return;
            } else if (key == settings.getKeybind(1)) {
                map.addTileDown(1, timestamp);
                System.out.println("Logged down 1: " + timestamp);
                return;
            } else if (key == settings.getKeybind(2)) {
                map.addTileDown(2, timestamp);
                System.out.println("Logged down 2: " + timestamp);
                return;
            } else if (key == settings.getKeybind(3)) {
                map.addTileDown(3, timestamp);
                System.out.println("Logged down 3: " + timestamp);
                return;
            }
        }
        if (key == KeyEvent.VK_ESCAPE) {
            settings.log("User quit.");
            closeFrame();
        } else if (key == KeyEvent.VK_SPACE) {
            if (recording) {
                pause();
            } else {
                play();
            }
        } else if (key == KeyEvent.VK_ENTER) {
            settings.log("User saved and quit.");
            save();
            closeFrame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        keysPressed.remove(String.valueOf(key));
        if (recording) {
            double timestamp = audio.getCurrentPosition();
            if (key == settings.getKeybind(0)) {
                map.addTileUp(0, timestamp);
                System.out.println("Logged up 0: " + timestamp);
                return;
            } else if (key == settings.getKeybind(1)) {
                map.addTileUp(1, timestamp);
                System.out.println("Logged up 1: " + timestamp);
                return;
            } else if (key == settings.getKeybind(2)) {
                map.addTileUp(2, timestamp);
                System.out.println("Logged up 2: " + timestamp);
                return;
            } else if (key == settings.getKeybind(3)) {
                map.addTileUp(3, timestamp);
                System.out.println("Logged up 3: " + timestamp);
                return;
            }
        }
    }

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
        } else if (source.equals(saveButton)) {
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
