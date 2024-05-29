package Content;

import Content.RTComponents.RTPanel;

import java.awt.Color;

public class Goal extends RTPanel {
    public Goal() {
        this("RTGoal");
    }

    public Goal(String name) {
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
