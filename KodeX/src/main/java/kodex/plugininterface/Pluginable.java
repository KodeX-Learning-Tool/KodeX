package kodex.plugininterface;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

/**
 * This interface must be implemented by every plugin and
 * contains information about the plugin
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 *
 */
public interface Pluginable {

    /**
     * Returns the name of the plugin
     * @return name of plugin
     * Note: Make absolutely sure which symbols are allowed in a string
     */
    public StringProperty getPluginName();

    /**
     * Returns the category of the plugin
     * @return category of plugin
     * Note: The category is limited to a string and should be uniform
     */
    public StringProperty getPluginDescription();

    public BooleanProperty activatedProperty();
}