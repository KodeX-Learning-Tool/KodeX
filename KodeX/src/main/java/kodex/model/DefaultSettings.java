package kodex.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import kodex.model.validator.PortNumValidator;
import kodex.presenter.PresenterManager;

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
  
  private static final String DEFAULT_SETTINGS_PATH  
      = INTERNAL_SETTINGS_DIRECTORY.concat("/".concat(DEFAULT_SETTINGS_PROPERTY));
  
  private static File userSettingsFile;

  /**
   * Provides the singleton instance of this class. The presenter can request the settings directly
   * from this
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
   * Returns port of local network.
   *
   * @return port of local
   */
  public static int getPort() {
    return port;
  }

  /* nessesary to read the property file */
  private InputStream input = null;

  /**
   * Constructor of the DefaultSettings class. However, since this class is a singleton, only one
   * instance can be created
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

    } catch (IOException e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
      alert.setContentText("Settings can not be loaded");
      PresenterManager.showAlertDialog(alert);
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

  /** Resets all settings. */
  public void reset() {
    String url = "settings/Default_Settings.properties";
    input = getClass().getResourceAsStream(url);

    try {
      prop.load(input);

      I18N.setLocale(new Locale(prop.getProperty("local")));

      setPort(Integer.parseInt(prop.getProperty("port")));

      setDefaultPath(prop.getProperty("defaultPath"));

    } catch (IOException e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
      alert.setContentText("Settings can not be loaded");
      PresenterManager.showAlertDialog(alert);
    }
  }

  /**
   * Sets the default path for loading and saving files, which the user can create via the program.
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
   * Sets port of local network.
   *
   * @param port : port of local network
   */
  public void setPort(int port) {
    if (!PortNumValidator.getInstance().isValid(input.toString())) {
      DefaultSettings.port = port;
    } else {
      Alert alert = new Alert(AlertType.ERROR);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
      alert.setContentText("Port is not valid");
      PresenterManager.showAlertDialog(alert);
      return;
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

  /*
   * Stores all properties changed by user
   */
  private void storeUserProperties() {
    try {
      prop.store(
          new FileOutputStream(
              getClass().getResource("settings").getPath() + "/User_Settings.properties"),
          null);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
