package nz.ac.auckland.se206;

import java.util.Random;

public class Voicelines {
  private static AudioPlayerManager audioPlayer = AudioPlayerManager.getInstance();

  public static void fillerVoiceLines(String role) {
    // Generate random int from 1 to 4 to get random voice lines
    Random random = new Random();
    int randomInt = random.nextInt(4) + 1;

    // Play a random voice line for each suspect in the game
    switch (role) {
      case "Ex-Wife":
        audioPlayer.playAudio("/exwife_tts/line_" + randomInt + ".mp3");
        break;
      case "Son":
        audioPlayer.playAudio("/son_tts/line_" + randomInt + ".mp3");
        break;
      case "Friend":
        audioPlayer.playAudio("/friend_tts/line_" + randomInt + ".mp3");
        break;

      default:
        audioPlayer.playAudio("/friend_tts/line_" + randomInt + ".mp3");
        break;
    }
  }

  public static void introVoiceLines(String role) {

    // Play a intro voice line for each suspect in the game
    switch (role) {
      case "Ex-Wife":
        audioPlayer.playAudio("/exwife_tts/intro.mp3");
        break;
      case "Son":
        audioPlayer.playAudio("/son_tts/intro.mp3");
        break;
      case "Friend":
        audioPlayer.playAudio("/friend_tts/intro.mp3");
        break;

      default:
        audioPlayer.playAudio("/friend_tts/intro.mp3");
        break;
    }
  }
}
