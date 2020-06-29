package kodex.model;

import java.util.*;

/**
 * 
 */
public abstract class Settings {

    /**
     * Default constructor
     */
    public Settings() {
    }

    /**
     * 
     */
    protected Path propertiesPath;

    /**
     * @param key 
     * @param value
     */
    public void editSetting(String key, String value) {
        // TODO implement here
    }

    /**
     * @param key 
     * @param value
     */
    public void addSetting(String key, String value) {
        // TODO implement here
    }

    /**
     * @param key
     */
    public void removeNonDefaultSetting(String key) {
        // TODO implement here
    }

}