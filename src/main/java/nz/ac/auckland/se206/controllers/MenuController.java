package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.Controller;
import nz.ac.auckland.se206.SceneManager;

public class MenuController implements Controller {

  @FXML private Button playButton;
  @FXML private Button quitButton;
  @FXML private ContextController contextController;
  @FXML private Parent contextRoot;

  @FXML
  public void initialize() {
    // Initialise
  }

  @FXML
  private void onPlayClicked(ActionEvent event) throws IOException {
    FXMLLoader contextLoader = new FXMLLoader(App.class.getResource("/fxml/context.fxml"));
    contextRoot = contextLoader.load();
    contextController = contextLoader.getController();
    App.setRoot(contextRoot);
    SceneManager.setupSceneMap();
  }

  @FXML
  private void onQuitClicked(ActionEvent event) {
    // Quit game
    Platform.exit();
  }
  
  public ContextController getContextController() {
    return contextController;
  }
}
