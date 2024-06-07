package Content.RTComponents;

import javafx.animation.PauseTransition;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class RTAudio {
    private String path;
    private boolean playing;
    private Media sound;
    private MediaPlayer player, offsetPlayer;
    private double offset;

    public RTAudio() {
        this("");
    }

    public RTAudio(String path) {
        setPath(path);
        playing = false;
        offset = 0.0;
    }

    public void setPath(String path) {
        this.path = path;

        if (isValidPath(path)) {
            String url = new File(path).toURI().toString();
            sound = new Media(url);
            if (player != null) { // Stop and dispose any previous media player
                player.stop();
                player.dispose();
            }
            if (offsetPlayer != null) {
                offsetPlayer.stop();
                offsetPlayer.dispose();
            }
            player = new MediaPlayer(sound);
            player.setVolume(0.0);
            offsetPlayer = new MediaPlayer(sound);

            // Linking the player and the offset player
            player.setOnPlaying(() -> {
                double owedOffset = Math.max(0, offset-getCurrentPosition());

                PauseTransition pause = new PauseTransition(Duration.millis(owedOffset));
                pause.setOnFinished((_) -> {
                    if (playing) {
                        offsetPlayer.seek(Duration.millis(getCurrentPosition()-offset));
                        offsetPlayer.play();
                    }
                });
                pause.play();
            });

            player.setOnPaused(() -> {
                offsetPlayer.pause();
            });

        } else {
            System.out.println(getClass().getName() + " - File [" + path + "] does not exist.");
        }
    }

    public String getPath() {
        return path;
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

    public boolean isPlaying() {
        return playing;
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

    public void restart() {
        if (player != null) {
            player.seek(Duration.ZERO);
        } else {
            System.out.println(getClass().getName() + " - Media player is null.");
        }
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public double getOffset() {
        return offset;
    }

    /**
     * Get the length of the audio in milliseconds.
     * @return {@code double} milliseconds
     */
    public double getLength() {
        return player.getTotalDuration().toMillis();
    }

    /**
     * Get the current time position in milliseconds.
     * @return {@code double} milliseconds
     */
    public double getCurrentPosition() {
        return player.getCurrentTime().toMillis();
    }

    /**
     * Set the current time position in milliseconds.
     * @param m {@code double} milliseconds
     */
    public void setCurrentPosition(double m) {
        player.seek(Duration.millis(m));
    }

    /**
     * Get the current rate/speed of the audio
     * @return {@code double} representing the audio rate ({@code 1.0} being the normal speed)
     */
    public double getRate() {
        return player.getRate();
    }

    /**
     * Set the current rate/speed of the audio
     * @param r {@code double} the new audio rate ({@code 1.0} being the normal speed)
     */
    public void setRate(double r) {
        player.setRate(r);
        offsetPlayer.setRate(r);
    }

    /**
     * Check if the file path is valid
     * @param path The file path (from Content Root) to check
     * @return {@code true} if the path ends with ".mp3" and exists, {@code false} if the path is invalid.
     */
    private boolean isValidPath(String path) {
        if (path.endsWith(".mp3")) {
            File audioFile = new File(path);
            return audioFile.exists();
        }
        return false;
    }
}
