package kodex.model;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * This class provides the values for text in the GUI in the selected language.
 * A presenter calls getMessage with the desired message and gets the result
 * back in the correct language.
 * Because it is needed everywhere, this class is a singleton.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 *
 */
public class Language {

    /* current instance of language */
    private static Language instance;

    /* list with all avaliable languages */
    private static List<Locale> languages;
    
    /* locale of current language */
    private static Locale language;

    /* File with current language*/
    private File currentLanguageFile;
    
    /* bundle of files with current language */
    private static ResourceBundle rb;

    /**
     * Constructor of the Language class. 
     * However, since this class is a singleton, only one instance can be created
     */
    private Language(Locale language) {
    		ClassLoader loader = null;
			try {
				loader = new URLClassLoader(new URL[] { new File("./src/main/resources/").toURI().toURL() });
			} catch (MalformedURLException e) {
				System.out.println("Path can not be loaded");
			}
			if (loader != null) {
				rb = ResourceBundle.getBundle("Language", language, loader);
			} else {
				rb = ResourceBundle.getBundle("Language", Locale.GERMAN);
			}
			Language.language = language;
    }

	/**
     * Provides the singleton instance of this class.
     * The presenter can request the desired message directly from this
     * 
     * @return instance of this class
     */
    public static Language getInstance() {
    	if (instance == null) {
    		instance = new Language(new Locale("DE"));
    	}
        return instance;
    }
    
    /**
     * Sets the singleton instance of this class.
     * 
     * @param locale : corresponding language
     */
    public static void setInstance(Locale locale) {
        instance = new Language(locale);
    }
    

    /**
     * returns a list of al available languages
     * @return list of type Locale
     */
    public static List<Locale> getLanguageList() {
        return languages;
    }

    /**
     * This method is called with the desired message in order to 
     * receive it in the selected language
     * @param message : Message that is required in the corresponding language
     * @return message in the current language
     */
    public static String getMessage(String message) {
    	if (instance == null) {
    		instance = new Language(new Locale("DE"));
    	}
    	return rb.getString(message);
    }

    /**
     * Returns information about current language
     * @return : locale of current language (e.g. Deutsch = DE)
     */
    public static Locale getLanguageInfo() {
        if (language == null) {
        	instance = new Language(new Locale("DE"));
        }
        return language;
    }

    /**
     * refreshes the list of current language
     */
    public static void refreshList() {
        // TODO implement here
    }

}