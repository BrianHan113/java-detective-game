package nz.ac.auckland.se206;

import javafx.fxml.FXML;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;

public interface Controller {
  @FXML
  public void initialize() throws ApiProxyException;
}
