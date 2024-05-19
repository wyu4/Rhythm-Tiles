package Content.RTContainers;

import javax.swing.*;
import java.awt.*;

public class RTButton extends JButton {
    public RTButton() {
        this("RTButton");
    }

    public RTButton(String name) {
        this(name, "");
    }

    public RTButton(String name, String text) {
        setName(name);
        setText(text);
        setFocusable(false);
        setBackground(new Color(30, 47, 64));
        setForeground(new Color(255, 255, 255));
    }
}
