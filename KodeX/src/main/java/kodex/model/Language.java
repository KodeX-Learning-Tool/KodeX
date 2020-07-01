package kodex.model;

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;
/**
 * 
 */
public class Language {

    /**
     * Default constructor
     */
    public Language() {
    }

    /**
     * 
     */
    private static Language instance;

    /**
     * 
     */
    private List<ResourceBundle> languages;

    /**
     * 
     */
    private File currentLanguageFile;



    /**
     * 
     */
    private void Language() {
        // TODO implement here
    }

    /**
     * @return
     */
    public static Language getInstance() {
        return instance;
    }

    /**
     * @return
     */
    public List<ResourceBundle> getLanguageList() {
        // TODO implement here
        return languages;
    }

    /**
     * @param message 
     * @return
     */
    public String getMessage(String message) {
        // TODO implement here
        return "";
    }

    /**
     * @return
     */
    public String getLanguageInfo() {
        // TODO implement here
        return "";
    }

    /**
     * 
     */
    public void refreshList() {
        // TODO implement here
    }

}