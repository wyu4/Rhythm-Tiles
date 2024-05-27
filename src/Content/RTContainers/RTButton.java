package Content.RTContainers;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class RTButton extends JButton {
    private float alpha;

    /**
     * Create a new RTButton object.
     */
    public RTButton() {
        this("RTButton");
    }

    /**
     * Create a new RTButton object.
     * @param name The name of the RTButton (JButton)
     */
    public RTButton(String name) {
        this(name, "");
    }

    /**
     * Create a new RTButton object.
     * @param name The name of the RTButton (JButton)
     * @param text The text displayed on the button
     */
    public RTButton(String name, String text) {
        this(name, text, 1);
    }

    /**
     * Create a new RTButton object.
     * @param name The name of the RTButton (JButton)
     * @param text The text displayed on the button
     * @param alpha The alpha keying
     */
    public RTButton(String name, String text, float alpha) {
        setName(name); // Set name
        setText(text); // Set text
        setFocusable(false); // Remove focus
        setLayout(null);
        setBackground(new Color(30, 47, 64)); // Set background
        setForeground(new Color(255, 255, 255)); // Set font color
        setAlpha(alpha);
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
}
