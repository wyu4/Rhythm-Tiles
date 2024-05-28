package Content.RTComponents;

import javafx.embed.swing.JFXPanel;
import javafx.scene.effect.Glow;

import java.awt.Color;

public class RTGoal extends RTPanel {
    public RTGoal() {
        this("RTGoal");
    }

    public RTGoal(String name) {
        super(name);
        init();
    }

    public void init() {
        setBackground(Color.WHITE);
        setAlpha(0.6f);

        revalidate();
        repaint();
    }
}
