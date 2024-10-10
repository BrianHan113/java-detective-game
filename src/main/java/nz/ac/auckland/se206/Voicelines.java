package nz.ac.auckland.se206;

import java.util.Random;

/**
 * Class to play voice lines for each suspect in the game. The voice lines are played based on the
 * role of the suspect. The voice lines are played when the user first visits, or sends a message to
 * the suspect.
 */
public class Voicelines {
  private static AudioPlayerManager audioPlayer = AudioPlayerManager.getInstance();

  /**
   * Play a random voice line for each suspect in the game when the user sends a message to them.
   * The voice line is played based on the role of the suspect.
   *
   * @param role the role of the suspect
   */
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

  /**
   * Play a intro voice line for each suspect in the game when the user first visits them. The voice
   * line is played based on the role of the suspect.
   *
   * @param role the role of the suspect
   */
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
