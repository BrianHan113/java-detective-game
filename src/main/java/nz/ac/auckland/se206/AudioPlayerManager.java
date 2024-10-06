package nz.ac.auckland.se206;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioPlayerManager {

  private static AudioPlayerManager instance;
  private DoubleProperty volume = new SimpleDoubleProperty(1.0);

  public static AudioPlayerManager getInstance() {
    if (instance == null) {
      instance = new AudioPlayerManager();
    }
    return instance;
  }

  private MediaPlayer mediaPlayer;

  public void playAudio(String audioFile) {
    try {
      // Prevent playing more than 1 audio at once
      if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
        mediaPlayer.stop();
      }

      // Load and play audio
      Media media = new Media(App.class.getResource("/sounds/" + audioFile).toURI().toString());
      mediaPlayer = new MediaPlayer(media);
      mediaPlayer.volumeProperty().bind(volume);
      mediaPlayer.play();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public DoubleProperty volumeProperty() {
    return volume;
  }
}
