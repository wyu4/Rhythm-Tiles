import Content.GameManager;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

public class Game {
    public static void main(String[] args) {
        Platform.setImplicitExit(false);
        new JFXPanel(); // Work around "Toolkit not initialized" error
        new GameManager();
    }
}
