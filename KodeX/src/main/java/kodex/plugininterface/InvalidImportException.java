package kodex.plugininterface;

import javafx.scene.control.Alert.AlertType;
import kodex.exceptions.AlertWindowException;

/**
 * The Class InvalidImportException. This Exception is thrown if the import is invalid.
 */
public class InvalidImportException extends AlertWindowException {
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new invalid import exception with a specified cause.
   *
   * @param type the alert type
   * @param title the alert window title
   * @param header the alert window header
   * @param content the alert window content
   */
  public InvalidImportException(AlertType type, String title, String header, String content) {
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
  public InvalidImportException(AlertType type, String title, String header, String content,
      Throwable cause) {
    super(type, title, header, content, cause);
  }
}
