package Content;

import Content.RTComponents.RTPanel;
import Content.RTComponents.RTTile;

import java.util.ArrayList;
import java.util.List;

public class MapPlayer extends RTPanel {
    private final Settings settings;

    private final RTPanel[] contentPanels;
    private final RTPanel[] tilePanels;
    private final List<RTTile> tiles;
    private final boolean botAssist;

    public MapPlayer(Settings settings) {
        this(settings, false);
    }

    public MapPlayer(Settings settings, boolean audioFade) {
        this.settings = settings;
        this.botAssist = audioFade;

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
        tiles = new ArrayList<RTTile>();

    }


}