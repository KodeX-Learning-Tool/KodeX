package kodex;

import javafx.scene.control.Alert.AlertType;

public class AlertWindowException extends Exception {
  private static final long serialVersionUID = 1L;
  
  /** The alert type. */
  protected final AlertType type;
  
  /** The alert window title. */
  protected final String title;
  
  /** The alert window header. */
  protected final String header;
  
  /** The alert window content. */
  protected final String content;

  /**
   * Instantiates a new invalid import exception with a specified cause.
   *
   * @param type the alert type
   * @param title the alert window title
   * @param header the alert window header
   * @param content the alert window content
   */
  public AlertWindowException(AlertType type, String title, String header, String content) {
    super(content);
    this.type = type;
    this.title = title;
    this.header = header;
    this.content = content;    
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
  public AlertWindowException(AlertType type, String title, String header, String content,
      Throwable cause) {
    super(content, cause);
    this.type = type;
    this.title = title;
    this.header = header;
    this.content = content;
  }
  
  /**
   * Gets the alert type.
   *
   * @return the type
   */
  public AlertType getType() {
    return type;
  }
  
  /**
   * Gets the alert title.
   *
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Gets the alert header.
   *
   * @return the header
   */
  public String getHeader() {
    return header;
  }

  /**
   * Gets the alert content.
   *
   * @return the content
   */
  public String getContent() {
    return content;
  }
}
