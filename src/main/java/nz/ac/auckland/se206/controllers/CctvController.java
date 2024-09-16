package nz.ac.auckland.se206.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Circle;

public class CctvController {

    @FXML
    private BorderPane MediaPlayer;

    @FXML
    private Label evidenceLabel;

    @FXML
    private Circle exitCircle;

    @FXML
    private Label fingerprintHideLabel;

    @FXML
    private Label fingerprintLabel;

    @FXML
    private MediaView mediaView;

    @FXML
    private Button pauseButton;

    @FXML
    private Button playButton;

    @FXML
    private Slider progressSlider;

    @FXML
    private Label shoeprintHideLabel;

    @FXML
    private Label shoeprintLabel;

    @FXML
    private Label timerLabel;

    @FXML
    void handlePauseClick(ActionEvent event) {

    }

    @FXML
    void handlePlayClick(ActionEvent event) {

    }

}

