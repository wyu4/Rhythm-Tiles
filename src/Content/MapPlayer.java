package Content;

import Content.RTComponents.RTAudio;
import Content.RTComponents.RTGoal;
import Content.RTComponents.RTPanel;
import Content.RTComponents.RTTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MapPlayer extends RTPanel {
    private final Settings settings;

    private final GridLayout mainLayout;

    private final RTPanel[] contentPanels;
    private final RTPanel[] tilePanels;
    private final RTGoal[] goals;
    private final List<RTTile> tiles;
    private final boolean botAssist;
    private RTAudio audio;

    public MapPlayer(Settings settings) {
        this(settings, false);
    }

    public MapPlayer(Settings settings, boolean botAssist) {
        this.settings = settings;
        this.botAssist = botAssist;

        mainLayout = new GridLayout(1, 4);

        contentPanels = new RTPanel[] {
                new RTPanel(getClass().getName() + "ContentPanel0"),
                new RTPanel(getClass().getName() + "ContentPanel1"),
                new RTPanel(getClass().getName() + "ContentPanel2"),
                new RTPanel(getClass().getName() + "ContentPanel3")
        };
        tilePanels = new RTPanel[] {
                new RTPanel(getClass().getName() + "TilePanel0"),
                new RTPanel(getClass().getName() + "TilePanel1"),
                new RTPanel(getClass().getName() + "TilePanel2"),
                new RTPanel(getClass().getName() + "TilePanel3")
        };
        goals = new RTGoal[] {
                new RTGoal(getClass().getName() + "Goal0"),
                new RTGoal(getClass().getName() + "Goal1"),
                new RTGoal(getClass().getName() + "Goal2"),
                new RTGoal(getClass().getName() + "Goal3")
        };
        tiles = new ArrayList<RTTile>();

        init();

        for (int i = 0; i < contentPanels.length; i++) {
            RTPanel contentPanel = contentPanels[i];
            RTPanel tilePanel = tilePanels[i];
            RTPanel goal = goals[i];

            tilePanel.add(goal);
            contentPanel.add(tilePanel);
            add(contentPanel);
        }
    }

    public void init() {
        init(getSize());
    }

    public void init(Dimension size) {
        setAlpha(0.75f);
        setVisible(true);
        setBackground(new Color(0, 0, 0));
        setPreferredSize(size);
        setLayout(mainLayout);

        for (int i = 0; i < contentPanels.length; i++) {
            RTPanel contentPanel = contentPanels[i];
            RTPanel tilePanel = tilePanels[i];
            RTGoal goal = goals[i];

            contentPanel.setAlpha(0);
            contentPanel.setLayout(null);

            if (i % 2 == 0) {
                tilePanel.setAlpha(0.5f);
            } else {
                tilePanel.setAlpha(0);
            }
            tilePanel.setLocation(0, 0);
            tilePanel.setSize(contentPanel.getWidth(), contentPanel.getHeight()*2);
            tilePanel.setBackground(new Color(32, 32, 35));
            tilePanel.setLayout(null);

            goal.setSize(tilePanel.getWidth()*2, tilePanel.getWidth());;
            goal.setLocation(-tilePanel.getWidth()/4,
                    (int) (tilePanel.getHeight()/2 - (goal.getHeight()*1.5))
            );
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
}