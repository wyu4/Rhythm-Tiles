package Content;

import Content.RTComponents.RTPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class TilePanel extends RTPanel {
    private final Settings settings;
    private final int index;
    private final double timeToGoal;
    private final RTPanel contentPanel;
    private final List<Tile> tiles;
    private final List<Tile> inreachTiles;
    private final Tile spawnpoint;
    private final Goal goal;

    public TilePanel(Settings settings, int index, double timeToGoal) {
        this.settings = settings;
        this.index = index;
        this.timeToGoal = timeToGoal;

        tiles = new ArrayList<>();
        inreachTiles = new ArrayList<>();
        goal = new Goal();
        spawnpoint = new Tile(goal);
        contentPanel = new RTPanel();

        contentPanel.add(goal);
        add(contentPanel);

        repaint();
        revalidate();
    }

    public void init(Dimension size) {
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
        contentPanel.setAlpha(0);
        contentPanel.setLayout(null);
        contentPanel.setSize(size.width, size.height*2);

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

    public double timeToGoal() {
        return timeToGoal;
    }

    public Goal getGoal() {
        return goal;
    }

    public void spawnTile() {
        Tile tile = new Tile(goal, 1);
        tile.setBounds(spawnpoint.getBounds());
        tiles.add(tile);
        inreachTiles.add(tile);
        add(tile);
    }

    public void update(double deltaRate, RankCalculator calculator) {
        for (Tile tile : tiles) {
            tile.setLocation(0.0, tile.getAccurateY() + calculateTileIncrement(deltaRate));
            tile.setSize(goal.getSize());

            if (inreachTiles.contains(tile) && tile.isOutOfReach()) {
                System.out.println("Out of reach brah");
                inreachTiles.remove(tile);
                calculator.addPoints(Tile.Rank.MISS);

            } else if (tiles.contains(tile) && (tile.getAccurateY() > contentPanel.getAccurateHeight())) {
                System.out.println("Deleting...");
                tiles.remove(tile);
                contentPanel.remove(tile);
                remove(tile);
            }
        }
        contentPanel.repaint();
    }

    public double calculateTileIncrement(double deltaRate) {
        double spawnGoalDist = Math.abs(spawnpoint.getAccurateY() - goal.getAccurateY());
        double incrPerMillis = spawnGoalDist/(timeToGoal*1000);
        double desiredIncrPerFrame = incrPerMillis * settings.calculateDesiredDelta();

        return desiredIncrPerFrame * deltaRate;
    }

    public void handleInput(RankCalculator calculator) {
        for (Tile tile : inreachTiles) {
            inreachTiles.remove(tile);
            tiles.remove(tile);
            remove(tile);

        }
    }
}
