package nz.ac.auckland.se206;

import java.util.Random;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Voicelines {
  private static MediaPlayer mediaPlayer;

  public static void fillerVoiceLines(String role) {
    // Generate random int from 1 to 4 to get random voice lines
    Random random = new Random();
    int randomInt = random.nextInt(4) + 1;
    Media sound;

    // Play a random voice line for each suspect in the game
    switch (role) {
      case "Ex-Wife":
        sound =
            new Media(
                App.class
                    .getResource("/sounds/exwife_tts/line_" + randomInt + ".mp3")
                    .toExternalForm());
        break;
      case "Son":
        sound =
            new Media(
                App.class
                    .getResource("/sounds/son_tts/line_" + randomInt + ".mp3")
                    .toExternalForm());
        break;
      case "Friend":
        sound =
            new Media(
                App.class
                    .getResource("/sounds/friend_tts/line_" + randomInt + ".mp3")
                    .toExternalForm());
        break;

      default:
        sound = new Media(App.class.getResource("/sounds/friend_tts/line_1.mp3").toExternalForm());
        break;
    }
    mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.play();
  }

  public static void introVoiceLines(String role) {
    Media sound;

    // Play a intro voice line for each suspect in the game
    switch (role) {
      case "Ex-Wife":
        sound = new Media(App.class.getResource("/sounds/exwife_tts/intro.mp3").toExternalForm());
        break;
      case "Son":
        sound = new Media(App.class.getResource("/sounds/son_tts/intro.mp3").toExternalForm());
        break;
      case "Friend":
        sound = new Media(App.class.getResource("/sounds/friend_tts/intro.mp3").toExternalForm());
        break;

      default:
        sound = new Media(App.class.getResource("/sounds/friend_tts/line_1.mp3").toExternalForm());
        break;
    }
    mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.play();
  }
}
