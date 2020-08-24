package kodex.pluginutils.model.content;

import kodex.plugininterface.Content;

/**
 * This class holds data in string format. It adds validation and exporting capabilities to Java's
 * String.
 * 
 * @author Yannick Neubert
 * @version 1.0
 */
public abstract class AbstractString extends Content<String> {

  /** The String containing this Contents data. */
  protected String data;

  /**
   * Returns the String containing this Contents data.
   *
   * @return The String containing this Contents data
   */
  public String getString() {
    return data;
  }

  // below some shortcuts for common actions
  public int length() {
    return data.length();
  }

  /**
   * Sets the String containing this Contents data.
   *
   * @param data The String containing this Contents data
   */
  public void setString(String data) {
    if (data == null) {
      throw new IllegalArgumentException();
    }
    
    this.data = data;
  }
}
