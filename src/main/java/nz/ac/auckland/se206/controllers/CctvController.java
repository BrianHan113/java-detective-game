package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaErrorEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.Evidence;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class CctvController extends Evidence {

  @FXML private MediaView mediaView;
  @FXML private Button playButton;
  @FXML private Slider progressSlider;

  private Media footage;
  private MediaPlayer videoPlayer;
  private boolean isPlaying;

  /**
   * Initializes the Security Cam view.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() {
    timerLabel.setText(timeManager.formatTime());
    timeManager.decrementTime(timerLabel);
    loadMedia();
  }

  // Method to update progress slider to follow video
  protected void updateValues() {
    Platform.runLater(
        new Runnable() {
          public void run() {
            // Update current position in progress bar to relative time in the video
            progressSlider.setValue(
                videoPlayer.getCurrentTime().toMillis()
                    / videoPlayer.getTotalDuration().toMillis()
                    * 100);
          }
        });
  }

  @FXML
  private void onPlayClick(ActionEvent event) {
    // Logic for play/pause button for security cam footage
    if (isPlaying) {
      videoPlayer.pause();
      playButton.setText("PLAY");
      isPlaying = false;
    } else {
      videoPlayer.play();
      playButton.setText("PAUSE");
      isPlaying = true;
    }
  }

  @FXML
  private void exitToCrimeScene(MouseEvent event) throws IOException {
    videoPlayer.pause();
    playButton.setText("PLAY");
    isPlaying = false;
    App.setRoot(SceneManager.getUiRoot(AppUi.CRIME_SCENE));
  }

  @FXML
  private void moveToOtherEvidence(MouseEvent event) throws IOException {

    // Find fxid of clicked object
    Label label = (Label) event.getSource();
    String labelId = label.getId();

    // Go to corresponding scene, and also puase the video
    switch (labelId) {
      case "evidenceLabel":
        videoPlayer.pause();
        playButton.setText("PLAY");
        isPlaying = false;
        App.setRoot(SceneManager.getUiRoot(AppUi.EVIDENCE));
        break;
      case "fingerprintLabel":
        videoPlayer.pause();
        playButton.setText("PLAY");
        isPlaying = false;
        App.setRoot(SceneManager.getUiRoot(AppUi.FINGERPRINT));
        break;
      case "shoeprintLabel":
        videoPlayer.pause();
        playButton.setText("PLAY");
        isPlaying = false;
        App.setRoot(SceneManager.getUiRoot(AppUi.FOOTPRINT));
        break;
      case "securityCamLabel":
        videoPlayer.pause();
        playButton.setText("PLAY");
        isPlaying = false;
        App.setRoot(SceneManager.getUiRoot(AppUi.CCTV));
        break;
      default:
        break;
    }
  }

  @FXML
  private void onMediaFailed(MediaErrorEvent event) {
    System.out.println("Media error occurred: " + event.getMediaError().getMessage());
    loadMedia();
  }

  private void loadMedia() {
    try {
      footage = new Media(App.class.getResource("/videos/SecurityCam4.mp4").toURI().toString());
      videoPlayer = new MediaPlayer(footage);
      mediaView.setMediaPlayer(videoPlayer);
    } catch (URISyntaxException e) {
      // Handle the exception as needed
      System.out.println("Problem with the media file");
      e.printStackTrace();
    }

    // Bind video player progress with progress slider
    videoPlayer
        .currentTimeProperty()
        .addListener(
            new InvalidationListener() {
              public void invalidated(Observable ov) {
                updateValues();
              }
            });

    // Bind progress slider with video property to allow for jumping
    progressSlider
        .valueProperty()
        .addListener(
            new InvalidationListener() {
              public void invalidated(Observable ov) {
                if (progressSlider.isPressed()) {
                  videoPlayer.seek(
                      videoPlayer
                          .getMedia()
                          .getDuration()
                          .multiply(progressSlider.getValue() / 100));
                }
              }
            });
  }
}
