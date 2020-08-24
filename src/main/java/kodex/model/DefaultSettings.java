package kodex.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Locale;
import java.util.Properties;

import javafx.scene.control.Alert.AlertType;
import kodex.exceptions.InvalidInputException;
import kodex.exceptions.LoadingException;
import kodex.model.validator.PortNumValidator;

/**
 * This class saves the user's settings. These are saved locally in a file so that they persist the
 * program. As a singleton, the user is able to access the settings from anywhere in the program.
 *
 * @author Patrick Spiesberger
 * @author Leonhard Kraft
 * @author Raimon Gramlich
 * @version 1.0
 */
public class DefaultSettings extends Settings {

  /* current instance of DefaultSettings */
  private static DefaultSettings instance;

  /* current network port */
  private static int port;

  /* current Path where files are stored */
  private static String defaultPath = null;

  /* instance of property file */
  private static Properties prop = new Properties();

  private static Properties defaultProperties = new Properties();

  private static final String SETTINGS_DIRECTORY = "settings";

  private static final String USER_SETTINGS_PROPERTY = "User_Settings.properties";

  private static final String USER_SETTINGS_PATH =
      SETTINGS_DIRECTORY.concat("/".concat(USER_SETTINGS_PROPERTY));

  // internal resource files need '/' as path delimiter
  private static final String INTERNAL_SETTINGS_DIRECTORY = "/kodex/model/settings";

  private static final String DEFAULT_SETTINGS_PROPERTY = "Default_Settings.properties";

  private static final String DEFAULT_SETTINGS_PATH =
      INTERNAL_SETTINGS_DIRECTORY.concat("/".concat(DEFAULT_SETTINGS_PROPERTY));

  private static File userSettingsFile;

  /**
   * Provides the singleton instance of this class. The presenter can request the settings directly
   * from this
   *
   * @return instance of this class
   * @throws LoadingException Thrown when settings can not be loaded.
   * @throws InvalidInputException Thrown when the port is not valid.
   */
  public static DefaultSettings getInstance() throws LoadingException, InvalidInputException {
    if (instance == null) {
      instance = new DefaultSettings();
    }
    return instance;
  }

  /**
   * Returns port of local network.
   *
   * @return port of local
   */
  public static int getPort() {
    return port;
  }

  /* necessary to read the property file */
  private InputStreamReader input = null;

  /**
   * Gets the current parent directory of the running jar.
   *
   * @return the parent path
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  private static String getParentPath() throws UnsupportedEncodingException {
    URL url = DefaultSettings.class.getProtectionDomain().getCodeSource().getLocation();
    String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
    return new File(jarPath).getParentFile().getPath();
  }

  /**
   * Constructor of the DefaultSettings class. However, since this class is a singleton, only one
   * instance can be created
   * @throws LoadingException Thrown when settings can not be loaded.
   * @throws InvalidInputException Thrown when the port is not valid.
   */
  private DefaultSettings() throws LoadingException, InvalidInputException {
    String fileSeparator = System.getProperty("file.separator");

    File settingsDir;
    try {
      settingsDir = new File(getParentPath() + fileSeparator + (SETTINGS_DIRECTORY));

      if (!settingsDir.exists() && settingsDir.mkdir()) {
        // TODO: do something
      }
    } catch (UnsupportedEncodingException e4) {
      e4.printStackTrace();
    }

    // makes a user settings property file with the default settings if necessary
    try {
      userSettingsFile = new File(getParentPath() + (fileSeparator.concat(USER_SETTINGS_PATH)));
    } catch (UnsupportedEncodingException e3) {
      e3.printStackTrace();
    }

    if (!userSettingsFile.exists()) {
      try {
        defaultProperties.load(DefaultSettings.class.getResourceAsStream(DEFAULT_SETTINGS_PATH));
      } catch (IOException e2) {
        e2.printStackTrace();
      }

      // write the default values to the file
      try {
        FileOutputStream fileOut = new FileOutputStream(userSettingsFile);
        defaultProperties.store(fileOut, null);
      } catch (FileNotFoundException e1) {
        e1.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    try {
      input = new InputStreamReader(new FileInputStream(userSettingsFile), "UTF-8");
    } catch (UnsupportedEncodingException | FileNotFoundException e1) {
      e1.printStackTrace();
    }

    try {
      prop.load(input);
      try {
        setPort(Integer.parseInt(prop.getProperty("port")));
      } catch (NumberFormatException e) {
        setPort(0);
      }
      setDefaultPath(prop.getProperty("defaultPath"));

    } catch (IOException e) {

      throw new LoadingException(
          AlertType.ERROR,
          I18N.get("alert.title.error"),
          I18N.get("alert.input.invalid"),
          "Settings can not be loaded",
          e);
    }
  }

  /**
   * Returns the default path for loading and saving files, which the user can create via the
   * program.
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
   * Returns language (as Locale) currently saved in the properties.
   *
   * @return current selected language
   */
  public Locale getSavedLanguage() {
    return new Locale(prop.getProperty("local"));
  }

  /** Resets all settings. 
   * 
   * @throws LoadingException Thrown when default setting could not be loaded.
   * @throws InvalidInputException Thrown when the saved port is not valid.
   * @throws NumberFormatException Thrown when the port was not a valid integer.
   */
  public void reset() throws LoadingException, NumberFormatException, InvalidInputException {
    try {
      prop.load(DefaultSettings.class.getResourceAsStream(DEFAULT_SETTINGS_PATH));
      
      //This causes tests to fail but if you use Platform.runLater() reset of language doesn't work
      I18N.setLocale(new Locale(prop.getProperty("local")));

      setPort(Integer.parseInt(prop.getProperty("port")));

      setDefaultPath(prop.getProperty("defaultPath"));

      PluginLoader.getInstance().resetActivePlugins();

    } catch (IOException e) {
      
      throw new LoadingException(
          AlertType.ERROR,
          I18N.get("alert.title.error"),
          I18N.get("alert.input.invalid"),
          "Settings can not be loaded",
          e);
    } catch (ExceptionInInitializerError e) {
      e.printStackTrace();
    }
  }

  /**
   * Sets the default path for loading and saving files, which the user can create via the program.
   *
   * @param path : desired default path
   */
  public void setDefaultPath(String path) {
    if (path == null || path.isEmpty()) {
      DefaultSettings.defaultPath = System.getProperty("user.home");
    } else {
      DefaultSettings.defaultPath = path;
    }
    prop.setProperty("path", defaultPath);
    storeUserProperties();
  }

  /**
   * Sets port of local network.
   *
   * @param port : port of local network
   * @throws InvalidInputException Thrown when the port is not valid.
   */
  public void setPort(int port) throws InvalidInputException {
    if (PortNumValidator.getInstance().isValid(String.valueOf(port))) {
      DefaultSettings.port = port;
    } else {
      
      throw new InvalidInputException(
          AlertType.ERROR,
          I18N.get("alert.title.error"),
          I18N.get("alert.input.invalid"),
          "Port is not valid");
    }
    prop.setProperty("port", String.valueOf(getPort()));
    storeUserProperties();
  }

  /**
   * Sets the desired language.
   *
   * @param locale : locale of desired language
   */
  public void setSavedLanguage(Locale locale) {
    prop.setProperty("local", locale.getLanguage());
    storeUserProperties();
  }

  /** Stores all properties changed by the user in the external property file. */
  private void storeUserProperties() {
    try {
      prop.store(new FileOutputStream(userSettingsFile), null);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
