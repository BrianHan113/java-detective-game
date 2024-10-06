package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
  private static InteractionManager interact = InteractionManager.getInstance();

  private boolean timeOver = false;

  @FXML private Button viewEvidenceBtn;
  @FXML private Rectangle shoeprintRect;
  @FXML private Rectangle securityCameraRect;
  @FXML private Rectangle hammerRect;
  @FXML private Label timerLbl;
  @FXML private Rectangle sonPinRect;
  @FXML private Rectangle friendPinRect;
  @FXML private Rectangle wifePinRect;
  @FXML private Button guessBtn;
  @FXML private ImageView sonHover;
  @FXML private ImageView exwifeHover;
  @FXML private ImageView friendHover;
  @FXML private ImageView securityCameraHover;
  @FXML private ImageView hammerHover;
  @FXML private ImageView shoeprintHover;

  private int minute;
  private int second;
  private Timeline timeline;
  private TimeManager timeManager = TimeManager.getInstance();
  private AudioPlayerManager audioPlayer = AudioPlayerManager.getInstance();
  private FeedbackController feedbackController;

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
    // Update the timer depending on singleton saved time
    minute = timeManager.getMinute();
    second = timeManager.getSecond();
    if (minute == 0 && second == 0) {
      timerLbl.setText("Time's Up!");
      try {
        // Timeout
        afterTimeLimit();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      // update time
      timerLbl.setText(String.format("%02d:%02d", minute, second));
    }
  }

  private void timeoutFeedbackScene() throws IOException {
    // Edit feedback label and play audio
    feedbackController.getWonLostLbl().setText("YOU LOST");
    audioPlayer.playAudio("/announcer/lost.mp3");
    feedbackController.getFeedbackStatusLbl().setText("You ran out of Time");
    // Append appropriate message
    feedbackController
        .getFeedbackTextArea()
        .setText(
            "Try be a little faster next time! Top-notch detectives need to be able to think"
                + " fast!");
    feedbackController.enableBackButton();
    timeOver = true;
    timeManager.setFirstTimeUp(false);

    App.setRoot(SceneManager.getUiRoot(AppUi.FEEDBACK));

    // Ensure timer resets
    timeManager.stop();
    timeManager.resetTimer(5);
    timerLbl.setText(timeManager.formatTime());
    timeOver = true;
  }

  private void afterTimeLimit() throws IOException {
    feedbackController = (FeedbackController) SceneManager.getController(AppUi.FEEDBACK);
    // Called when timer reaches 0

    // Pause CCTV when timeout occurs
    CctvController cctvController = (CctvController) SceneManager.getController(AppUi.CCTV);
    cctvController.getMediaPlayer().pause();

    // If user has met all requirments, go through to guessing scene
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
      timeManager.setFirstTimeUp(false);

      // Otherwise give lost to timeout screen on feedback
    } else if (!timeOver
        && !interact.getInteractClue()
        && interact.getInteractExwife()
        && interact.getInteractFriend()
        && interact.getInteractSon()) {

      // You lost feedback screen
      timeoutFeedbackScene();

    } else if (!timeOver
        && interact.getInteractClue()
        && (!interact.getInteractExwife()
            || !interact.getInteractFriend()
            || !interact.getInteractSon())) {

      timeoutFeedbackScene();

    } else if (!timeOver
        && !interact.getInteractClue()
        && (!interact.getInteractExwife()
            || !interact.getInteractFriend()
            || !interact.getInteractSon())) {

      timeoutFeedbackScene();
    }
  }

  @FXML
  private void onShowEvidence(ActionEvent event) throws IOException {
    App.setRoot(SceneManager.getUiRoot(AppUi.EVIDENCE));
  }

  @FXML
  private void onGuessClicked() throws IOException {
    // Interact with clue and all suspects
    if (interact.getInteractClue()
        && interact.getInteractExwife()
        && interact.getInteractFriend()
        && interact.getInteractSon()) {

      // proceed to guessing scene
      timeOver = true;
      timeManager.resetTimer(1);
      timerLbl.setText(timeManager.formatTime());

      audioPlayer.playAudio("/announcer/click_theif_submit.mp3");
      App.setRoot(SceneManager.getUiRoot(AppUi.GUESSING));

      // No item interaction
    } else if (!interact.getInteractClue()
        && interact.getInteractExwife()
        && interact.getInteractFriend()
        && interact.getInteractSon()) {

      audioPlayer.playAudio("/announcer/interact_item.mp3");

      // No suspects interaction
    } else if (interact.getInteractClue()
        && (!interact.getInteractExwife()
            || !interact.getInteractFriend()
            || !interact.getInteractSon())) {

      audioPlayer.playAudio("/announcer/chat_suspects.mp3");

      // No item or suspects interaction
    } else if (!interact.getInteractClue()
        && (!interact.getInteractExwife()
            || !interact.getInteractFriend()
            || !interact.getInteractSon())) {

      audioPlayer.playAudio("/announcer/chat_and_interact.mp3");
    }
  }

  @FXML
  private void handleRectangleClick(MouseEvent event) throws IOException {

    // Get fxid of clicked rectangle
    Rectangle shape = (Rectangle) event.getSource();
    String shapeId = shape.getId();

    // Access controllers to change if a clue is viewable in the clue view
    Evidence evController = (Evidence) SceneManager.getController(AppUi.EVIDENCE);
    Evidence footController = (Evidence) SceneManager.getController(AppUi.FOOTPRINT);
    Evidence fingController = (Evidence) SceneManager.getController(AppUi.FINGERPRINT);
    Evidence cctvController = (Evidence) SceneManager.getController(AppUi.CCTV);

    // Handle map clicks
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

      // Handle item interactions
      case "securityCameraRect":
        audioPlayer.playAudio("cctv.mp3");
        interact.setInteractClue(true);
        // Set visible in the corresponding clue views
        evController.setSecurityCamLabelVisible();
        footController.setSecurityCamLabelVisible();
        fingController.setSecurityCamLabelVisible();

        App.setRoot(SceneManager.getUiRoot(AppUi.CCTV));
        break;
      case "shoeprintRect":
        audioPlayer.playAudio("footstep.mp3");
        interact.setInteractClue(true);
        // Set visible in the corresponding clue views
        evController.setShoeprintLabelVisible();
        fingController.setShoeprintLabelVisible();
        cctvController.setShoeprintLabelVisible();

        App.setRoot(SceneManager.getUiRoot(AppUi.FOOTPRINT));
        break;
      case "hammerRect":
        audioPlayer.playAudio("hammer.mp3");
        // Set visible in the corresponding clue views
        evController.setFingerprintLabelVisible();
        footController.setFingerprintLabelVisible();
        cctvController.setFingerprintLabelVisible();
        App.setRoot(SceneManager.getUiRoot(AppUi.HAMMER));
        break;

      default:
        break;
    }
  }

  @FXML
  private void handleRectangleEnter(MouseEvent event) {
    Rectangle shape = (Rectangle) event.getSource();
    String shapeId = shape.getId();

    // Handle map clicks
    switch (shapeId) {
      case "wifePinRect":
        exwifeHover.setVisible(true);
        break;
      case "friendPinRect":
        friendHover.setVisible(true);
        break;
      case "sonPinRect":
        sonHover.setVisible(true);
        break;
      case "securityCameraRect":
        securityCameraHover.setVisible(true);
        break;
      case "hammerRect":
        hammerHover.setVisible(true);
        break;
      case "shoeprintRect":
        shoeprintHover.setVisible(true);
        break;
      default:
        break;
    }
  }

  @FXML
  private void handleRectangleExit(MouseEvent event) {
    Rectangle shape = (Rectangle) event.getSource();
    String shapeId = shape.getId();

    // Handle map clicks
    switch (shapeId) {
      case "wifePinRect":
        exwifeHover.setVisible(false);
        break;
      case "friendPinRect":
        friendHover.setVisible(false);
        break;
      case "sonPinRect":
        sonHover.setVisible(false);
        break;
      case "securityCameraRect":
        securityCameraHover.setVisible(false);
        break;
      case "hammerRect":
        hammerHover.setVisible(false);
        break;
      case "shoeprintRect":
        shoeprintHover.setVisible(false);
        break;
      default:
        break;
    }
  }
}
