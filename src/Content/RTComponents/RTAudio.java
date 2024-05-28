package Content.RTComponents;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class RTAudio {
    private String path;
    private boolean playing;
    private Media sound;
    private MediaPlayer player;

    public RTAudio() {
        this("");
    }

    public RTAudio(String path) {
        setPath(path);
        playing = false;
    }

    public void setPath(String path) {
        this.path = path;
        File audioFile = new File(path);

        if (audioFile.exists()) {
            sound = new Media(audioFile.toURI().toString());
            player = new MediaPlayer(sound);
        } else {
            System.out.println(getClass().getName() + " - File [" + path + "] does not exist.");
        }
    }

    public void play() {
        if (player != null) {
            if (!playing) {
                playing = true;
                player.play();
            }
        } else {
            System.out.println(getClass().getName() + " - Media player is null.");
        }
    }

    public void pause() {
        if (player != null) {
            if (playing) {
                playing = false;
                player.pause();
            }
        } else {
            System.out.println(getClass().getName() + " - Media player is null.");
        }
    }
}
