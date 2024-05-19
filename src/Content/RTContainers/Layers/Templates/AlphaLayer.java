package Content.RTContainers.Layers.Templates;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;

public class AlphaLayer extends LayerUI<JComponent> {
    private float alpha;

    /**
     * Create a new RTLayer (subclass of LayerUI)
     */
    public AlphaLayer() {
        alpha = 1f;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float a) {
        alpha = a;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c); // Call the original paint method
        Graphics2D g2d = (Graphics2D) g; // Parse graphics as Graphics2D object

        // Set the alpha of g2d
        g2d.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER,
                alpha
        ));

        g2d.fillRect(0, 0, c.getWidth(), c.getHeight());
        g2d.dispose();
    }

    @Override
    public String toString() {
        return "AlphaLayer{alpha:" + alpha + "}";
    }
}
