package nz.ac.auckland.se206;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioPlayerManager {

  private static AudioPlayerManager instance;

  public static AudioPlayerManager getInstance() {
    if (instance == null) {
      instance = new AudioPlayerManager();
    }
    return instance;
  }

  private MediaPlayer mediaPlayer;

  public void playAudio(String audioFile) {
    try {
      if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
        mediaPlayer.stop();
      }
      Media media = new Media(App.class.getResource("/sounds/" + audioFile).toURI().toString());
      mediaPlayer = new MediaPlayer(media);
      mediaPlayer.play();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
