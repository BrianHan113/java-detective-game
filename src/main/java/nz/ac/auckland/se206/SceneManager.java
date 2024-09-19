package nz.ac.auckland.se206;

import java.util.HashMap;
import javafx.scene.Parent;

public class SceneManager {

  public enum AppUi {
    MAIN_MENU,
    SON,
    EX_WIFE,
    FRIEND,
    CRIME_SCENE,
    CCTV,
    FEEDBACK,
    FINGERPRINT,
    FOOTPRINT,
    GUESSING,
    HAMMER,
    EVIDENCE,
  }

  private static HashMap<AppUi, Parent> sceneMap = new HashMap<AppUi, Parent>();
  private static HashMap<AppUi, Controller> controllerMap = new HashMap<AppUi, Controller>();

  public static void addUi(AppUi appUi, Parent uiRoot) {
    sceneMap.put(appUi, uiRoot);
  }

  public static Parent getUiRoot(AppUi appUi) {
    return sceneMap.get(appUi);
  }

  public static void addController(AppUi appUi, Controller controller) {
    controllerMap.put(appUi, controller);
  }

  public static Controller getController(AppUi appUi) {
    return controllerMap.get(appUi);
  }
}
