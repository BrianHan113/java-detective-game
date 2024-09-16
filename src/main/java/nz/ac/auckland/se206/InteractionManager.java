package nz.ac.auckland.se206;

public class InteractionManager {

  private static InteractionManager instance;

  public static InteractionManager getInstance() {
    if (instance == null) {
      instance = new InteractionManager();
    }
    return instance;
  }

  private boolean interactClue;
  private boolean interactExwife;
  private boolean interactFriend;
  private boolean interactSon;

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
}
