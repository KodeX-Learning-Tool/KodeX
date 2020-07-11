package kodex.model;

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
 * 
 * @version 1.0
 */
public class I18N {
    
    /** the current selected Locale. */
    private static final ObjectProperty<Locale> locale;
    
    static {
        //set language loaded by the settings
        locale = new SimpleObjectProperty<>(DefaultSettings.getInstance().getLanguage());
        
        //change this locale for this instance of the JVM
        locale.addListener((observable, oldValue, newValue) -> Locale.setDefault(newValue));
    }
    
    /**
     * get the supported Locales.
     *
     * @return List of Locale objects.
     */
    public static List<Locale> getSupportedLocales() {
        
        //TODO replace Language with this class then language list has to be loaded here
        return new ArrayList<>(Language.getInstance().getLanguageList());
    }
    
    /**
     * Get the default locale.
     * This is the systems default if contained in the supported Locales, english otherwise.
     * Should be used when language property is empty.
     *
     * @return The default Loacle.
     */
    public static Locale getDefaultLocale() {
        Locale sysDefault = Locale.getDefault();
        return getSupportedLocales().contains(sysDefault) ? sysDefault : new Locale("en");
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
     * Gets the string with the given key from the resource bundle for the current locale and uses it as first argument
     * to MessageFormat.format, passing in the optional args and returning the result.
     *
     * @param key The key used to get the string from the Property file.
     * @param args Optional arguments for the retrieved message.
     * @return Localized and formatted string.
     */
    public static String get(final String key, final Object... args) {
        ResourceBundle bundle = ResourceBundle.getBundle("Language", getLocale());
        return MessageFormat.format(bundle.getString(key), args);
    }
    
    /**
     * Creates a String binding to a localized String for the given resource bundle key.
     *
     * @param key The key used to get the string from the Property file.
     * @return String binding A String Binding to the current locale.
     */
    public static StringBinding createStringBinding(final String key, Object... args) {
        return Bindings.createStringBinding(() -> get(key, args), locale);
    }
    
    /**
     * Creates a String Binding to a localized String that is computed by calling the given func.
     *
     * @param func The function called on every change.
     * @return StringBinding A String Binding to the current locale.
     */
    public static StringBinding createStringBinding(Callable<String> func) {
        return Bindings.createStringBinding(func, locale);
    }

//      TODO: remove this is not used because the presenter can set the binding localy
//    /**
//     * creates a bound Label whose value is computed on language change.
//     *
//     * @param func
//     *         the function to compute the value
//     * @return Label
//     */
//    public static void bindStringProperty(StringProperty textProperty, Callable<String> func) {
//        textProperty.bind(createStringBinding(func));
//    }
}
