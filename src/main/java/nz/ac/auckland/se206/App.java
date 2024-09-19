package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nz.ac.auckland.se206.controllers.ContextController;
import nz.ac.auckland.se206.speech.FreeTextToSpeech;

/**
 * This is the entry point of the JavaFX application. This class initializes and runs the JavaFX
 * application.
 */
public class App extends Application {

  private static Scene scene;
  private static Parent menuRoot;
  private static Parent contextRoot;
  private static ContextController contextController;


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

  public static Parent getContextRoot() {
    return App.contextRoot;
  }

  public static ContextController getContextController() {
    return App.contextController;
  }

  /**
   * This method is invoked when the application starts. It loads and shows the "room" scene.
   *
   * @param stage the primary stage of the application
   * @throws IOException if the "src/main/resources/fxml/room.fxml" file is not found
   */
  @Override
  public void start(final Stage stage) throws IOException {
    FXMLLoader menuLoader = new FXMLLoader(App.class.getResource("/fxml/menu.fxml"));
    menuRoot = menuLoader.load();

    FXMLLoader contextLoader = new FXMLLoader(App.class.getResource("/fxml/context.fxml"));
    contextRoot = contextLoader.load();
    contextController = contextLoader.getController();

    scene = new Scene(menuRoot);
    stage.setScene(scene);
    stage.show();
    stage.setOnCloseRequest(event -> handleWindowClose(event));
    menuRoot.requestFocus();
  }

  private void handleWindowClose(WindowEvent event) {
    FreeTextToSpeech.deallocateSynthesizer();
  }
}
