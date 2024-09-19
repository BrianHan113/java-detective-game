package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.Controller;
import nz.ac.auckland.se206.SceneManager;

public class MenuController implements Controller{

  @FXML private Button playButton;
  @FXML private Button quitButton;

  @FXML
  public void initialize() {
    // Initialise
  }

  @FXML
  private void playButtonClicked(ActionEvent event) throws IOException {
    App.setRoot(App.getContextRoot());
    SceneManager.setupSceneMap();
  }

  @FXML
  private void quitButtonClicked(ActionEvent event) {
    // Quit game
    Platform.exit();
  }
}
