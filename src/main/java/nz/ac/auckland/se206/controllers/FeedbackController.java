package nz.ac.auckland.se206.controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.Controller;

public class FeedbackController implements Controller {
  @FXML private Label wonLostLbl;
  @FXML private Label feedbackStatusLbl;
  @FXML private TextArea feedbackTextArea;
  @FXML private Button exitBtn;
  @FXML private Button playAgainBtn;

  @FXML
  public void initialize() {
  }

  @FXML
  private void exitGame() {
    // Quit game
    Platform.exit();
  }

  @FXML
  private void resetGame() throws IOException {
    App.setRoot(App.getMenuRoot());
  }

  public void displayFeedback(String feedback) {
    feedbackStatusLbl.setText("Review your Feedback!");
    feedbackTextArea.appendText(feedback);
  }
}
