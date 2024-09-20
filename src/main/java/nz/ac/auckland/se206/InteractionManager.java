package nz.ac.auckland.se206;

public class InteractionManager {

  private static InteractionManager instance;

  public static InteractionManager getInstance() {
    if (instance == null) {
      instance = new InteractionManager();
    }
    return instance;
  }

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

  private InteractionManager() {}

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
}
