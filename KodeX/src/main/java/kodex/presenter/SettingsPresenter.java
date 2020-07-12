package kodex.presenter;

import java.io.File;
import java.util.Locale;

import org.controlsfx.control.ToggleSwitch;

import javafx.collections.FXCollections;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.util.StringConverter;
import kodex.model.DefaultSettings;
import kodex.model.Language;
import kodex.model.validator.PortNumValidator;
import kodex.presenter.textformatter.PortNumFormatter;

/**
 * This Presenter is responsible for the settings page. In the settings page the
 * user can alter the settings.
 * 
 * @author Leonhard Kraft
 * @author Yannick Neubert
 * 
 * @version 1.0
 */
public class SettingsPresenter extends Presenter {

    /*
     * Store an Instance of defaultSettings to prevent calling getInstance every
     * time they are needed
     */
    private DefaultSettings defaultSettings;

    @FXML
    private ChoiceBox<Locale> languageChoiceBox;

    @FXML
    private ToggleSwitch darkModeSwitch;

    @FXML
    private TextField portTextField;

    @FXML
    private TextField pathTextField;

    /**
     * Creates a new SettingsPresenter with a reference to the PresenterManager
     * 
     * @param presenterManager The PresenterManager for the superclass constructor.
     */
    public SettingsPresenter(PresenterManager presenterManager) {
        super(presenterManager, "settingspage");
    }

    /**
     * Initializes the view-object created by the FXMLLoader.
     */
    @FXML
    private void initialize() {

        defaultSettings = DefaultSettings.getInstance();

        // reset pseudo classes for reset

        setErrorPseudoClass(portTextField, false);

        /*
         * Initialize the language ChoiceBox setting.
         */

        languageChoiceBox.setConverter(createLanguageConverter());
        languageChoiceBox.setItems(FXCollections.observableArrayList(Language.getInstance().getLanguageList()));
        // set initial language, works fine because select uses equals to compare and
        // therefore the instances don't have to be the same
        languageChoiceBox.getSelectionModel().select(defaultSettings.getLanguage().getLanguageInfo());

        /*
         * Initialize the DarkMode switch setting.
         */

        darkModeSwitch.setSelected(defaultSettings.isDarkMode());

        /*
         * Initialize the port setting.
         */
        portTextField.setTextFormatter(PortNumFormatter.createTextFormatter());

        String portString = Integer.toString(defaultSettings.getPort());
        portTextField.setText(portString);

        /*
         * Initialize the path setting.
         */
        updateDefaultPath();
    }

    /*
     * Creates a language converter to show Locales with their local language
     */
    private StringConverter<Locale> createLanguageConverter() {
        return new StringConverter<Locale>() {

            @Override
            public String toString(Locale locale) {
                return locale.getDisplayLanguage(locale);
            }

            @Override
            public Locale fromString(String string) {
                return null;
            }
        };
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

    /**
     * This Method is called when a new Language is selected. Changes the language.
     */
    @FXML
    public void handleChangeLanguage() {
        defaultSettings.setLanguauge(languageChoiceBox.getValue());
    }

    /**
     * This Method is called when the Dark-Mode has been de-/activated. Changes the
     * appearance of the application by changing the loaded css file.
     */
    @FXML
    public void handleDarkModeToggle() {

        // TODO switch label of switch
        defaultSettings.setDarkMode(darkModeSwitch.isSelected());
    }

    /**
     * This Method is called when the user clicks on the item to change the default
     * path for saving files. Opens a dialog to change the path and saves it.
     */
    @FXML
    public void handleBrowsePath() {

        DirectoryChooser dirChooser = new DirectoryChooser();

        // TODO: change from null to stage to disable stage interactions while dialog is
        // open
        File selectedDirectory = dirChooser.showDialog(null);

        if (selectedDirectory == null) {
            // no directory has been chosen
            return;
        }

        defaultSettings.setDefaultPath(selectedDirectory.getAbsolutePath());
        updateDefaultPath();
    }

    /**
     * This Method is called when the user clicks on the item to confirm the entered
     * default port. Saves the entered port.
     */
    @FXML
    public void handleSubmitPort() {

        String portText = portTextField.getText();

        if (!PortNumValidator.getInstance().isValid(portText)) {
            // port number is invalid

            setErrorPseudoClass(portTextField, true);
            return;
        }

        int portNumber = Integer.parseInt(portText);

        defaultSettings.setPort(portNumber);
    }

    /**
     * This Method is called when the user clicks on the item to restore the default
     * settings. Resets the settings.
     */
    @FXML
    public void handleRestoreDefaultSettings() {

        defaultSettings.reset();

        // initialize all settings again to display the reset
        this.initialize();
    }
}