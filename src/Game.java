import Content.GameManager;
import javafx.embed.swing.JFXPanel;

public class Game {
    public static void main(String[] args) {
        new JFXPanel(); // Work around "Toolkit not initialized" error
        new GameManager();
    }
}
