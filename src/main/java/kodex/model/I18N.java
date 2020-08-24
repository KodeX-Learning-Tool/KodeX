package kodex.model;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileAlreadyExistsException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Alert.AlertType;
import kodex.exceptions.InvalidInputException;
import kodex.exceptions.LoadingException;
import kodex.presenter.PresenterManager;

/**
 * I18N utility class.
 * This class is inspired by:
 * https://www.sothawo.com/2016/09/how-to-implement-a-javafx-ui-where-the-language-can-be-changed-dynamically/
 *
 * @author Leonhard Kraft
 * @author Patrick Spiesberger
 * @author Raimon Gramlich
 * @version 1.0
 */
public class I18N {

  /** the current selected Locale. */
  private static  ObjectProperty<Locale> locale = null;

  private static final List<Locale> supportedLocales = new ArrayList<>();

  private static final String LANGUAGE_FILE_NAME = "Languages";

  private static final String LANGUAGE_FOLDER_PATH = "/kodex/model/languages/";

  private static final String LANGUAGE_PROPERTY_PATH = "kodex.model.languages.";

  /**
   * The Constant LANGUAGE_FILE_LIST_PATH. If you have added a new language
   * property file make sure to add the file name to this file. When building with
   * 'maven' the file is generated automatically with the correct (all) entries,
   * but for running the program in the IDE it is necessary to edit the file
   * manually.
   */
  private static final String LANGUAGE_FILE_LIST_PATH
      = LANGUAGE_FOLDER_PATH + "language-files-list.json";

  private static final String DEFAULT_LOCALE = "en";

  private static final int VALID_NAME_PART_NUMBER = 2;

  private static final int LANGUAGE_PREFIX = 0;

  private static final int LANGUAGE_SUFIX = 1;

  static {
    try {
      loadSupportedLocales();
    } catch (FileAlreadyExistsException | FileNotFoundException e) {
      PresenterManager.showAlertDialog(AlertType.ERROR, I18N.get("alert.title.error"),
          I18N.get("alert.load.failed"), "Language files or the file list are missing!");
    }

    // set language loaded by the settings
    try {
      locale = new SimpleObjectProperty<>(DefaultSettings.getInstance().getSavedLanguage());
      // change the locale for this instance of the JVM
      locale.addListener((observable, oldValue, newValue) -> Locale.setDefault(newValue));
    } catch (LoadingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvalidInputException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

   
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
   * Gets the string with the given key from the resource bundle for the current
   * locale and uses it as first argument to MessageFormat.format, passing in the
   * optional args and returning the result.
   *
   * @param key  The key used to get the string from the Property file.
   * @param args Optional arguments for the retrieved message.
   * @return Localized and formatted string.
   */
  public static String get(final String key, final Object... args) {
    ResourceBundle bundle = ResourceBundle.getBundle(LANGUAGE_PROPERTY_PATH 
        + LANGUAGE_FILE_NAME, getLocale());
    try {
      return MessageFormat.format(bundle.getString(key), args);
    } catch (MissingResourceException e) {
      ResourceBundle defaultBundle = ResourceBundle.getBundle(LANGUAGE_PROPERTY_PATH 
          + LANGUAGE_FILE_NAME, new Locale("EN"));
      try {
        return MessageFormat.format(defaultBundle.getString(key), args);
      } catch (MissingResourceException ex) {
        return "Text not found!";
      }
    }
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
   * Get a list of the supported Locales.
   *
   * @return List of Locale objects.
   */
  public static List<Locale> getSupportedLocales() {

    return supportedLocales;
  }

  private static void loadSupportedLocales() 
      throws FileNotFoundException, FileAlreadyExistsException {

    List<String> fileNames = new ArrayList<>();

    Gson gson = new Gson();

    try (InputStreamReader in = 
        new InputStreamReader(I18N.class.getResourceAsStream(LANGUAGE_FILE_LIST_PATH));
        BufferedReader br = new BufferedReader(in)) {

      String[] jsonFileNames = gson.fromJson(br, String[].class);
      fileNames = Arrays.asList(jsonFileNames);

    } catch (FileNotFoundException e) {
      PresenterManager.showAlertDialog(AlertType.ERROR, I18N.get("alert.title.error"),
          I18N.get("alert.load.failed"), "Language files or the file list are missing!");
      throw new FileNotFoundException("Couldn't find file-list.json.");
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    if (fileNames.isEmpty()) {
      throw new FileNotFoundException("No language file has been found.");
    }

    String[] fileNameParts;
    Locale fileLocale;
    boolean defaultFound = false;
    int exPos;

    /*
     * Get Locale from all available files.
     */
    for (String fileName : fileNames) {
      exPos = fileName.lastIndexOf(".");

      // strip file name of file extension
      fileName = fileName.substring(0, exPos);

      if (fileName.equals(LANGUAGE_FILE_NAME)) {

        if (defaultFound) {
          PresenterManager.showAlertDialog(AlertType.ERROR, I18N.get("alert.title.error"),
              I18N.get("alert.load.failed"),
              "Language default property file " + fileName + "is not unique.");
        }

        defaultFound = true;
        continue;
      }

      fileNameParts = fileName.split("_");

      if (fileNameParts.length != VALID_NAME_PART_NUMBER) {
        PresenterManager.showAlertDialog(AlertType.ERROR, "Error", "Load Failed",
            "Please check name of File: " + fileName);
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
            "Language property file for language " 
        + fileLocale.getDisplayLanguage() + "is not unique.");

      }

      supportedLocales.add(fileLocale);
    }

    if (supportedLocales.isEmpty()) {
      // property files have been found, but no language files
      throw new FileNotFoundException("No valid language file has been found.");
    }
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
   * Set new current Locale.
   *
   * @param locale The new current Locale.
   */
  public static void setLocale(Locale locale) {
    localeProperty().set(locale);
    Locale.setDefault(locale);
  }
}
