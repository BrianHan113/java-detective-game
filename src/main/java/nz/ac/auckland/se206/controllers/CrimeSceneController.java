package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.InteractionManager;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.TimeManager;

public class CrimeSceneController {

  private static boolean isFirstTimeInit = true;
  private static boolean timeOver = false;
  private static InteractionManager interact = InteractionManager.getInstance();

  @FXML private Button viewEvidenceBtn;
  @FXML private Rectangle shoeprintRect;
  @FXML private Rectangle securityCameraRect;
  @FXML private Rectangle hammerRect;
  @FXML private Label timerLbl;
  @FXML private Rectangle sonPinRect;
  @FXML private Rectangle friendPinRect;
  @FXML private Rectangle wifePinRect;
  @FXML private Button guessBtn;

  private int minute;
  private int second;
  private Timeline timeline;
  private TimeManager timeManager = TimeManager.getInstance();

  @FXML
  public void initialize() {
    if (isFirstTimeInit) {
      isFirstTimeInit = false;
    }
    timerLbl.setText(timeManager.formatTime());
    decrementTime();
  }

  private void decrementTime() {
    timeline = new Timeline(new KeyFrame(Duration.millis(1), e -> updateTimerLabel()));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  private void updateTimerLabel() {
    minute = timeManager.getMinute();
    second = timeManager.getSecond();
    if (minute == 0 && second == 0) {
      timerLbl.setText("Time's Up!");
      try {
        afterTimeLimit();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      timerLbl.setText(String.format("%02d:%02d", minute, second));
    }
  }

  private void afterTimeLimit() throws IOException {

    if (!timeOver
        && interact.getInteractClue()
        && interact.getInteractExwife()
        && interact.getInteractFriend()
        && interact.getInteractSon()) {
      System.out.println("Clues: Y, All suspects: Y");
      timeOver = true;
    } else if (!timeOver
        && !interact.getInteractClue()
        && interact.getInteractExwife()
        && interact.getInteractFriend()
        && interact.getInteractSon()) {
      System.out.println("Clues: N, All suspects: Y");
      timeOver = true;
    } else if (!timeOver
        && interact.getInteractClue()
        && (!interact.getInteractExwife()
            || !interact.getInteractFriend()
            || !interact.getInteractSon())) {
      System.out.println("Clues: Y, All suspects: N");
      timeOver = true;
    } else if (!timeOver
        && !interact.getInteractClue()
        && (!interact.getInteractExwife()
            || !interact.getInteractFriend()
            || !interact.getInteractSon())) {
      System.out.println("Clues: N, All suspects: N");
      timeOver = true;
    }
  }

  @FXML
  private void showEvidence(ActionEvent event) {
    Button button = (Button) event.getSource();
    Scene scene = button.getScene();
    scene.setRoot(SceneManager.getUiRoot(AppUi.EVIDENCE));
  }

  @FXML
  private void guessBtnClicked() {
    System.out.println("Guess Button Clicked");
  }

  @FXML
  private void handleRectangleClick(MouseEvent event) {
    Rectangle shape = (Rectangle) event.getSource();
    String shapeId = shape.getId();
    Scene sceneOfShape = shape.getScene();

    switch (shapeId) {
      case "wifePinRect":
        sceneOfShape.setRoot(SceneManager.getUiRoot(AppUi.EX_WIFE));
        break;
      case "friendPinRect":
        sceneOfShape.setRoot(SceneManager.getUiRoot(AppUi.FRIEND));
        break;
      case "sonPinRect":
        sceneOfShape.setRoot(SceneManager.getUiRoot(AppUi.SON));
        break;
      case "securityCameraRect":
        sceneOfShape.setRoot(SceneManager.getUiRoot(AppUi.CCTV));
        break;
      case "shoeprintRect":
        sceneOfShape.setRoot(SceneManager.getUiRoot(AppUi.FOOTPRINT));
        break;
      case "hammerRect":
        sceneOfShape.setRoot(SceneManager.getUiRoot(AppUi.HAMMER));
        break;

      default:
        break;
    }
  }
}
