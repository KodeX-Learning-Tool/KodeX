package kodex.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Properties;


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
    private List<Locale> languages;

    /* File with current language*/
    private File currentLanguageFile;
    
    /* instance of property file */
    private Properties prop = new Properties();
    
    /* nessesary to read the property file */
    private InputStream input = null;

    /**
     * Constructor of the Language class. 
     * However, since this class is a singleton, only one instance can be created
     */
    private Language(Locale language) {
    	currentLanguageFile = new File("Language_" + language + ".properties");
    	input = getClass().getClassLoader().getResourceAsStream(currentLanguageFile.toString());
    	try {
			prop.load(input);
			instance = this;
		} catch (IOException e) {
			System.out.println("Language can not be selected");
		}
    }

    /**
     * Provides the singleton instance of this class.
     * The presenter can request the desired message directly from this
     * 
     * @return instance of this class
     */
    public static Language getInstance() {
        return instance;
    }

    /**
     * returns a list of al available languages
     * @return list of type Locale
     */
    public List<Locale> getLanguageList() {
        return languages;
    }

    /**
     * This method is called with the desired message in order to 
     * receive it in the selected language
     * @param message : Message that is required in the corresponding language
     * @return message in the current language
     */
    public String getMessage(String message) {
        return prop.getProperty(message);
    }

    /**
     * Returns information about current language
     * @return : name and domain of current language (e.g. Deutsch : DE)
     */
    public String getLanguageInfo() {
        return prop.getProperty("Language") + " : " + prop.getProperty("Domain");
    }

    /**
     * refreshes the list of current language
     */
    public void refreshList() {
        // TODO implement here
    }

}