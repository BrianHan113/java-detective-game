package nz.ac.auckland.se206.controllers;

import java.net.URISyntaxException;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Circle;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.App;

public class CctvController {

  @FXML private Label evidenceLabel;
  @FXML private Circle exitCircle;
  @FXML private Label fingerprintHideLabel;
  @FXML private Label fingerprintLabel;
  @FXML private MediaView mediaView;
  @FXML private Button playButton;
  @FXML private Slider progressSlider;
  @FXML private Label shoeprintHideLabel;
  @FXML private Label shoeprintLabel;
  @FXML private Label timerLabel;

  private Media footage;
  private MediaPlayer videoPlayer;
  private boolean isPlaying;

  /**
   * Initializes the Security Cam view.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() throws ApiProxyException {
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
    videoPlayer.currentTimeProperty().addListener(new InvalidationListener() {
      public void invalidated(Observable ov) {
        updateValues();
      }
    });

    // Bind progress slider with video property to allow for jumping
    progressSlider.valueProperty().addListener(new InvalidationListener() {
      public void invalidated(Observable ov) {
        if (progressSlider.isPressed()) {
          videoPlayer.seek(videoPlayer.getMedia().getDuration().multiply(progressSlider.getValue()/100));
        }
      }
    });
  }

  // Method to update progress slider to follow video
  protected void updateValues() {
    Platform.runLater(new Runnable() {
      public void run() {
        progressSlider.setValue(videoPlayer.getCurrentTime().toMillis() / videoPlayer.getTotalDuration().toMillis() * 100);
      }
    });
  }

  @FXML
  private void handlePlayClick(ActionEvent event) {
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
  private void onMediaError(ActionEvent event) {
    System.out.println("Broken Media");
  }
}

