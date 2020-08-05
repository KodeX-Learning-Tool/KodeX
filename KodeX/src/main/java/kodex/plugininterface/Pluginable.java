package kodex.plugininterface;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

/**
 * This interface must be implemented by every plugin and contains information about the plugin.
 *
 * @author Patrick Spiesberger
 * @author Raimon Gramlich
 * @version 1.0
 */
public abstract class Pluginable {

  /**
   * Returns the state of the plugin Note: Whether the plugin is activated or not.
   *
   * @return state of the plugin
   */
  public abstract BooleanProperty activatedProperty();

  /**
   * Returns the description of the plugin Note: The description is limited to a string and should
   * be short enough to be displayed in a table.
   *
   * @return description of the plugin
   */
  public abstract StringProperty pluginDescriptionProperty();

  /**
   * Returns the unique name of the plugin Note: Make absolutely sure which symbols are allowed in a
   * string.
   *
   * @return name of plugin
   */
  public abstract StringProperty pluginNameProperty();
  
  @Override
  public boolean equals(Object v) {
    boolean retVal = false;
    
    // use the plugin name for comparing two plugins
    if (v instanceof Pluginable) {
      Pluginable ptr = (Pluginable) v;
      retVal = ptr.pluginNameProperty().get().equals(this.pluginNameProperty().get());
    }

    return retVal;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 17 * hash
        + (this.pluginNameProperty().get() != null ? this.pluginNameProperty().get().hashCode()
            : 0);
    return hash;
  }
}
