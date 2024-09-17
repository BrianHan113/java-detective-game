package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
    System.out.println("Exit Game Button Pressed");
  }

  @FXML
  private void resetGame() {
    System.out.println("Reset Game Button Pressed");
  }
}
