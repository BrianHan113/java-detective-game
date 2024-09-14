package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionRequest;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionResult;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.chat.openai.Choice;
import nz.ac.auckland.apiproxy.config.ApiProxyConfig;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.prompts.PromptEngineering;
import nz.ac.auckland.se206.speech.FreeTextToSpeech;

public class ExwifeController {

  @FXML private Button sendButton;
  @FXML private TextArea txtArea;
  @FXML private TextField txtInput;
  @FXML private Label timerLabel;
  @FXML private Rectangle crimeSceneRect;
  @FXML private Rectangle sonRect;
  @FXML private Rectangle friendRect;

  private ChatCompletionRequest chatCompletionRequest;
  private String role;
  private String suspectName = "Ex-Wife";

  /**
   * Initializes the chat view.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() throws ApiProxyException {
    System.out.println("Initialized Ex-wife Chat: "+this);
    // Bind <Enter> key to sendButton
    txtInput.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        sendButton.fire();
      }
    });
  }

  /**
   * Generates the system prompt based on the role.
   *
   * @return the system prompt string
   */
  private String getSystemPrompt() {
    Map<String, String> map = new HashMap<>();
    map.put("role", role);
    return PromptEngineering.getPrompt("chat.txt", map);
  }

  /**
   * Sets the role for the chat context and initializes the ChatCompletionRequest.
   *
   * @param role the role to set
   */
  public void setRole(String role) {
    this.role = role;
    System.out.println(this);
    try {
      ApiProxyConfig config = ApiProxyConfig.readConfig();
      chatCompletionRequest =
          new ChatCompletionRequest(config)
              .setN(1)
              .setTemperature(0.2)
              .setTopP(0.5)
              .setMaxTokens(100);
      runGpt(new ChatMessage("system", getSystemPrompt()));
    } catch (ApiProxyException e) {
      e.printStackTrace();
    }
  }

  /**
   * Appends a chat message to the chat text area.
   *
   * @param msg the chat message to append
   */
  private void appendChatMessage(ChatMessage msg) {
    txtArea.appendText(msg.getRole() + ": " + msg.getContent() + "\n\n");
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

    Task<Void> gptTask = new Task<Void>() {
      @Override
      protected Void call() {
        try {
          //Disable button so another request cannot be sent
          sendButton.setDisable(true);

          ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
          Choice result = chatCompletionResult.getChoices().iterator().next();
          chatCompletionRequest.addMessage(result.getChatMessage());

          // Create temp message to change role name
          ChatMessage tempMsg = new ChatMessage(suspectName, result.getChatMessage().getContent());

          Platform.runLater(() -> {
            // Append text and ren-enable button on task complete
            appendChatMessage(tempMsg);
            sendButton.setDisable(false);
            FreeTextToSpeech.speak(result.getChatMessage().getContent());
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
    String message = txtInput.getText().trim();
    if (message.isEmpty()) {
      return;
    }
    txtInput.clear();

    // Create tempMsg to change role name
    ChatMessage tempMsg = new ChatMessage("Detective", message);
    appendChatMessage(tempMsg);
    ChatMessage msg = new ChatMessage("user", message);
    runGpt(msg);
  }
}
