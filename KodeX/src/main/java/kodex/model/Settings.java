package kodex.model;

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
    protected String propertiesPath;

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