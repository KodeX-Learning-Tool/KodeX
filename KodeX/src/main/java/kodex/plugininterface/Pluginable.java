package kodex.plugininterface;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

/**
 * This interface must be implemented by every plugin and
 * contains information about the plugin
 * 
 * @author Patrick Spiesberger
 * @author Raimon Gramlich
 * 
 * @version 1.0
 *
 */
public interface Pluginable {

    /**
     * Returns the name of the plugin
     * Note: Make absolutely sure which symbols are allowed in a string
     * @return name of plugin
     */
    public StringProperty pluginNameProperty();

    /**
     * Returns the description of the plugin
     * Note: The description is limited to a string and should be short enough to be displayed in a table
     * @return description of the plugin
     */
    public StringProperty pluginDescriptionProperty();

    /**
     * Returns the state of the plugin
     * Note: Whether the plugin is activated or not
     * @return state of the plugin
     */
    public BooleanProperty activatedProperty();
}