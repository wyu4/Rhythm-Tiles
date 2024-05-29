package Content.RTComponents;

import javax.swing.*;
import java.awt.*;

/**
 * A subclass of JPanel that has support for extra features such as alpha keying, and double properties.
 */
public class RTPanel extends JPanel {
    private float alpha;
    private double accurateX, accurateY, accurateWidth, accurateHeight;

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
        this(name, 1);
    }

    /**
     * Create a new Rhythm-Tiles panel object (is-a JPanel)
     * @param name The name of the RTPanel (JPanel)
     * @param alpha The alpha keying of the panel
     */
    public RTPanel(String name, float alpha) {
        setBackground(new Color(0, 0, 0)); // Set the background color to black
        setBorder(null); // Delete borders
        setLayout(null); // Remove layout manager
        setFocusable(false); // Remove focusable
        setName(name); // Set name to the corresponding parameter
        setAlpha(alpha);

        accurateX = getX();
        accurateY = getY();
        accurateWidth = getWidth();
        accurateHeight = getHeight();
    }

    public double getAccurateX() {
        return accurateX;
    }

    public double getAccurateY() {
        return accurateY;
    }

    public double getAccurateWidth() {
        return accurateWidth;
    }

    public double getAccurateHeight() {
        return accurateHeight;
    }

    /**
     * Moves this component to a new location. The top-left corner of
     * the new location is specified by the {@code x} and {@code y}
     * parameters in the coordinate space of this component's parent.
     * <p>
     * This method changes layout-related information, and therefore,
     * invalidates the component hierarchy.
     *
     * @param x the <i>x</i>-coordinate of the new location's
     *          top-left corner in the parent's coordinate space
     * @param y the <i>y</i>-coordinate of the new location's
     *          top-left corner in the parent's coordinate space
     * @see #getLocation
     * @see #setBounds
     * @see #invalidate
     * @since 1.1
     * @apiNote Support for double values is exclusive to this project.
     */
    public void setLocation(double x, double y) {
        accurateX = x;
        accurateY = y;
        super.setLocation((int) Math.round(x), (int) Math.round(y));
    }

    @Override
    public void setLocation(Point p) {
        setLocation(p.x, p.y);
    }

    @Override
    public void setLocation(int x, int y) {
        accurateX = x;
        accurateY = y;
        super.setLocation(x, y);
    }

    /**
     * Resizes this component so that it has width {@code width}
     * and height {@code height}.
     * <p>
     * This method changes layout-related information, and therefore,
     * invalidates the component hierarchy.
     *
     * @param width the new width of this component in pixels
     * @param height the new height of this component in pixels
     * @see #getSize
     * @see #setBounds
     * @see #invalidate
     * @apiNote Support for float values is exclusive to this project.
     */
    public void setSize(double width, double height) {
        accurateWidth = width;
        accurateHeight = height;
        super.setSize((int) Math.round(width), (int) Math.round(height));
    }

    @Override
    public void setSize(Dimension d) {
        setSize(d.width, d.height);
    }

    @Override
    public void setSize(int width, int height) {
        accurateWidth = width;
        accurateHeight = height;
        super.setSize(width, height);
    }

    public void setBounds(double x, double y, double width, double height) {
        accurateX = x;
        accurateY = y;
        accurateWidth = width;
        accurateHeight = height;
        super.setBounds((int) Math.round(x), (int) Math.round(y), (int) Math.round(width), (int) Math.round(height));
    }

    @Override
    public void setBounds(Rectangle r) {
        setBounds(r.x, r.y, r.width, r.height);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        accurateX = x;
        accurateY = y;
        accurateWidth = width;
        accurateHeight = height;
        super.setBounds(x, y, width, height);
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
     * Calculate the x value of the center of the panel
     * @return X value of the center
     */
    public double getAccurateCenterX() {
        return (accurateX + (accurateWidth/2.0));
    }

    /**
     * Calculate the y value of the center of the panel
     * @return Y value of the center
     */
    public double getAccurateCenterY() {
        return (accurateY + (accurateHeight/2.0));
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
        super.setOpaque(alpha>0);

        this.alpha = alpha;
        repaint();
    }

    public boolean hasComponent(Component c) {
        for (Component addedComp : getComponents()) {
            if (addedComp.equals(c)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setOpaque(boolean isOpaque) {
        if (isOpaque) {
            alpha = 0;
        }
        super.setOpaque(isOpaque);
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