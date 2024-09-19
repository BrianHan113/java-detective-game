package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.speech.FreeTextToSpeech;

/**
 * This is the entry point of the JavaFX application. This class initializes and runs the JavaFX
 * application.
 */
public class App extends Application {

  private static Scene scene;

  /**
   * The main method that launches the JavaFX application.
   *
   * @param args the command line arguments
   */
  public static void main(final String[] args) {
    // TextToSpeech.speak("Why [pause] is that [pause] umm [pause] relevant?");

    launch();
  }

  /**
   * Sets the root of the scene to the specified FXML file.
   *
   * @param fxml the name of the FXML file (without extension)
   * @throws IOException if the FXML file is not found
   */
  public static void setRoot(Parent root) throws IOException {
    scene.setRoot(root);
  }

  public static Scene getScene() {
    return App.scene;
  }

  /**
   * This method is invoked when the application starts. It loads and shows the "room" scene.
   *
   * @param stage the primary stage of the application
   * @throws IOException if the "src/main/resources/fxml/room.fxml" file is not found
   */
  @Override
  public void start(final Stage stage) throws IOException {

    // Initialise all scenes and store them in scene manager.
    Map<AppUi, String> uiMap = new HashMap<>();
    uiMap.put(AppUi.FINGERPRINT, "fingerprint");
    uiMap.put(AppUi.FOOTPRINT, "footprint");
    uiMap.put(AppUi.CCTV, "cctv");
    uiMap.put(AppUi.EVIDENCE, "evidence");
    uiMap.put(AppUi.MAIN_MENU, "menu");
    uiMap.put(AppUi.SON, "son");

    uiMap.put(AppUi.EX_WIFE, "exwife");
    uiMap.put(AppUi.FEEDBACK, "feedback");

    uiMap.put(AppUi.FRIEND, "friend");
    uiMap.put(AppUi.GUESSING, "guess");
    uiMap.put(AppUi.HAMMER, "hammer");
    uiMap.put(AppUi.CRIME_SCENE, "crimeScene");

    for (Map.Entry<AppUi, String> entry : uiMap.entrySet()) {
      AppUi appUi = entry.getKey();
      String value = entry.getValue();

      FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/" + value + ".fxml"));
      Parent root = loader.load();

      SceneManager.addUi(appUi, root);
      SceneManager.addController(appUi, loader.getController());
    }

    Parent root = SceneManager.getUiRoot(AppUi.MAIN_MENU);

    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    stage.setOnCloseRequest(event -> handleWindowClose(event));
    root.requestFocus();
  }

  private void handleWindowClose(WindowEvent event) {
    FreeTextToSpeech.deallocateSynthesizer();
  }
}
