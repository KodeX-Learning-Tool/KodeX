package kodex.exceptions;

import javafx.scene.control.Alert.AlertType;

/**
 * Exception to be thrown when loading of something failed.
 *
 * @author Leonhard Kraft
 */
public class LoadingException extends AlertWindowException {
  private static final long serialVersionUID = 1L;
  
  /**
   * Instantiates a new invalid loading exception without a specified cause.
   *
   * @param type the alert type
   * @param title the alert window title
   * @param header the alert window header
   * @param content the alert window content
   */
  public LoadingException(AlertType type, String title, String header, String content) {
    super(type, title, header, content);
  }

  /**
   * Instantiates a new invalid loading exception with a specified cause.
   *
   * @param type the alert type
   * @param title the alert window title
   * @param header the alert window header
   * @param content the alert window content
   * @param cause the cause
   */
  public LoadingException(
      AlertType type, String title, String header, String content, Throwable cause) {
    super(type, title, header, content, cause);
  }
}
