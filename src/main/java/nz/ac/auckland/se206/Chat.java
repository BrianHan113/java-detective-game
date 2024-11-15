package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionRequest;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionResult;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.chat.openai.Choice;
import nz.ac.auckland.apiproxy.config.ApiProxyConfig;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.controllers.ExwifeController;
import nz.ac.auckland.se206.controllers.FriendController;
import nz.ac.auckland.se206.controllers.SonController;
import nz.ac.auckland.se206.prompts.PromptEngineering;

/**
 * Abstract class for the chat scene. This class implements the Controller interface. This class is
 * responsible for handling the chat scene, including the chat text area, input field and send
 * button. This class is responsible for handling features in the chat scene.
 */
public abstract class Chat implements Controller {
  private ChatCompletionRequest chatCompletionRequest;
  private String role;
  private String promptFilename;
  private String suspectName;
  private InteractionManager interact = InteractionManager.getInstance();
  private String thinkingText;

  @FXML private Button sendButton;
  @FXML private TextArea txtArea;
  @FXML private TextField txtInput;

  /**
   * Generates the system prompt based on the role.
   *
   * @return the system prompt string
   */
  private String getSystemPrompt() {
    Map<String, String> map = new HashMap<>();
    map.put("role", role);
    return PromptEngineering.getPrompt(promptFilename, map);
  }

  /**
   * Sets the role for the chat context and initializes the ChatCompletionRequest.
   *
   * @param role the role to set
   */
  public void setRole(String role, String promptFilename) {
    this.role = role;
    this.promptFilename = promptFilename;
    this.suspectName = role; // Add suspectName param when names are given
    this.thinkingText = suspectName + " is thinking ...";
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

    Task<Void> gptTask =
        new Task<Void>() {
          @Override
          protected Void call() {
            try {
              // Disable button so another request cannot be sent
              sendButton.setDisable(true);
              txtArea.appendText(thinkingText);

              ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
              Choice result = chatCompletionResult.getChoices().iterator().next();
              chatCompletionRequest.addMessage(result.getChatMessage());

              // Create temp message to change role name
              ChatMessage tempMsg =
                  new ChatMessage(suspectName, result.getChatMessage().getContent());

              Platform.runLater(
                  () -> {
                    txtArea.clear();
                    // Append text and re-enable button on task complete
                    appendChatMessage(tempMsg);
                    sendButton.setDisable(false);
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

    // Dont let user send blank text
    if (message.isEmpty()) {
      return;
    }

    // Play filler voiceline to hide chat delay
    Voicelines.fillerVoiceLines(this.role);

    txtInput.clear();

    // User has interacted/chatted with suspect
    switch (role) {
      case "Friend":
        interact.setInteractFriend(true);
        break;
      case "Son":
        interact.setInteractSon(true);
        break;
      case "Ex-Wife":
        interact.setInteractExwife(true);
        break;
      default:
        break;
    }
    ChatMessage msg = new ChatMessage("user", message);
    runGpt(msg);
  }

  @FXML
  private void handleRectangleClick(MouseEvent event) throws IOException {

    // Find fxid of clicked object
    Rectangle shape = (Rectangle) event.getSource();
    String shapeId = shape.getId();

    // Handle map clicks
    switch (shapeId) {
      case "wifePinRect":
        App.setRoot(SceneManager.getUiRoot(AppUi.EX_WIFE));
        ((ExwifeController) SceneManager.getController(AppUi.EX_WIFE)).requestInputFocus();
        if (!InteractionManager.isVisitExWife()) {
          Voicelines.introVoiceLines("Ex-Wife");
          InteractionManager.setVisitExWife(true);
        }
        break;
      case "friendPinRect":
        App.setRoot(SceneManager.getUiRoot(AppUi.FRIEND));
        ((FriendController) SceneManager.getController(AppUi.FRIEND)).requestInputFocus();
        if (!InteractionManager.isVisitFriend()) {
          Voicelines.introVoiceLines("Friend");
          InteractionManager.setVisitFriend(true);
        }
        break;
      case "sonPinRect":
        App.setRoot(SceneManager.getUiRoot(AppUi.SON));
        ((SonController) SceneManager.getController(AppUi.SON)).requestInputFocus();
        if (!InteractionManager.isVisitSon()) {
          Voicelines.introVoiceLines("Son");
          InteractionManager.setVisitSon(true);
        }
        break;
      case "crimeScenePinRect":
        App.setRoot(SceneManager.getUiRoot(AppUi.CRIME_SCENE));
        break;

      default:
        break;
    }
  }

  public void requestInputFocus() {
    txtInput.requestFocus();
  }

  @FXML
  private void handleSendBtnEnter() {
    sendButton.setStyle("-fx-background-color: #6f7c89;");
  }

  @FXML
  private void handleSendBtnExit() {
    sendButton.setStyle("-fx-background-color: #BBD1E8;");
  }
}
