package kodex.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import kodex.presenter.PresenterManager;

/**
 * I18N utility class..
 *
 * <p>This class is inspired by:
 *
 * <p>https://www.sothawo.com/2016/09/how-to-implement-a-javafx-ui-where-the-language-can-be-changed-dynamically/
 *
 * @author Leonhard Kraft
 * @author Patrick Spiesberger
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

  static {
    try {
      loadSupportedLocales();
    } catch (FileAlreadyExistsException | FileNotFoundException e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
      alert.setContentText("Language file is missing!");
      PresenterManager.showAlertDialog(alert);
    }

    try {
      loader =
          new URLClassLoader(
              new URL[] {
                new File(I18N.class.getResource(LANGUAGE_FOLDER_NAME).getPath()).toURI().toURL()
              });
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // set language loaded by the settings
    locale = new SimpleObjectProperty<>(DefaultSettings.getInstance().getSavedLanguage());

    // change the locale for this instance of the JVM
    locale.addListener((observable, oldValue, newValue) -> Locale.setDefault(newValue));
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
   * Gets the string with the given key from the resource bundle for the current locale and uses it
   * as first argument to MessageFormat.format, passing in the optional args and returning the
   * result.
   *
   * @param key The key used to get the string from the Property file.
   * @param args Optional arguments for the retrieved message.
   * @return Localized and formatted string.
   */
  public static String get(final String key, final Object... args) {
    ResourceBundle bundle = ResourceBundle.getBundle(LANGUAGE_FILE_NAME, getLocale(), loader);
    return MessageFormat.format(bundle.getString(key), args);
  }

  /**
   * Get the default locale. This is the systems default if contained in the supported Locales,
   * english otherwise. Should be used when language property is empty.
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

  private static void loadSupportedLocales() throws FileAlreadyExistsException, FileNotFoundException {

    try {
      System.out.println("A");
      File jarFile = new File(I18N.class
          .getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
      System.out.println(jarFile);
      JarFile newJarFile = new JarFile(jarFile);
      System.out.println(newJarFile);
      System.out.println(newJarFile.getEntry("kodex"));
      System.out.println(newJarFile.getEntry("kodex/model"));
      System.out.println(newJarFile.getEntry("kodex/model/languages"));
      System.out.println(newJarFile.getEntry("kodex/model/languages/I18N.class"));
      ZipEntry a = newJarFile.getEntry("kodex/model/languages");
    } catch (URISyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    ArrayList<JarEntry> fileNameList = new ArrayList<>();
    
    Enumeration<JarEntry> jar;
    try {
      jar = new JarFile(
          new File(I18N.class.getProtectionDomain().getCodeSource().getLocation().toURI())).entries();
      
      Iterator<JarEntry> it = jar.asIterator();
      while (it.hasNext()) {     
        JarEntry jarEntry = it.next();
        String name = jarEntry.getName();
       // System.out.println(name);
        if (name.contains("kodex/model/languages/" + LANGUAGE_FILE_NAME)) {
          System.out.println("Found: " + name);
          fileNameList.add(jarEntry);
        }
      }
    } catch (IOException | URISyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    

    
    // File to the languages folder directory
    File languageDir = new File(I18N.class.getResource(LANGUAGE_FOLDER_NAME).getPath());

    /*
     * Languages are only contained in property files.
     */
    FileFilter propertyFilter = file -> file.getPath().toLowerCase().endsWith(".properties");

    // File[] languageFiles = languageDir.listFiles(propertyFilter);

    if (fileNameList.size() <= 0) {
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
    for (JarEntry propertyFile : fileNameList) {

      fileName = propertyFile.getName();

      exPos = fileName.lastIndexOf(".");

      // strip file name of file extension
      fileName = fileName.substring(0, exPos);

      if (fileName.equals(LANGUAGE_FILE_NAME)) {

        if (defaultFound) {
          Alert alert = new Alert(AlertType.ERROR);
          alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
          alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
          alert.setContentText("Language default property file " + fileName + "is not unique.");
          PresenterManager.showAlertDialog(alert);
        }

        defaultFound = true;
        continue;
      }

      fileNameParts = fileName.split("_");

      if (fileNameParts.length != VALID_NAME_PART_NUMBER) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
        alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
        alert.setContentText("Please check name of File: " + fileName);
        PresenterManager.showAlertDialog(alert);
        continue;
      }

      if (!fileNameParts[LANGUAGE_PREFIX].equals(LANGUAGE_FILE_NAME)) {
        // not a Language file
        continue;
      }

      fileLocale = new Locale(fileNameParts[LANGUAGE_SUFIX]);
      
      System.out.println(fileLocale);

      if (supportedLocales.contains(fileLocale)) {

        // there should only be one language property file of each language
        throw new FileAlreadyExistsException(
            "Language property file for language "
                + fileLocale.getDisplayLanguage()
                + "is not unique.");
        
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

  private I18N() {
  }
}
