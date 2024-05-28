package Content.RTComponents.Layers.Templates;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;

public class AlphaGradientLayer extends LayerUI<JComponent> {
    private Color gradientColor1, gradientColor2;
    private float alpha;

    /**
     * Create a new RTLayer (subclass of LayerUI)
     */
    public AlphaGradientLayer() {
        gradientColor1 = Color.BLACK;
        gradientColor2 = gradientColor1;

        alpha = 1f;
    }

    public Color getGradientColor1() {
        return gradientColor1;
    }

    public Color getGradientColor2() {
        return gradientColor2;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setGradientColor1(Color color) {
        gradientColor1 = color;
    }

    public void setGradientColor2(Color color) {
        gradientColor2 = color;
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

        // Set gradient of g2d
        g2d.setPaint(new GradientPaint(
                0,
                0,
                gradientColor1,
                0,
                c.getHeight(),
                gradientColor2
                )
        );

        g2d.fillRect(0, 0, c.getWidth(), c.getHeight());
        g2d.dispose();
    }

    @Override
    public String toString() {
        return "AlphaGradientLayer{" +
                "alpha" + alpha + "," +
                "gradientColor1" + gradientColor1 + "," +
                "gradientColor2" + gradientColor2 +
                "}";
    }
}
