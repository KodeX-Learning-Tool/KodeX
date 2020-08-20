package kodex.exceptions;

import javafx.scene.control.Alert.AlertType;

/**
 * The Class InvalidInputException. This Exception is thrown if the user input is invalid.
 * 
 * @author Raimon Gramlich
 */
public class InvalidInputException extends AlertWindowException {
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new invalid import exception without a specified cause.
   *
   * @param type the alert type
   * @param title the alert window title
   * @param header the alert window header
   * @param content the alert window content
   */
  public InvalidInputException(AlertType type, String title, String header, String content) {
    super(type, title, header, content);
  }
  
  /**
   * Instantiates a new invalid import exception with a specified cause.
   *
   * @param type the alert type
   * @param title the alert window title
   * @param header the alert window header
   * @param content the alert window content
   * @param cause the cause
   */
  public InvalidInputException(AlertType type, String title, String header, String content,
      Throwable cause) {
    super(type, title, header, content, cause);
  }
}
