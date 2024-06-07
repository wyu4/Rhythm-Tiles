package Content;

import Content.RTComponents.RTAudio;
import Content.RTComponents.RTPanel;

import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

public class MapPlayer extends RTPanel implements ActionListener {
    private final Settings settings;

    private final GridLayout mainLayout;

    private final TilePanel[] tilePanels;
    private final boolean botAssist;
    private double timeToGoal;
    private final RankCalculator rankCalculator;
    private final Timer timer, testTimer;
    private RTAudio audio;

    private Long lastFrameTime;

    private Map currentMap;
    private List<List<Double>> tilesDown;

    private boolean playing;

    public MapPlayer(Settings settings) {
        this(settings, false);
    }

    public MapPlayer(Settings settings, boolean botAssist) {
        this.settings = settings;
        this.botAssist = botAssist;
        timeToGoal = 1;
        playing = false;

        rankCalculator = new RankCalculator();

        mainLayout = new GridLayout(1, 4);

        tilePanels = new TilePanel[] {
                new TilePanel(settings, 0, timeToGoal),
                new TilePanel(settings, 1, timeToGoal),
                new TilePanel(settings, 2, timeToGoal),
                new TilePanel(settings, 3, timeToGoal)
        };

        timer = new Timer((int) settings.calculateDesiredDelta(), this);
        testTimer = new Timer(250, this);

        refresh();

        for (TilePanel p : tilePanels) {
            add(p);
            p.refresh(calculateTilePanelSize());
        }

        timer.start();
//        testTimer.start(); // Disabled by default
    }

    public void setCurrentMap(Map newMap) {
        if (currentMap == null || !this.currentMap.equals(newMap)) { // Make sure newMap isn't equal to the current map
            currentMap = newMap;
            loadMap();
        } else {
            settings.error("MapPlayer attempted to play the same map. Request was ignored.");
        }
    }

    private void loadMap() {
        if (currentMap != null) {
            playing = false;

            timeToGoal = currentMap.getSpawnToGoal();
            tilesDown = currentMap.getTilesDown();
            audio = new RTAudio(currentMap.getAudioPath());
            audio.setOffset(timeToGoal * 1000);

            playing = true;
            audio.play();
        } else {
            settings.error("Map is null... Cannot load.");
        }
    }

    /**
     * Check if there are any tiles in bounds.
     * @return {@code true} if there are none in bounds, {@code false} if not.
     */
    private boolean isEmpty() {
        for (TilePanel t : tilePanels) {
            if (!t.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void refresh() {
        refresh(getSize());
    }

    public void refresh(Dimension size) {
        timer.setDelay((int) settings.calculateDesiredDelta());

        setAlpha(0.6f);
        setVisible(true);
        setBackground(new Color(0, 0, 0));
        setPreferredSize(size);
        setLayout(mainLayout);

        for (TilePanel p : tilePanels) {
            p.refresh(calculateTilePanelSize());
        }

        revalidate();
        repaint();
    }

    public Dimension calculateTilePanelSize() {
        return new Dimension(
                getWidth()/mainLayout.getColumns(),
                getHeight()/mainLayout.getRows()
        );
    }

    public void setAudio(RTAudio audio) {
        this.audio = audio;
    }

    public void setAudio(String path) {
        setAudio(new RTAudio(path));
    }

    public RTAudio getAudio() {
        return audio;
    }

    public void handleKeyEvent(KeyEvent e) {
        for (int i = 0; i < tilePanels.length; i++) {
            if (e.getKeyCode() == settings.getKeybind(i)) {
                tilePanels[i].handleInput(rankCalculator);
                if (!botAssist) {
                    rankCalculator.reset();
                }
            }
        }
    }

    public void handleSpawn(int index) {
        if (playing && tilesDown != null && audio != null) {
            List<Double> tiles = tilesDown.get(index);

            if (tiles.isEmpty()) {
                return;
            }

            if (tiles.getFirst() <= (audio.getCurrentPosition())) {
                tiles.removeFirst();
                tilePanels[index].spawnTile();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(timer)) {
            if (lastFrameTime == null) {
                lastFrameTime = settings.getTimeElapsedMillis();
                return;
            }
            long actualDelta = settings.getTimeElapsedMillis() - lastFrameTime;
            double deltaRate = actualDelta/settings.calculateDesiredDelta();

            // Update the tiles
            for (int i = 0; i < tilePanels.length; i++) {
                TilePanel t = tilePanels[i];
                t.update(deltaRate, rankCalculator);
                handleSpawn(i);
            }

            lastFrameTime = settings.getTimeElapsedMillis();

            settings.repaintWindow();
        } else if (e.getSource().equals(testTimer)) { // Testing tile spawns
            int randomIndex = (int) (Math.random() * 4);
            tilePanels[randomIndex].spawnTile();
        }
    }
}