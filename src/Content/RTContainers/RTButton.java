package Content.RTContainers;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class RTButton extends JButton {
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
    }
}
