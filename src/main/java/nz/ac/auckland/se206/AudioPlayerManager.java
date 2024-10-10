package nz.ac.auckland.se206;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * AudioPlayerManager class is a singleton class that manages the audio in the game. It is used to
 * play audio files.
 */
public class AudioPlayerManager {

  private static AudioPlayerManager instance;

  /**
   * Returns the instance of the AudioPlayerManager class. If the instance is null, a new instance
   * is created.
   *
   * @return the instance of the AudioPlayerManager class
   */
  public static AudioPlayerManager getInstance() {
    if (instance == null) {
      instance = new AudioPlayerManager();
    }
    return instance;
  }

  private MediaPlayer mediaPlayer;
  private DoubleProperty volume = new SimpleDoubleProperty(1.0);

  /**
   * Plays the audio file specified by the {@code audioFile} parameter. The audio file is loaded
   * from the resources folder.
   *
   * @param audioFile the name of the audio file to be played
   */
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

  public DoubleProperty getVolumeProperty() {
    return volume;
  }
}
