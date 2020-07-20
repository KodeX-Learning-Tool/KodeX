package kodex.model;

/**
 * This class is an interface for your own settings.
 * Please overwrite this methods and create a new
 * instance of Settings in MainApp.
 * 
 * We do not use our own interface to keep the interface 
 * as expandable as possible and therefore do not want 
 * to design this for our settings.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 *
 */
public abstract class Settings {


	/*
	 * The path of the property file, which contains the information
	 * for the settings holds.
	 */
    protected String propertiesPath;

    /**
     * Sets the value of a setting option	
     * @param key : the key of the setting option
     * @param value : the value to be set
     */
    public void editSetting(String key, String value) {
        // TODO overwrite me!
    }

    /**
     * Adds a new setting option with a standard value
     * @param key : the key of the setting option
     * @param value : the value to be set
     */
    public void addSetting(String key, String value) {
        // TODO overwrite me!
    }

    /**
     * removes a setting option
     * @param key : the key of the setting option
     * 
     */
    public void removeSetting(String key) {
        // TODO overwrite me!
    }

}