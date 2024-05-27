package Content.RTContainers;

import javax.swing.*;
import java.awt.*;

public class RTPanel extends JPanel {
    private float alpha;

    /**
     * Create a new Rhythm-Tiles panel object (is-a JPanel)
     */
    public RTPanel() {
        this("RTPanel");
    }
    
    /**
     * Create a new Rhythm-Tiles panel object (is-a JPanel)
     * @param name The name of the RTPanel (JPanel)
     */
    public RTPanel(String name) {
        setBackground(new Color(0, 0, 0)); // Set the background color to black
        setBorder(null); // Delete borders
        setLayout(null); // Remove layout manager
        setFocusable(false); // Remove focusable
        setName(name); // Set name to the corresponding parameter
        alpha = 1;
    }

    /**
     * Calculate the location of the center of the panel
     * @return Location of the center
     */
    public Point getCenterLocation() {
        return new Point(
                (int)(getX() + (getSize().getWidth()/2)),
                (int)(getY() + (getSize().getHeight()/2))
        );
    }

    /**
     * Set the location of the center of the panel
     * @param x The X value of the center
     * @param y The Y value of the center
     */
    public void setCenterLocation(int x, int y) {
        setCenterLocation(new Point(x, y));
    }

    /**
     * Set the location of the center of the panel
     * @param p The location of the center
     */
    public void setCenterLocation(Point p) {
        setLocation(new Point(
                        (int) Math.round(p.getX() - (getSize().getWidth()/2)),
                        (int) Math.round(p.getY() - (getSize().getHeight()/2))
                )
        );
    }

    /**
     * Get the current set alpha.
     * @return The current alpha
     */
    public float getAlpha() {
        return alpha;
    }

    /**
     * Set the new alpha
     * @param alpha The new alpha
     */
    public void setAlpha(float alpha) {
        this.alpha = alpha;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2d.setComposite(AlphaComposite.getInstance( // Apply alpha
                AlphaComposite.SRC_OVER,
                alpha
        ));

        super.paint(g);
    }

    @Override
    public String toString() {
        return getClass().getName() + "{alpha:" + alpha + ", " + super.toString() + "}";
    }
}