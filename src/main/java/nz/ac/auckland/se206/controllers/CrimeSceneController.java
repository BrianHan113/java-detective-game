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
import nz.ac.auckland.se206.AudioPlayerManager;
import nz.ac.auckland.se206.Controller;
import nz.ac.auckland.se206.Evidence;
import nz.ac.auckland.se206.InteractionManager;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.TimeManager;
import nz.ac.auckland.se206.Voicelines;

public class CrimeSceneController implements Controller {

  private static boolean isFirstTimeInit = true;
  private boolean timeOver = false;
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
  private AudioPlayerManager audioPlayer = AudioPlayerManager.getInstance();

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
    FeedbackController feedbackController =
        (FeedbackController) SceneManager.getController(AppUi.FEEDBACK);

    if (!timeOver
        && interact.getInteractClue()
        && interact.getInteractExwife()
        && interact.getInteractFriend()
        && interact.getInteractSon()) {

      timeManager.resetTimer(1);
      timerLbl.setText(timeManager.formatTime());

      audioPlayer.playAudio("/announcer/click_theif_submit.mp3");
      App.setRoot(SceneManager.getUiRoot(AppUi.GUESSING));

      timeOver = true;
    } else if (!timeOver
        && !interact.getInteractClue()
        && interact.getInteractExwife()
        && interact.getInteractFriend()
        && interact.getInteractSon()) {

      feedbackController.getWonLostLbl().setText("YOU LOST");
      audioPlayer.playAudio("/announcer/lost.mp3");
      feedbackController.getFeedbackStatusLbl().setText("You ran out of Time");
      feedbackController
          .getFeedbackTextArea()
          .setText(
              "Try be a little faster next time! Top-notch detectives need to be able to think"
                  + " fast!");
      feedbackController.enableBackButton();
      timeOver = true;

      App.setRoot(SceneManager.getUiRoot(AppUi.FEEDBACK));

      timeManager.stop();
      timeManager.resetTimer(5);
      timerLbl.setText(timeManager.formatTime());

      timeOver = true;
    } else if (!timeOver
        && interact.getInteractClue()
        && (!interact.getInteractExwife()
            || !interact.getInteractFriend()
            || !interact.getInteractSon())) {

      feedbackController.getWonLostLbl().setText("YOU LOST");
      audioPlayer.playAudio("/announcer/lost.mp3");
      feedbackController.getFeedbackStatusLbl().setText("You ran out of Time");
      feedbackController
          .getFeedbackTextArea()
          .setText(
              "Try be a little faster next time! Top-notch detectives need to be able to think"
                  + " fast!");
      feedbackController.enableBackButton();
      timeOver = true;

      App.setRoot(SceneManager.getUiRoot(AppUi.FEEDBACK));

      timeManager.stop();
      timeManager.resetTimer(5);
      timerLbl.setText(timeManager.formatTime());

      timeOver = true;
    } else if (!timeOver
        && !interact.getInteractClue()
        && (!interact.getInteractExwife()
            || !interact.getInteractFriend()
            || !interact.getInteractSon())) {

      feedbackController.getWonLostLbl().setText("YOU LOST");
      audioPlayer.playAudio("/announcer/lost.mp3");
      feedbackController.getFeedbackStatusLbl().setText("You ran out of Time");
      feedbackController
          .getFeedbackTextArea()
          .setText(
              "Try be a little faster next time! Top-notch detectives need to be able to think"
                  + " fast!");
      feedbackController.enableBackButton();
      timeOver = true;

      App.setRoot(SceneManager.getUiRoot(AppUi.FEEDBACK));

      timeManager.stop();
      timeManager.resetTimer(5);
      timerLbl.setText(timeManager.formatTime());

      timeOver = true;
    }
  }

  @FXML
  private void showEvidence(ActionEvent event) throws IOException {
    App.setRoot(SceneManager.getUiRoot(AppUi.EVIDENCE));
  }

  @FXML
  private void guessBtnClicked() throws IOException {
    if (interact.getInteractClue()
        && interact.getInteractExwife()
        && interact.getInteractFriend()
        && interact.getInteractSon()) {

      timeManager.resetTimer(1);
      timerLbl.setText(timeManager.formatTime());

      audioPlayer.playAudio("/announcer/click_theif_submit.mp3");
      App.setRoot(SceneManager.getUiRoot(AppUi.GUESSING));

      timeOver = true;
    } else if (!interact.getInteractClue()
        && interact.getInteractExwife()
        && interact.getInteractFriend()
        && interact.getInteractSon()) {

      audioPlayer.playAudio("/announcer/interact_item.mp3");
    } else if (interact.getInteractClue()
        && (!interact.getInteractExwife()
            || !interact.getInteractFriend()
            || !interact.getInteractSon())) {

      audioPlayer.playAudio("/announcer/chat_suspects.mp3");
    } else if (!interact.getInteractClue()
        && (!interact.getInteractExwife()
            || !interact.getInteractFriend()
            || !interact.getInteractSon())) {

      audioPlayer.playAudio("/announcer/chat_and_interact.mp3");
    }
  }

  @FXML
  private void handleRectangleClick(MouseEvent event) throws IOException {
    Rectangle shape = (Rectangle) event.getSource();
    String shapeId = shape.getId();
    Evidence evController = (Evidence) SceneManager.getController(AppUi.EVIDENCE);
    Evidence footController = (Evidence) SceneManager.getController(AppUi.FOOTPRINT);
    Evidence fingController = (Evidence) SceneManager.getController(AppUi.FINGERPRINT);
    Evidence cctvController = (Evidence) SceneManager.getController(AppUi.CCTV);

    switch (shapeId) {
      case "wifePinRect":
        App.setRoot(SceneManager.getUiRoot(AppUi.EX_WIFE));
        if (!InteractionManager.isVisitExWife()) {
          Voicelines.introVoiceLines("Ex-Wife");
          InteractionManager.setVisitExWife(true);
        }
        break;
      case "friendPinRect":
        App.setRoot(SceneManager.getUiRoot(AppUi.FRIEND));
        if (!InteractionManager.isVisitFriend()) {
          Voicelines.introVoiceLines("Friend");
          InteractionManager.setVisitFriend(true);
        }
        break;
      case "sonPinRect":
        App.setRoot(SceneManager.getUiRoot(AppUi.SON));
        if (!InteractionManager.isVisitSon()) {
          Voicelines.introVoiceLines("Son");
          InteractionManager.setVisitSon(true);
        }
        break;
      case "securityCameraRect":
        audioPlayer.playAudio("cctv.mp3");
        interact.setInteractClue(true);
        evController.setSecurityCamLabelVisible();
        footController.setSecurityCamLabelVisible();
        fingController.setSecurityCamLabelVisible();

        App.setRoot(SceneManager.getUiRoot(AppUi.CCTV));
        break;
      case "shoeprintRect":
        audioPlayer.playAudio("footstep.mp3");
        interact.setInteractClue(true);
        evController.setShoeprintLabelVisible();
        fingController.setShoeprintLabelVisible();
        cctvController.setShoeprintLabelVisible();

        App.setRoot(SceneManager.getUiRoot(AppUi.FOOTPRINT));
        break;
      case "hammerRect":
        audioPlayer.playAudio("hammer.mp3");
        evController.setFingerprintLabelVisible();
        footController.setFingerprintLabelVisible();
        cctvController.setFingerprintLabelVisible();
        App.setRoot(SceneManager.getUiRoot(AppUi.HAMMER));
        break;

      default:
        break;
    }
  }
}
