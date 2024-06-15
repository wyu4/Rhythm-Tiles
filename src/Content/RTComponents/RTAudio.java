package Content.RTComponents;

import javafx.animation.PauseTransition;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

import java.io.File;

public class RTAudio {
    private String path;
    private boolean playing, disposeOnEnd;
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
        disposeOnEnd = false;
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
                playing = true;
                if (offset == 0.0) {
                    offsetPlayer.play();
                } else {
//                    double waitTime = Math.max(0, offset-getCurrentPosition());
                    offsetPlayer.seek(Duration.millis(getCurrentPosition()-offset));
                    offsetPlayer.setVolume(0);
                    offsetPlayer.play();
//                    PauseTransition pause = new PauseTransition(Duration.millis(waitTime));
//                    pause.setOnFinished((e) -> {
//                        offsetPlayer.seek(Duration.millis(getCurrentPosition()-offset));
//                        if (playing) {
//                            offsetPlayer.play();
//                        }
//                    });
//                    pause.play();
                }
            });

            // player.setOnPlaying(() -> {
            //     playing = true;
            //     // offsetPlayer.play();
            // });

            player.setOnPaused(() -> {
                offsetPlayer.pause();
                playing = false;
            });

            player.currentTimeProperty().addListener((e) -> {
                if (offsetPlayer.getVolume() == 0) {
                    if (getCurrentPosition() >= offset) {
                        offsetPlayer.setVolume(1.0);
                        offsetPlayer.play();
                        offsetPlayer.seek(Duration.millis(getCurrentPosition()-offset));
                    }
                }
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
            player.pause();
            player.seek(Duration.ZERO);
            offsetPlayer.seek(Duration.ZERO);
            player.play();
        } else {
            System.out.println(getClass().getName() + " - Media player is null.");
        }
    }

    /**
     * Set the offset of the audio.
     * @param offset Positive milliseconds to play audio later than it's supposed to, negative to play audio earlier than it's supposed to.
     */
    public void setOffset(double offset) {
        this.offset = offset;
    }

    public double getOffset() {
        return offset;
    }

    public double getActualOffset() {
        return getCurrentPosition() - offsetPlayer.getCurrentTime().toMillis();
    }

    public void setDisposeOnEnd(boolean disposeOnEnd) {
        this.disposeOnEnd = disposeOnEnd;
    }

    public boolean getDisposeOnEnd() {
        return disposeOnEnd;
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
