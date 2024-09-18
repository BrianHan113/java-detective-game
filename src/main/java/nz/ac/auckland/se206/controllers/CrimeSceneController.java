package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.Controller;
import nz.ac.auckland.se206.InteractionManager;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.TimeManager;

public class CrimeSceneController implements Controller {

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

      timeManager.resetTimer(1);
      timerLbl.setText(timeManager.formatTime());

      App.setRoot(SceneManager.getUiRoot(AppUi.GUESSING));

      timeOver = true;
    } else if (!timeOver
        && !interact.getInteractClue()
        && interact.getInteractExwife()
        && interact.getInteractFriend()
        && interact.getInteractSon()) {
      System.out.println("Clues: N, All suspects: Y");

      App.setRoot(SceneManager.getUiRoot(AppUi.FEEDBACK));

      timeOver = true;
    } else if (!timeOver
        && interact.getInteractClue()
        && (!interact.getInteractExwife()
            || !interact.getInteractFriend()
            || !interact.getInteractSon())) {
      System.out.println("Clues: Y, All suspects: N");

      App.setRoot(SceneManager.getUiRoot(AppUi.FEEDBACK));

      timeOver = true;
    } else if (!timeOver
        && !interact.getInteractClue()
        && (!interact.getInteractExwife()
            || !interact.getInteractFriend()
            || !interact.getInteractSon())) {
      System.out.println("Clues: N, All suspects: N");

      App.setRoot(SceneManager.getUiRoot(AppUi.FEEDBACK));

      timeOver = true;
    }
  }

  @FXML
  private void showEvidence(ActionEvent event) throws IOException {
    App.setRoot(SceneManager.getUiRoot(AppUi.EVIDENCE));
  }

  @FXML
  private void guessBtnClicked() throws IOException{
    App.setRoot(SceneManager.getUiRoot(AppUi.GUESSING));
  }

  @FXML
  private void handleRectangleClick(MouseEvent event) throws IOException {
    Rectangle shape = (Rectangle) event.getSource();
    String shapeId = shape.getId();

    switch (shapeId) {
      case "wifePinRect":
        App.setRoot(SceneManager.getUiRoot(AppUi.EX_WIFE));
        break;
      case "friendPinRect":
        App.setRoot(SceneManager.getUiRoot(AppUi.FRIEND));
        break;
      case "sonPinRect":
        App.setRoot(SceneManager.getUiRoot(AppUi.SON));
        break;
      case "securityCameraRect":
        interact.setInteractClue(true);
        App.setRoot(SceneManager.getUiRoot(AppUi.CCTV));
        break;
      case "shoeprintRect":
        interact.setInteractClue(true);
        App.setRoot(SceneManager.getUiRoot(AppUi.FOOTPRINT));
        break;
      case "hammerRect":
        App.setRoot(SceneManager.getUiRoot(AppUi.HAMMER));
        break;

      default:
        break;
    }
  }
}
