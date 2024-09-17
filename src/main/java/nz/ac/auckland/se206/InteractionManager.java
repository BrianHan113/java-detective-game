package nz.ac.auckland.se206;

public class InteractionManager {

  private static InteractionManager instance;

  public static InteractionManager getInstance() {
    if (instance == null) {
      instance = new InteractionManager();
    }
    return instance;
  }

  private boolean interactClue = false;
  private boolean interactExwife = false;
  private boolean interactFriend = false;
  private boolean interactSon = false;

  private boolean interactHammer = false;
  private boolean interactFingerprintDusted = false;
  private boolean interactFootprint = false;
  private boolean interactSecurityCamera = false;

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

  public void setInteractClue(boolean interactClue) {
    this.interactClue = interactClue;
  }

  public void setInteractExwife(boolean interactExwife) {
    this.interactExwife = interactExwife;
  }

  public void setInteractFriend(boolean interactFriend) {
    this.interactFriend = interactFriend;
  }

  public void setInteractSon(boolean interactSon) {
    this.interactSon = interactSon;
  }

  public void setInteractHammer(boolean interactHammer) {
    this.interactHammer = interactHammer;
  }

  public void setInteractFingerprintDusted(boolean interactFingerprint) {
    this.interactFingerprintDusted = interactFingerprint;
  }

  public void setInteractFootprint(boolean interactFootprint) {
    this.interactFootprint = interactFootprint;
  }

  public void setInteractSecurityCamera(boolean interactSecurityCamera) {
    this.interactSecurityCamera = interactSecurityCamera;
  }
}
