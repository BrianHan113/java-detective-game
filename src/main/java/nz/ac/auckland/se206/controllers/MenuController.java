package nz.ac.auckland.se206.controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class MenuController {
  @FXML private Button playButton;

  @FXML private Button quitButton;

  @FXML
  public void initialize() throws IOException {
    // Initialise
  }

  @FXML
  private void playButtonClicked(ActionEvent event) throws IOException {
    // Change to correct room to start game.
    Scene sceneOfBtn = ((Button) event.getSource()).getScene();
    sceneOfBtn.setRoot(SceneManager.getUiRoot(AppUi.CCTV));
  }

  @FXML
  private void quitButtonClicked(ActionEvent event) {
    // Quit game
    Platform.exit();
  }
}
