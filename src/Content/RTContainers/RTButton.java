package Content.RTContainers;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class RTButton extends JButton {
    private float alpha;

    public RTButton() {
        this("RTButton");
    }

    public RTButton(String name) {
        this(name, "");
    }

    public RTButton(String name, String text) {
        setName(name); // Set name
        setText(text); // Set text
        setFocusable(false); // Remove focus
        setLayout(null);
        setBackground(new Color(30, 47, 64)); // Set background
        setForeground(new Color(255, 255, 255)); // Set font color

        alpha = 1;
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
