package Content.RTContainers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class RTFrame extends JFrame implements PropertyChangeListener {
    /**
     * Create a new Rhythm-Tiles Frame object (is-a JFrame)
     */
    public RTFrame() {
        RTPanel contentPanel = new RTPanel();
        contentPanel.setOpaque(false);

        setUndecorated(true); // Remove decoration
        setFocusable(true); // Make frame focusable
        setAlwaysOnTop(true); // Set frame to always be on top
        setContentPane(contentPanel); // Set the content pane to contentPanel
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Make closing the frame exit the program
        setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Set the frame size the toolkits screen size

        requestFocusInWindow(); // Request the frame to be focused.
    }

    /**
     * Calculate the location of the center of the frame
     * @return Location of the center
     */
    public Point getCenterLocation() {
        // Center = ((x + (width/2)), (y + (height/2)))
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
        // Center = ((x - (width/2)), (y - (height/2)))
        setLocation(new Point(
                    (int) Math.round(p.getX() - (getSize().getWidth()/2)),
                    (int) Math.round(p.getY() - (getSize().getHeight()/2))
                )
        );
    }

    /**
     * Programmatically close the frame.
     */
    public void closeFrame() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)); // Triggers the default closing event
    }

    @Override
    public String toString() {
        return "TFrame{" +
                "JFrame{" + super.toString() + "}" +
                "}";
    }

    // Interface methods
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        repaint();
    }
}
