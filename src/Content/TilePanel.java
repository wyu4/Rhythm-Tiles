package Content;

import Content.RTComponents.RTPanel;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TilePanel extends RTPanel implements ActionListener {
    private final Settings settings;
    private final int index;
    private double timeToGoal;
    private final RTPanel contentPanel, highlightPanel;
    private final List<Tile> tiles;
    private final Tile spawnpoint;
    private final Goal goal;
    private final RankCalculator calculator;
    private final boolean autoAssist;
    private final File triggerSfxFile;

    private final Timer animTimer;

    public TilePanel(Settings settings, int index, RankCalculator calculator, boolean autoAssist) {
        this.settings = settings;
        this.index = index;
        this.timeToGoal = 1;
        this.calculator = calculator;
        this.autoAssist = autoAssist;

        tiles = new ArrayList<>();
        goal = new Goal();
        spawnpoint = new Tile(goal);
        contentPanel = new RTPanel();
        highlightPanel = new RTPanel("", 0);
        animTimer = new Timer((int) settings.calculateDesiredDelta(), this);

        contentPanel.add(goal);
        add(contentPanel);
        add(highlightPanel);

        triggerSfxFile = new File("Resources\\Click.wav");


        repaint();
        revalidate();
        animTimer.start();
    }

    public void refresh(Dimension size) {
        setLayout(null);
        setName("TilePanel" + index);
        setBackground(new Color(32, 32, 35));
        setLocation(0, 0);
        setSize(size.width, size.height);
        if (index % 2 == 0) {
            setAlpha(0.5f);
        } else {
            setAlpha(0);
        }

        contentPanel.setName("ContentPanel");
        contentPanel.setLocation(0, 0);
        contentPanel.setBackground(new Color(0, 0, 0));
        contentPanel.setAlpha(0);
        contentPanel.setLayout(null);
        contentPanel.setSize(size.width, size.height*2);

        highlightPanel.setName("HighlightPanel");
        highlightPanel.setLocation(0, 0);
        highlightPanel.setBackground(new Color(255, 255, 255));
        // highlightPanel.setAlpha(0);
        highlightPanel.setLayout(null);
        highlightPanel.setSize(size.width*2, size.height);
        highlightPanel.setCenterLocation(getCenterLocation().x, highlightPanel.getCenterLocation().y);

        goal.setSize(size.width*2, size.width);
        goal.setCenterLocation(getCenterLocation().x, (int)(size.height - (goal.getHeight()*1.25)));

        spawnpoint.setAlpha(0);
        spawnpoint.setSize(goal.getSize());
        spawnpoint.setLocation(0f, -spawnpoint.getHeight()*1.25f);

        revalidate();
        repaint();
    }

    public int getKeybind() {
        return settings.getKeybind(index);
    }

    public int getIndex() {
        return index;
    }

    public double getTimeToGoal() {
        return timeToGoal;
    }

    public void setTimeToGoal(double timeToGoal) {
        this.timeToGoal = timeToGoal;
    }

    public Goal getGoal() {
        return goal;
    }

    /**
     * Check if there are any tiles in bounds.
     * @return {@code true} if there are none in bounds, {@code false} if not.
     */
    public boolean isEmpty() {
        for (Tile t : tiles) {
            if (!t.getTriggered()) {
                return false;
            }
        }
        return true;
    }

    public void close() {
        animTimer.stop();
    }

    public void spawnTile() {
        Tile tile = new Tile(goal, 1);
        tile.setBounds(spawnpoint.getBounds());
        tiles.add(tile);
        contentPanel.add(tile);
    }

    public void update(double deltaRate) {
        for (Tile tile : tiles) {
            if (tiles.contains(tile)) { // Check if tile still exists inside the list
                updateTile(tile, deltaRate);
            }
        }
        refresh(getSize());
    }

    /**
     * Update the properties of a tile.
     * @param tile The tile to update
     */
    private void updateTile(Tile tile, double deltaRate) {
        // Quit out of the method if the tile has since been removed from the array list
        if (!tiles.contains(tile)) {
            return;
        }

        // Trigger the tile to be inputted if the tile is out of reach
        if (tile.isOutOfPotentialReach()) {
            inputTile(tile);
        }

        // Remove the tile if it moves out of the content panel
        if (tile.getAccurateY() > contentPanel.getAccurateHeight()) {
            if (contentPanel.hasComponent(tile)) {
                contentPanel.remove(tile);
            }
            return;
        }

        handleAutoAssist(tile);

        // Move the tile
        tile.setLocation(tile.getAccurateX(), tile.getAccurateY() + calculateTileIncrement(deltaRate));
    }

    //
    public double calculateTileIncrement(double deltaRate) {
        double spawnGoalDist = Math.abs(spawnpoint.getAccurateY() - goal.getAccurateY());
        double incrPerMillis = spawnGoalDist/(timeToGoal*1000);
        double desiredIncrPerFrame = incrPerMillis * settings.calculateDesiredDelta();

        return desiredIncrPerFrame * deltaRate;
    }

    /**
     * Handle a user input
     */
    public void handleInput() {
        highlightPanel.setAlpha(0.25f);
        for (Tile tile : tiles) {
            inputTile(tile);
        }
    }

    private void handleAutoAssist(Tile tile) {
        if (!autoAssist) {
            return;
        }

        if (tile.getAccurateY() >= goal.getAccurateY() && !tile.getTriggered()) {
            inputTile(tile);
            highlightPanel.setAlpha(0.25f);
        }
    }


    private void inputTile(Tile tile) {
        Tile.Rank rank = tile.calculateRank();
        if (!tile.getTriggered()) {
            if (tile.isInReach()) {
                tile.setTriggered(true);
                tile.setAlpha(switch (rank) {
                    case PERFECT, GREAT -> 0f;
                    case GOOD, BAD -> 0.25f;
                    case MISS -> 1f;
                });
                if (!autoAssist) {
                    calculator.addPoints(rank);
                    System.out.println(rank);
                } else {
                    System.out.println(rank + " (bot assisted)");
                }

                if (triggerSfxFile.exists()) {
                    try {
                        AudioInputStream stream = AudioSystem.getAudioInputStream(triggerSfxFile.toURI().toURL());

                        Clip triggerSfx = AudioSystem.getClip();
                        triggerSfx.open(stream);
                        triggerSfx.start();

                    } catch (Exception e) {
                        settings.error("An error occured while playing the Trigger SFX file: " + e.getMessage());
                    }
                } else {
                    settings.error("Trigger SFX file is missing.");
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(animTimer)) {
            float incr = (float) (settings.calculateDesiredDelta()/1000);
            if (highlightPanel.getAlpha() > 0) {
                highlightPanel.setAlpha(highlightPanel.getAlpha() - incr);
            }
        }
    }
}
