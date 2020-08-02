package kodex.presenter;

import java.io.File;
import java.util.Locale;
import java.util.Optional;

import org.controlsfx.control.ToggleSwitch;
import javafx.collections.FXCollections;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.util.StringConverter;
import kodex.model.DefaultSettings;
import kodex.model.I18N;
import kodex.model.validator.PortNumValidator;
import kodex.presenter.textformatter.PortNumFormatter;

/**
 * This Presenter is responsible for the settings page. In the settings page the user can alter the
 * settings.
 *
 * @author Leonhard Kraft
 * @author Yannick Neubert
 * @author Raimon Gramlich
 * @version 1.0
 */
public class SettingsPresenter extends Presenter {

  /*
   * Store an Instance of defaultSettings to prevent calling getInstance every
   * time they are needed
   */
  private DefaultSettings defaultSettings;

  @FXML private ChoiceBox<Locale> languageChoiceBox;

  @FXML private ToggleSwitch darkModeSwitch;

  @FXML private TextField portTextField;

  @FXML private TextField pathTextField;

  @FXML private Label lblHeader;

  @FXML private Label lblLanguage;

  @FXML private Label lblDarkmode;

  @FXML private Label lblDefaultPort;

  @FXML private Label lblDefaultPortDescription;

  @FXML private Label lblDefaultPath;

  @FXML private Label lblDefaultPathDescription;

  @FXML private Button resetButton;

  /**
   * Creates a new SettingsPresenter with a reference to the PresenterManager.
   *
   * @param presenterManager The PresenterManager for the superclass constructor.
   */
  public SettingsPresenter(PresenterManager presenterManager) {
    super(presenterManager, "settingspage");
  }

  /*
   * Creates a language converter to show Locales with their local language
   */
  private StringConverter<Locale> createLanguageConverter() {
    return new StringConverter<Locale>() {

      @Override
      public Locale fromString(String string) {
        return null;
      }

      @Override
      public String toString(Locale locale) {
        return locale.getDisplayLanguage(locale);
      }
    };
  }

  /**
   * This Method is called when the user clicks on the item to change the default path for saving
   * files. Opens a dialog to change the path and saves it.
   */
  @FXML
  public void handleBrowsePath() {

    DirectoryChooser dirChooser = new DirectoryChooser();

    // TODO: change from null to stage to disable stage interactions while dialog is
    // open
    File selectedDirectory = PresenterManager.showDirectoryChooser(dirChooser);

    if (selectedDirectory == null) {
      // no directory has been chosen
      return;
    }

    defaultSettings.setDefaultPath(selectedDirectory.getAbsolutePath());
    updateDefaultPath();
  }

  /** This Method is called when a new Language is selected. Changes the language. */
  @FXML
  public void handleChangeLanguage() {

    Locale chosenLanguage = languageChoiceBox.getValue();

    if (chosenLanguage == null) {
      return;
    }

    defaultSettings.setSavedLanguage(chosenLanguage);
    I18N.setLocale(chosenLanguage);
  }

  /**
   * This Method is called when the Dark-Mode has been de-/activated. Changes the appearance of the
   * application by changing the loaded css file.
   */
  @FXML
  public void handleDarkModeToggle() {

    // TODO switch label of switch
    defaultSettings.setDarkMode(darkModeSwitch.isSelected());
  }

  /**
   * This Method is called when the user clicks on the item to restore the default settings. Resets
   * the settings.
   */
  @FXML
  public void handleRestoreDefaultSettings() {
    
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.titleProperty().bind(I18N.createStringBinding("alert.title.confirmation"));
    alert.headerTextProperty().bind(I18N.createStringBinding("alert.settings.reset"));
    alert.setContentText("Restore default settings? " 
        + " All changes made to the settings will be lost.");
    
    Optional<ButtonType> result = PresenterManager.showAlertDialog(alert);
    
    if (result.isPresent() && result.get() == ButtonType.OK) {
      defaultSettings.reset();

      // initialize all settings again to display the reset
      this.initialize();
    } 
  }

  /**
   * This Method is called when the user clicks on the item to confirm the entered default port.
   * Saves the entered port.
   */
  @FXML
  public void handleSubmitPort() {

    String portText = portTextField.getText();

    if (!PortNumValidator.getInstance().isValid(portText)) {
      // port number is invalid

      setErrorPseudoClass(portTextField, true);
      
      Alert alert = new Alert(AlertType.ERROR);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
      alert.setContentText("The input is not a valid port. " 
          + "The number has to be between 0 and 65535.");
      PresenterManager.showAlertDialog(alert);
      
      return;
    }

    int portNumber = Integer.parseInt(portText);

    defaultSettings.setPort(portNumber);
  }

  /** Initializes the view-object created by the FXMLLoader. */
  @FXML
  private void initialize() {

    defaultSettings = DefaultSettings.getInstance();

    lblHeader.textProperty().bind(I18N.createStringBinding("settingspage.header"));
    lblLanguage.textProperty().bind(I18N.createStringBinding("settingspage.language.header"));
    lblDarkmode.textProperty().bind(I18N.createStringBinding("settingspage.darkmode.header"));
    lblDefaultPort.textProperty().bind(I18N.createStringBinding("settingspage.defaultport.header"));
    lblDefaultPortDescription
        .textProperty()
        .bind(I18N.createStringBinding("settingspage.defaultport.lbl"));
    lblDefaultPath.textProperty().bind(I18N.createStringBinding("settingspage.defaultpath.header"));
    lblDefaultPathDescription
        .textProperty()
        .bind(I18N.createStringBinding("settingspage.defaultpath.lbl"));
    resetButton.textProperty().bind(I18N.createStringBinding("settingspage.resetbutton"));

    // reset pseudo classes for reset

    setErrorPseudoClass(portTextField, false);

    /*
     * Initialize the language ChoiceBox setting.
     */

    languageChoiceBox.setConverter(createLanguageConverter());
    languageChoiceBox.setItems(FXCollections.observableArrayList(I18N.getSupportedLocales()));
    // set initial language, works fine because select uses equals to compare and
    // therefore the instances don't have to be the same
    languageChoiceBox.getSelectionModel().select(I18N.getLocale());

    /*
     * Initialize the DarkMode switch setting.
     */

    darkModeSwitch.setSelected(defaultSettings.isDarkMode());

    /*
     * Initialize the port setting.
     */
    portTextField.setTextFormatter(PortNumFormatter.createTextFormatter());

    String portString = Integer.toString(DefaultSettings.getPort());
    portTextField.setText(portString);

    /*
     * Initialize the path setting.
     */
    updateDefaultPath();
  }

  /*
   * Sets or removes the error pseudoclass for the given control depending on the
   * given state.
   */
  private void setErrorPseudoClass(Control control, boolean state) {

    final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
    control.pseudoClassStateChanged(errorClass, state);
  }

  /*
   * Sets the text of the default path text field to the path saved in the
   * properties
   */
  private void updateDefaultPath() {

    String pathtext = defaultSettings.getDefaultPath();

    pathTextField.setText(pathtext);
  }
}
