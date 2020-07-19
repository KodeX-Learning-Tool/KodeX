package kodex.model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import kodex.model.validator.PortNumValidator;

/**
 * This class saves the user's settings. These are saved locally 
 * in a file so that they persist the program. As a singleton, 
 * the user is able to access the settings from anywhere in the program.
 * 
 * @author Patrick Spiesberger
 * @author Leonhard Kraft
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
    private static String defaultPath = null;

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
    	String url = "settings/User_Settings.properties";
    	input = getClass().getResourceAsStream(url);
    	
    	try {
			prop.load(input);
			try {
				setPort(Integer.parseInt(prop.getProperty("port")));
			} catch (NumberFormatException e) {
				setPort(0);
			}
			setDefaultPath(prop.getProperty("defaultPath"));		
			isDarkModeEnabled = !prop.getProperty("isDarkModeEnabled").equals("false");
			
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
     * Returns language (as Locale) currently saved in the properties.
     * 
     * @return current selected language
     */
    public Locale getSavedLanguage() {
        return new Locale(prop.getProperty("local"));
    }

    /**
	 * Sets the desired language
	 * 
     * @param locale : locale of desired language
     */
    public void setSavedLanguage(Locale locale) {
    	prop.setProperty("local", locale.getLanguage()); 
    	storeUserProperties();
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
    	if (!PortNumValidator.getInstance().isValid(input.toString())) {
    		DefaultSettings.port = port;
    	} else {
    		System.out.println("invalid port");
    		return;
    	}
    	prop.setProperty("port", String.valueOf(getPort()));
    	storeUserProperties();
    }

    /**
     * Returns the default path for loading and saving files,
	 * which the user can create via the program
	 * 
     * @return default path
     */
    public String getDefaultPath() {
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
        if (path.isEmpty()) {
        	DefaultSettings.defaultPath = System.getProperty("user.home");
        } else {
            DefaultSettings.defaultPath = path;
        }
    	prop.setProperty("path", defaultPath);
        storeUserProperties();
    }

    /**
     * Sets state of dark mode
     * 
     * @param enable : is darkmode enabled?
     */
    public void setDarkMode(boolean enable) {
        isDarkModeEnabled = enable;
    	prop.setProperty("port", Boolean.toString(enable));
    	storeUserProperties();
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
    	String url = "settings/Default_Settings.properties";
    	input = getClass().getResourceAsStream(url);
    	
    	try {
			prop.load(input);
			
			I18N.setLocale(new Locale(prop.getProperty("local")));
			
			setPort(Integer.parseInt(prop.getProperty("port")));
			
			setDefaultPath(prop.getProperty("defaultPath"));
			
			setDarkMode(!prop.getProperty("isDarkModeEnabled").equals("false"));
			
		} catch (IOException e) {
			System.out.println("Settings can not be loaded");
		}
    }
    
    /*
     * Stores all properties changed by user
     */
    private void storeUserProperties() {
    	try {
			prop.store(new FileOutputStream(getClass().getResource("settings").getPath() + "/User_Settings.properties"), null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}