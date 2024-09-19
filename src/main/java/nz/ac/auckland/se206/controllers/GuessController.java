package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionRequest;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionResult;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.chat.openai.Choice;
import nz.ac.auckland.apiproxy.config.ApiProxyConfig;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.AudioPlayerManager;
import nz.ac.auckland.se206.Controller;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.TimeManager;
import nz.ac.auckland.se206.prompts.PromptEngineering;

public class GuessController implements Controller {

  @FXML private Label timerLabel;
  @FXML private Label selectLabel;
  @FXML private Rectangle exwifeRect;
  @FXML private Rectangle sonRect;
  @FXML private Rectangle friendRect;
  @FXML private TextArea txtInput;
  @FXML private Button submitButton;

  private static boolean timeOver = false;

  private ChatCompletionRequest chatCompletionRequest;
  private ChatMessage feedback;
  private FeedbackController feedbackController;
  private String suspectName;
  private int minute;
  private int second;
  private Timeline timeline;
  private TimeManager timeManager = TimeManager.getInstance();
  private AudioPlayerManager audioPlayer = AudioPlayerManager.getInstance();

  /**
   * Initializes the chat view.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() {
    Map<String, String> map = new HashMap<>();
    try {
      ApiProxyConfig config = ApiProxyConfig.readConfig();
      chatCompletionRequest =
          new ChatCompletionRequest(config)
              .setN(1)
              .setTemperature(0.2)
              .setTopP(0.5)
              .setMaxTokens(400);
      runGpt(new ChatMessage("system", getPrompt(map)));
    } catch (ApiProxyException e) {
      e.printStackTrace();
    }
    // Bind <Enter> key to sendButton
    txtInput.setOnKeyPressed(
        event -> {
          if (event.getCode() == KeyCode.ENTER) {
            submitButton.fire();
          }
        });

    timerLabel.setText(timeManager.formatTime());
    decrementTime();
  }

  private void decrementTime() {
    timeline =
        new Timeline(
            new KeyFrame(
                Duration.millis(1),
                e -> {
                  try {
                    updateTimerLabel();
                  } catch (IOException e1) {
                    e1.printStackTrace();
                  }
                }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  private void updateTimerLabel() throws IOException {
    minute = timeManager.getMinute();
    second = timeManager.getSecond();
    if (!timeOver && minute == 0 && second == 0) {
      timerLabel.setText("Time's Up!");
      FeedbackController feedbackController =
          (FeedbackController) SceneManager.getController(AppUi.FEEDBACK);
      feedbackController.getWonLostLbl().setText("YOU LOST");
      audioPlayer.playAudio("/announcer/lost.mp3");
      feedbackController.getFeedbackStatusLbl().setText("You ran out of Time");
      feedbackController
          .getFeedbackTextArea()
          .setText(
              "Try be a little faster next time! Top-notch detectives need to be able to think"
                  + " fast!");
      timeOver = true;
      App.setRoot(SceneManager.getUiRoot(AppUi.FEEDBACK));
    } else {
      timerLabel.setText(String.format("%02d:%02d", minute, second));
    }
  }

  /**
   * Generates the system prompt based on the profession.
   *
   * @return the system prompt string
   */
  private String getPrompt(Map<String, String> map) {
    return PromptEngineering.getPrompt("feedback.txt", map);
  }

  /**
   * Runs the GPT model with a given chat message.
   *
   * @param msg the chat message to process
   * @return the response chat message
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  private void runGpt(ChatMessage msg) throws ApiProxyException {
    chatCompletionRequest.addMessage(msg);

    Task<Void> gptTask =
        new Task<Void>() {
          @Override
          protected Void call() {
            try {
              // Disable button so another request cannot be sent
              submitButton.setDisable(true);

              ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
              Choice result = chatCompletionResult.getChoices().iterator().next();
              chatCompletionRequest.addMessage(result.getChatMessage());

              Platform.runLater(
                  () -> {
                    feedbackController =
                        (FeedbackController) SceneManager.getController(AppUi.FEEDBACK);
                    submitButton.setDisable(false);
                    feedback = result.getChatMessage();
                    if (msg.getRole() == "user") {
                      feedbackController.displayFeedback(feedback.getContent());
                      feedbackController.enableBackButton();
                    }
                  });
            } catch (ApiProxyException e) {
              e.printStackTrace();
            }
            return null;
          }
        };

    Thread bgThread = new Thread(gptTask);
    bgThread.start();
  }

  /**
   * Sends a message to the GPT model.
   *
   * @param event the action event triggered by the send button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onSendMessage(ActionEvent event) throws ApiProxyException, IOException {
    if (suspectName == null) {
      selectLabel.setText("Please click on who you think it is.");
      audioPlayer.playAudio("/announcer/select_suspect.mp3");
      return;
    }

    String message = txtInput.getText().trim();
    if (message.isEmpty()) {
      audioPlayer.playAudio("/announcer/explain_choice.mp3");
      return;
    }
    txtInput.clear();
    Map<String, String> map = new HashMap<>();
    map.put("suspect", suspectName);
    map.put("fingerprint", ""); // Temp blank
    map.put("security_footage", "");
    map.put("shoe", "");

    String submitPrefix = PromptEngineering.getPrompt("guess.txt", map);
    ChatMessage msg = new ChatMessage("user", submitPrefix + message);
    runGpt(msg);

    FeedbackController feedbackController =
        (FeedbackController) SceneManager.getController(AppUi.FEEDBACK);

    if (suspectName.equals("Ex-Wife")) {
      feedbackController.getWonLostLbl().setText("YOU WON!");
      audioPlayer.playAudio("/announcer/won.mp3");
      timeOver = true;
    } else {
      feedbackController.getWonLostLbl().setText("YOU LOST");
      audioPlayer.playAudio("/announcer/lost.mp3");
      timeOver = true;
    }

    App.setRoot(SceneManager.getUiRoot(AppUi.FEEDBACK));
    timeManager.stop();
    timeManager.resetTimer(5);
    timerLabel.setText(timeManager.formatTime());
  }

  @FXML
  private void handleRectangleClick(MouseEvent event) {
    Rectangle clickedRectangle = (Rectangle) event.getSource();
    switch (clickedRectangle.getId()) {
      case "exwifeRect":
        suspectName = "Ex-Wife";
        break;
      case "friendRect":
        suspectName = "Friend";
        break;
      case "sonRect":
        suspectName = "Son";
        break;
      default:
        System.out.println("Invalid Rectangle");
        return;
    }
    selectLabel.setText(suspectName);
  }
}
