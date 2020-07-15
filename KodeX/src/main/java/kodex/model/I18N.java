package kodex.model;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileAlreadyExistsException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * I18N utility class..
 * 
 * This class is inspired by:
 * 
 * https://www.sothawo.com/2016/09/how-to-implement-a-javafx-ui-where-the-language-can-be-changed-dynamically/
 * 
 * @author Leonhard Kraft
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 */
public class I18N {

    /** the current selected Locale. */
    private static final ObjectProperty<Locale> locale;

    private static final List<Locale> supportedLocales = new ArrayList<>();

    private static final String LANGUAGE_FILE_NAME = "Languages";

    private static final String LANGUAGE_FOLDER_NAME = "languages";

    private static final String DEFAULT_LOCALE = "en";

    private static final int VALID_NAME_PART_NUMBER = 2;

    private static final int LANGUAGE_PREFIX = 0;

    private static final int LANGUAGE_SUFIX = 1;
    
    private static URLClassLoader loader = null;
    
    private I18N() {}
    
    static {
        
        try {
            loadSupportedLocales();
        } catch (FileAlreadyExistsException | FileNotFoundException e) {
            e.printStackTrace();
        }
        
        try {
            loader = new URLClassLoader(new URL[] { new File(I18N.class.getResource(LANGUAGE_FOLDER_NAME).getPath()).toURI().toURL() });
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // set language loaded by the settings
        locale = new SimpleObjectProperty<>(DefaultSettings.getInstance().getSavedLanguage());

        // change the locale for this instance of the JVM
        locale.addListener((observable, oldValue, newValue) -> Locale.setDefault(newValue));
    }

    private static void loadSupportedLocales() throws FileNotFoundException, FileAlreadyExistsException {

        // File to the languages folder directory
        File languageDir = new File(I18N.class.getResource(LANGUAGE_FOLDER_NAME).getPath());

        /*
         * Languages are only contained in property files.
         */
        FileFilter propertyFilter = file -> file.getPath().toLowerCase().endsWith(".properties");

        File[] languageFiles = languageDir.listFiles(propertyFilter);

        if (languageFiles.length <= 0) {
            throw new FileNotFoundException("No language file has been found.");
        }

        String fileName;
        String[] fileNameParts;
        Locale fileLocale;
        boolean defaultFound = false;
        int exPos;

        /*
         * Get Locale from all available files.
         */
        for (File propertyFile : languageFiles) {

            fileName = propertyFile.getName();

            exPos = fileName.lastIndexOf(".");

            // strip file name of file extension
            fileName = fileName.substring(0, exPos);
            
            if (fileName.equals(LANGUAGE_FILE_NAME)) {
                
                if (defaultFound) {
                    throw new FileAlreadyExistsException(
                            "Language default property file " +
                             fileName +
                            "is not unique.");
                }
                
                defaultFound = true;
                continue;
            }

            fileNameParts = fileName.split("_");

            if (fileNameParts.length != VALID_NAME_PART_NUMBER) {
                System.err.println("Please check name of File: " + fileName);
                continue;
            }

            if (!fileNameParts[LANGUAGE_PREFIX].equals(LANGUAGE_FILE_NAME)) {
                // not a Language file
                continue;
            }

            fileLocale = new Locale(fileNameParts[LANGUAGE_SUFIX]);

            if (supportedLocales.contains(fileLocale)) {
                
                // there should only be one language property file of each language
                throw new FileAlreadyExistsException(
                        "Language property file for language " +
                         fileLocale.getDisplayLanguage() +
                         "is not unique.");
            }

            supportedLocales.add(fileLocale);
        }

        if (supportedLocales.isEmpty()) {
            // property files have been found, but no language files
            throw new FileNotFoundException("No valid language file has been found.");
        }
    }

    /**
     * Get a list of the supported Locales.
     *
     * @return List of Locale objects.
     */
    public static List<Locale> getSupportedLocales() {

        return supportedLocales;
    }

    /**
     * Get the default locale. This is the systems default if contained in the
     * supported Locales, english otherwise. Should be used when language property
     * is empty.
     *
     * @return The default Loacle.
     */
    public static Locale getDefaultLocale() {
        Locale sysDefault = Locale.getDefault();
        return getSupportedLocales().contains(sysDefault) ? sysDefault : new Locale(DEFAULT_LOCALE);
    }

    /**
     * Get the current Locale.
     * 
     * @return The current Locale.
     */
    public static Locale getLocale() {
        return locale.get();
    }

    /**
     * Set new current Locale.
     * 
     * @param locale The new current Locale.
     */
    public static void setLocale(Locale locale) {
        localeProperty().set(locale);
        Locale.setDefault(locale);
    }

    /**
     * Get the property of the current Locale.
     * 
     * @return The Locale property.
     */
    public static ObjectProperty<Locale> localeProperty() {
        return locale;
    }

    /**
     * Gets the string with the given key from the resource bundle for the current
     * locale and uses it as first argument to MessageFormat.format, passing in the
     * optional args and returning the result.
     *
     * @param key  The key used to get the string from the Property file.
     * @param args Optional arguments for the retrieved message.
     * @return Localized and formatted string.
     */
    public static String get(final String key, final Object... args) {
        ResourceBundle bundle = ResourceBundle.getBundle(LANGUAGE_FILE_NAME, getLocale(), loader);
        return MessageFormat.format(bundle.getString(key), args);
    }

    /**
     * Creates a String binding to a localized String for the given resource bundle
     * key.
     *
     * @param key The key used to get the string from the Property file.
     * @return String binding A String Binding to the current locale.
     */
    public static StringBinding createStringBinding(final String key, Object... args) {
        return Bindings.createStringBinding(() -> get(key, args), locale);
    }

    /**
     * Creates a String Binding to a localized String that is computed by calling
     * the given func.
     *
     * @param func The function called on every change.
     * @return StringBinding A String Binding to the current locale.
     */
    public static StringBinding createStringBinding(Callable<String> func) {
        return Bindings.createStringBinding(func, locale);
    }
}
