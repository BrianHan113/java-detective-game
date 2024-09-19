package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
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
  // Initialise all scenes and store them in scene manager.
  private static Map<AppUi, String> uiMap = Map.ofEntries(
    Map.entry(AppUi.CCTV, "cctv"),
    Map.entry(AppUi.FINGERPRINT, "fingerprint"),
    Map.entry(AppUi.FOOTPRINT, "footprint"),
    Map.entry(AppUi.EVIDENCE, "evidence"),
    Map.entry(AppUi.MAIN_MENU, "menu"),
    Map.entry(AppUi.SON, "son"),
    Map.entry(AppUi.EX_WIFE, "exwife"),
    Map.entry(AppUi.FEEDBACK, "feedback"),
    Map.entry(AppUi.FRIEND, "friend"),
    Map.entry(AppUi.GUESSING, "guess"),
    Map.entry(AppUi.HAMMER, "hammer"),
    Map.entry(AppUi.CRIME_SCENE, "crimeScene")
  );

    
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

  public static void setupSceneMap() throws IOException{
    if (!sceneMap.isEmpty()) {
      sceneMap.clear();
      controllerMap.clear();
    }
    for (Map.Entry<AppUi, String> entry : uiMap.entrySet()) {
      AppUi appUi = entry.getKey();
      String value = entry.getValue();

      FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/" + value + ".fxml"));
      Parent root = loader.load();

      SceneManager.addUi(appUi, root);
      SceneManager.addController(appUi, loader.getController());
    }
  }
}
