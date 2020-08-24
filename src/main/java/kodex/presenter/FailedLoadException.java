package kodex.presenter;

import javafx.scene.control.Alert.AlertType;
import kodex.exceptions.AlertWindowException;

/**
 * The Class FailedLoadException. This exception is thrown if the program failed loading a file.
 * 
 * @author Raimon Gramlich
 */
public class FailedLoadException extends AlertWindowException {
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new invalid import exception with a specified cause.
   *
   * @param type the alert type
   * @param title the alert window title
   * @param header the alert window header
   * @param content the alert window content
   */
  public FailedLoadException(AlertType type, String title, String header, String content) {
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
  public FailedLoadException(AlertType type, String title, String header, String content,
      Throwable cause) {
    super(type, title, header, content, cause);
  }
}
