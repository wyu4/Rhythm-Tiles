package Content.RTComponents;

import javax.swing.*;
import java.awt.*;

public class RTScrollPane extends JScrollPane {

    public RTScrollPane() {
        this("RTScrollPanel");
    }

    public RTScrollPane(String name) {
        setName(name);

        setBackground(new Color(0, 0, 0, 0)); // Set the background color to black
        setBorder(null); // Delete borders
        setLayout(null); // Remove layout manager
        setFocusable(false); // Remove focusable
        setName(name); // Set name to the corresponding parameter
    }
}
