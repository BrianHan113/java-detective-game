package nz.ac.auckland.se206;

/**
 * InteractionManager class is a singleton class that manages the interactions between the player
 * and the characters and objects in the game. It is used to keep track of the interactions that
 * have occurred in the game.
 */
public class InteractionManager {

  private static InteractionManager instance;
  private static boolean interactClue = false;
  private static boolean interactExwife = false;
  private static boolean interactFriend = false;
  private static boolean interactSon = false;
  private static boolean interactHammer = false;
  private static boolean interactFingerprintDusted = false;
  private static boolean interactFootprint = false;
  private static boolean interactSecurityCamera = false;
  private static boolean isVisitExWife = false;
  private static boolean isVisitFriend = false;
  private static boolean isVisitSon = false;

  /**
   * Returns the instance of the InteractionManager class. If the instance is null, a new instance
   * is created. The instance is used to access the methods of the InteractionManager class. This is
   * so that only one instance of the InteractionManager class is created and used throughout the
   * game.
   *
   * @return the instance of the InteractionManager class
   */
  public static InteractionManager getInstance() {
    if (instance == null) {
      instance = new InteractionManager();
    }
    return instance;
  }

  public static boolean isVisitExWife() {
    return isVisitExWife;
  }

  public static void setVisitExWife(boolean isVisitExWife) {
    InteractionManager.isVisitExWife = isVisitExWife;
  }

  public static boolean isVisitFriend() {
    return isVisitFriend;
  }

  public static void setVisitFriend(boolean isVisitFriend) {
    InteractionManager.isVisitFriend = isVisitFriend;
  }

  public static boolean isVisitSon() {
    return isVisitSon;
  }

  public static void setVisitSon(boolean isVisitSon) {
    InteractionManager.isVisitSon = isVisitSon;
  }

  /**
   * Resets all important variables to prepare for game restart. This method is used to reset the
   * interactions between the player and the characters and objects in the game. It is called when
   * the user chooses to play again.
   */
  public static void resetManager() {
    // Reset all important variables to prepare for game restart
    interactClue = false;
    interactExwife = false;
    interactFriend = false;
    interactSon = false;

    interactHammer = false;
    interactFingerprintDusted = false;
    interactFootprint = false;
    interactSecurityCamera = false;

    isVisitExWife = false;
    isVisitFriend = false;
    isVisitSon = false;
  }

  private InteractionManager() {
    // Constructor
  }

  public boolean getInteractClue() {
    return interactClue;
  }

  public boolean getInteractExwife() {
    return interactExwife;
  }

  public boolean getInteractFriend() {
    return interactFriend;
  }

  public boolean getInteractSon() {
    return interactSon;
  }

  public boolean getInteractHammer() {
    return interactHammer;
  }

  public boolean getInteractFingerprintDusted() {
    return interactFingerprintDusted;
  }

  public boolean getInteractFootprint() {
    return interactFootprint;
  }

  public boolean getInteractSecurityCamera() {
    return interactSecurityCamera;
  }

  public void setInteractClue(boolean bool) {
    interactClue = bool;
  }

  public void setInteractExwife(boolean bool) {
    interactExwife = bool;
  }

  public void setInteractFriend(boolean bool) {
    interactFriend = bool;
  }

  public void setInteractSon(boolean bool) {
    interactSon = bool;
  }

  public void setInteractHammer(boolean bool) {
    interactHammer = bool;
  }

  public void setInteractFingerprintDusted(boolean bool) {
    interactFingerprintDusted = bool;
  }

  public void setInteractFootprint(boolean bool) {
    interactFootprint = bool;
  }

  public void setInteractSecurityCamera(boolean bool) {
    interactSecurityCamera = bool;
  }
}
