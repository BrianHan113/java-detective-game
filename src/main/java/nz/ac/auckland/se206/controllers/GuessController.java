package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionRequest;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionResult;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.chat.openai.Choice;
import nz.ac.auckland.apiproxy.config.ApiProxyConfig;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.prompts.PromptEngineering;

public class GuessController {

  @FXML private Label timerLabel;
  @FXML private Label selectLabel;
  @FXML private Rectangle exwifeRect;
  @FXML private Rectangle sonRect;
  @FXML private Rectangle friendRect;
  @FXML private TextField txtInput;
  @FXML private Button submitButton;

  private ChatCompletionRequest chatCompletionRequest;
  private String suspectName;

  /**
   * Initializes the chat view.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() throws ApiProxyException {
    Map<String, String> map = new HashMap<>();
    try {
      ApiProxyConfig config = ApiProxyConfig.readConfig();
      chatCompletionRequest =
          new ChatCompletionRequest(config)
              .setN(1)
              .setTemperature(0.2)
              .setTopP(0.5)
              .setMaxTokens(100);
      runGpt(new ChatMessage("system", getPrompt(map)));
    } catch (ApiProxyException e) {
      e.printStackTrace();
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
  private ChatMessage runGpt(ChatMessage msg) throws ApiProxyException {
    chatCompletionRequest.addMessage(msg);
    try {
      ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
      Choice result = chatCompletionResult.getChoices().iterator().next();
      chatCompletionRequest.addMessage(result.getChatMessage());
      System.out.println(result.getChatMessage().getContent());
      return result.getChatMessage();
    } catch (ApiProxyException e) {
      e.printStackTrace();
      return null;
    }
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
    String message = txtInput.getText().trim();
    if (message.isEmpty()) {
      return;
    }
    txtInput.clear();
    Map<String, String> map = new HashMap<>();
    map.put("suspect", suspectName);
    String submitPrefix = PromptEngineering.getPrompt("guess.txt", map);
    ChatMessage msg = new ChatMessage("user", submitPrefix + message);
    System.out.println(msg.getContent());
    runGpt(msg);
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
