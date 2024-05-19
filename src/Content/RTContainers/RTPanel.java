package Content.RTContainers;

import javax.swing.*;
import java.awt.*;

public class RTPanel extends JPanel {
    public RTPanel() {
        this("RTPanel");
    }
    
    /**
     * Create a new Rhythm-Tiles panel object (is-a JPanel)
     */
    public RTPanel(String name) {
        setBackground(new Color(0, 0, 0, 0)); // Set the background color to black
        setBorder(null); // Delete borders
        setLayout(null); // Remove layout manager
        setFocusable(false); // Remove focusable
        setVisible(true);
        setName(name); // Set name to the corresponding parameter
    }

    /**
     * Calculate the location of the center of the frame
     * @return Location of the center
     */
    public Point getCenterLocation() {
        return new Point(
                (int)(getX() + (getSize().getWidth()/2)),
                (int)(getY() + (getSize().getHeight()/2))
        );
    }

    /**
     * Set the location of the center of the frame
     * @param x The X value of the center
     * @param y The Y value of the center
     */
    public void setCenterLocation(int x, int y) {
        setCenterLocation(new Point(x, y));
    }

    /**
     * Set the location of the center of the frame
     * @param p The location of the center
     */
    public void setCenterLocation(Point p) {
        setLocation(new Point(
                        (int) Math.round(p.getX() - (getSize().getWidth()/2)),
                        (int) Math.round(p.getY() - (getSize().getHeight()/2))
                )
        );
    }
}