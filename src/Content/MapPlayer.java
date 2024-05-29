package Content;

import Content.RTComponents.RTAudio;
import Content.RTComponents.RTPanel;

import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class MapPlayer extends RTPanel implements ActionListener {
    private final Settings settings;

    private final GridLayout mainLayout;

    private final TilePanel[] tilePanels;
    private final boolean botAssist;
    private final double timeToGoal;
    private final RankCalculator rankCalculator;
    private final Timer timer;
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

        init();

        for (TilePanel p : tilePanels) {
            add(p);
        }

        timer.start();
    }

    public void init() {
        init(getSize());
    }

    public void init(Dimension size) {
        timer.setDelay((int) settings.calculateDesiredDelta());

        setAlpha(0.6f);
        setVisible(true);
        setBackground(new Color(0, 0, 0));
        setPreferredSize(size);
        setLayout(mainLayout);

        for (TilePanel p : tilePanels) {
            p.init(new Dimension(
                    getWidth()/mainLayout.getColumns(),
                    getHeight()/mainLayout.getRows()
            ));
        }

        revalidate();
        repaint();
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
            double deltaRate = settings.calculateDesiredDelta() / actualDelta;
            System.out.println("Desired delta: " + settings.calculateDesiredDelta() + ", Actual delta: " + actualDelta + ", Rate: " + deltaRate);

            for (TilePanel tilePanel : tilePanels) {
                if (((int) settings.getTimeElapsedSec()) % 4 == 0) {
                    tilePanel.spawnTile();
                }
                tilePanel.update(deltaRate, rankCalculator);
            }

            lastFrameTime = settings.getTimeElapsedMillis();
        }
    }
}