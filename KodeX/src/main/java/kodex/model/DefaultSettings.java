package kodex.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * This class saves the user's settings. These are saved locally 
 * in a file so that they persist the program. As a singleton, 
 * the user is able to access the settings from anywhere in the program.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 *
 */
public class DefaultSettings extends Settings {
	

    /* current instance of DefaultSettings */
    private static DefaultSettings instance;

    /* current network port */
    private static int port;

    /* current Path where files are stored */
    private static String defaultPath;

    /* current state of darkmode */
    private static boolean isDarkModeEnabled;
    
    /* instance of property file */
    private static Properties prop = new Properties();
    
    /* nessesary to read the property file */
    private InputStream input = null;



    /**
     * Constructor of the DefaultSettings class. 
     * However, since this class is a singleton, only one instance can be created
     */
    private DefaultSettings() {
    	input = getClass().getClassLoader().getResourceAsStream("User_Settings.properties");
    	try {
			prop.load(input);
			port = Integer.parseInt(prop.getProperty("port"));
			defaultPath = prop.getProperty("defaultPath");
			
			int darkmode = Integer.parseInt(prop.getProperty("isDarkModeEnabled"));
			if (darkmode == 0) isDarkModeEnabled = false;
			else isDarkModeEnabled = true;
			
		} catch (IOException e) {
			System.out.println("Settings can not be loaded");
		}
    }

    /**
     * Provides the singleton instance of this class.
     * The presenter can request the settings directly from this
     * 
     * @return instance of this class
     */
    public static DefaultSettings getInstance() {
    	if (instance == null) {
    		instance = new DefaultSettings();
    	}
        return instance;
    }

    /**
     * Returns current selected language
     * 
     * @return current selected language
     */
    public Language getLanguage() {
        if (Language.getInstance() == null) {
        	setLanguauge(new Locale("DE"));
        }
        return Language.getInstance();
    }

    /**
	 * Sets the desired language
	 * 
     * @param locale : locale of desired language
     */
    public void setLanguauge(Locale locale) {
        Language.setInstance(locale);  
    }

    /**
     * Returns port of local network
     * 
     * @return port of local
     */
    public static int getPort() {
        return port;
    }

    /**
     * Sets port of local network
     * 
     * @param port : port of local network
     */
    public void setPort(int port) {
        DefaultSettings.port = port;
    }

    /**
     * Returns the default path for loading and saving files,
	 * which the user can create via the program
	 * 
     * @return default path
     */
    public static String getDefaultPath() {
    	if (defaultPath == null) {
    		defaultPath = System.getProperty("user.home");
    	}
        return defaultPath;
    }

    /**
     * Sets the default path for loading and saving files,
	 * which the user can create via the program
	 * 
     * @param path : desired default path
     */
    public void setDefaultPath(String path) {
        DefaultSettings.defaultPath = path;
    }

    /**
     * Sets state of dark mode
     * 
     * @param enable : is darkmode enabled?
     */
    public void setDarkMode(boolean enable) {
        isDarkModeEnabled = enable;
    }

    /**
     * Returns state of dark mode
     * 
     * @return state of dark mode
     */
    public boolean isDarkMode() {
        return isDarkModeEnabled;
    }

    /**
     * Resets all settings
     */
    public void reset() {
    	input = getClass().getClassLoader().getResourceAsStream("Default_Settings.properties");
    	try {
			prop.load(input);
			port = Integer.parseInt(prop.getProperty("port"));
			defaultPath = prop.getProperty("defaultPath");
			
			int darkmode = Integer.parseInt(prop.getProperty("isDarkModeEnabled"));
			if (darkmode == 0) isDarkModeEnabled = false;
			else isDarkModeEnabled = true;
			
		} catch (IOException e) {
			System.out.println("Settings can not be loaded");
		}
    }

}