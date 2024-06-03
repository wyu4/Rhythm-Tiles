package Content;

import Content.RTComponents.RTAudio;
import Content.RTComponents.RTPanel;

import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MapPlayer extends RTPanel implements ActionListener {
    private final Settings settings;

    private final GridLayout mainLayout;

    private final TilePanel[] tilePanels;
    private final boolean botAssist;
    private final double timeToGoal;
    private final RankCalculator rankCalculator;
    private final Timer timer, testTimer;
    private RTAudio audio;

    private Long lastFrameTime;

    public MapPlayer(Settings settings) {
        this(settings, false);
    }

    public MapPlayer(Settings settings, boolean botAssist) {
        this.settings = settings;
        this.botAssist = botAssist;
        timeToGoal = 1;

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
        testTimer.start();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(timer)) {
            if (lastFrameTime == null) {
                lastFrameTime = settings.getTimeElapsedMillis();
                return;
            }
            long actualDelta = settings.getTimeElapsedMillis() - lastFrameTime;
            double deltaRate = actualDelta/settings.calculateDesiredDelta();

//            System.out.println("Delta: " + settings.calculateDesiredDelta() + ", Actual delta: " + actualDelta + ", Rate: " + deltaRate);
            for (TilePanel tilePanel : tilePanels) {
                tilePanel.update(deltaRate, rankCalculator);
            }

            lastFrameTime = settings.getTimeElapsedMillis();

            settings.repaintWindow();
        } else if (e.getSource().equals(testTimer)) {
            int randomIndex = (int) (Math.random() * 4);
            tilePanels[randomIndex].spawnTile();
        }
    }
}