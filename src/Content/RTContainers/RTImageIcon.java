package Content.RTContainers;

import javax.swing.*;
import java.awt.*;

/**
 * A ImageIcon custom modified for this project (Rhythm Tiles). It's equipped with antialiasing, bilinear interpolation, and support for alpha compositing, making for an overall much smoother output.
 * Antialiasing is a way of processing images from a higher resolution to a lower resolution with minimal distortion. Works well when resizing images.
 * Bilinear interpolation is a way of minimizing visible pixels of an image by creating new pixels. These new pixels will take on the average color of its four neighboring pixels.
 * Alpha compositing allows for images with lower opacity.
 */
public class RTImageIcon extends ImageIcon {
    private float alpha; // The alpha variable

    /**
     * Create a new RTImageIcon.
     * @param path The path of the image file.
     */
    public RTImageIcon(String path) {
        this(path, 1f); // Chain constructor
    }

    /**
     * Create a new RTImageIcon.
     * @param path The path of the image file.
     * @param alpha The opacity of the final render.
     */
    public RTImageIcon(String path, float alpha) {
        super(path); // Call the super constructor (no use of re-coding the entire ImageIcon)
        this.alpha = alpha; // Set alpha to the corresponding parameter
    }

    /**
     * Resize the visible bounds of the image.
     * @param width The width of the visible image
     * @param height The height of the visible image
     */
    public void resizeIcon(int width, int height) {
        Image resized = getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH); // Create a new resized image
        setImage(resized); // Set the image to the new resized image
    }

    /**
     * Resize the visible bounds of the image.
     * @param size The size of the visible image
     */
    public void resizeIcon(Dimension size) {
        resizeIcon(size.width, size.height); // Chaining method
    }

    /**
     * Set the opacity of the final render
     * @param alpha {@code float} between 0 and 1 representing the final render's new opacity
     * @apiNote The parent component may require a repaint in order for the opacity to update.
     */
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    /**
     * Get the opacity of the final render
     * @return {@code float} between 0 to 1 representing the opacity of the final render.
     */
    public float getAlpha() {
        return alpha; // Return the value of the alpha variable
    }

    @Override
    public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
        // Making image render more smoothly
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Turning on antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); // Turning on bilinear interpolation
        g2d.setComposite(AlphaComposite.getInstance( // Applying alpha variable to alpha composite
                AlphaComposite.SRC_OVER,
                alpha
        ));

        // Getting width and height
        int w = c.getWidth();
        int h = c.getHeight();

        // Re-write the paint method to scale the image relatively to its parent component
        if (getImageObserver() == null) {
            g2d.drawImage(getImage(), 0, 0, w, h, c);
        } else {
            g2d.drawImage(getImage(), 0, 0, w, h, getImageObserver());
        }
    }
}
