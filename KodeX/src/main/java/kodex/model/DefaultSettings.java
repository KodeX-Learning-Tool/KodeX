package kodex.model;

import java.util.*;

/**
 * 
 */
public class DefaultSettings extends Settings {

    /**
     * Default constructor
     */
    public DefaultSettings() {
    }

    /**
     * 
     */
    private static DefaultSettings instance;

    /**
     * 
     */
    private Locale locale;

    /**
     * 
     */
    private int port;

    /**
     * 
     */
    private String defaultPath;

    /**
     * 
     */
    private boolean isDarkModeEnabled;



    /**
     * 
     */
    private void DefaultSettings() {
        // TODO implement here
    }

    /**
     * @return
     */
    public static DefaultSettings getInstance() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Locale getLanguage() {
        // TODO implement here
        return null;
    }

    /**
     * @param locale
     */
    public void setLanguauge(Locale locale) {
        // TODO implement here
    }

    /**
     * @return
     */
    public int getPort() {
        // TODO implement here
        return 0;
    }

    /**
     * @param port
     */
    public void setPort(int port) {
        // TODO implement here
    }

    /**
     * @return
     */
    public String getDefaultPath() {
        // TODO implement here
        return null;
    }

    /**
     * @param path
     */
    public void setDefaultPath(String path) {
        // TODO implement here
    }

    /**
     * @param enable
     */
    public void setDarkMode(boolean enable) {
        // TODO implement here
    }

    /**
     * @return
     */
    public boolean isDarkMode() {
        // TODO implement here
        return false;
    }

    /**
     * 
     */
    public void reset() {
        // TODO implement here
    }

}