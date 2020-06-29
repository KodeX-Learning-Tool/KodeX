package kodex.model;

import java.util.*;

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
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public List<RessourceBundle> getLanguageList() {
        // TODO implement here
        return null;
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