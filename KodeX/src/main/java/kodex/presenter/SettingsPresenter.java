package kodex.presenter;

import java.io.File;
import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import kodex.model.DefaultSettings;

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
    private TextField portTextField;
    
    @FXML
    private TextField pathTextField;

    /*
     * Indicates whether the content of the port TextField is valid.
     */
    private boolean validPortTextField;

    /**
     * Creates a new SettingsPresenter with a reference to the PresenterManager
     * 
     * @param presenterManager The PresenterManager for the superclass constructor.
     */
    public SettingsPresenter(PresenterManager presenterManager) {
        super(presenterManager, "settingspage");

        defaultSettings = DefaultSettings.getInstance();
    }

    /**
     * Initializes the view-object created by the FXMLLoader.
     */
    @FXML
    private void initialize() {
        
        //TODO: validate loaded settings or trust them?
        //TODO: reset pseudo classes for reset

        /*
         * Initialize the port setting.
         */
        portTextField.textProperty().addListener(createPortChangeListener());

        String portString = Integer.toString(defaultSettings.getPort());
        portTextField.setText(portString);

        validPortTextField = true;
        
        /*
         * Initialize the path setting.
         */
        updateDefaultPath();
    }

    /**
     * This Method is called when a new Language is selected. Changes the language.
     */
    @FXML
    public void handleChangeLanguage() {
        // TODO implement here
    }

    /**
     * This Method is called when the Dark-Mode has been de-/activated. Changes the
     * appearance of the application by changing the loaded css file.
     */
    @FXML
    public void handleChangeSkin() {
        // TODO implement here
    }

    /**
     * This Method is called when the user clicks on the item to change the default
     * path for saving files. Opens a dialog to change the path and saves it.
     */
    @FXML
    public void handleBrowsePath() {
        
        DirectoryChooser dirChooser = new DirectoryChooser();
        
        //TODO: change from null to stage to disable stage interactions while dialog is open
        File selectedDirectory = dirChooser.showDialog(null);
        
        if (selectedDirectory == null) {
            //no directory has been chosen
            return;
        }
        
        defaultSettings.setDefaultPath(selectedDirectory.getAbsolutePath());
        updateDefaultPath();
    }
    
    /*
     * Sets the text of the default path text field to the path saved in the properties
     */
    private void updateDefaultPath() {
        
        String pathtext = defaultSettings.getDefaultPath();
        
        pathTextField.setText(pathtext);
    }

    private ChangeListener<String> createPortChangeListener() {

        /*
         * Use listener to replace invalid text with the old value. This is not possible
         * if you use a normal handle method because the old value is not known.
         */
        return new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                validatePort(oldValue, newValue);
            }
        };
    }
    
    /*
     * Sets or removes the error pseudoclass for the given control depending on the given state.
     */
    private void setErrorPseudoClass(Control control, boolean state) {
        
        final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
        control.pseudoClassStateChanged(errorClass, state);
    }

    /**
     * This Method is called when the user enters text into the text field for the
     * default port and the text has to be validated.
     */
    private void validatePort(String oldValue, String newValue) {
        /*
         * Only ports in range [0, 65535] are allowed for the ServerSocket. A port
         * number of 0 means that the port number is automatically allocated. Source:
         * https://docs.oracle.com/javase/7/docs/api/java/net/ServerSocket.html
         */

        /*
         * Validate Textfield content: 
         * -text consists only of numerical digits 
         * -text length is in the range [0, 5] 
         * -text as integer is in the accepted port range [0, 65535]
         */

        /*
         * Only 5 digits are allowed and since the port is an integer only numerical
         * digits are accepted
         */
        if (!newValue.matches("\\d{0,5}")) {
            portTextField.setText(oldValue);
            return;
        }

        if (newValue.isEmpty()) {
            setErrorPseudoClass(portTextField, false);
            return;
        }

        int portNumber = Integer.parseInt(newValue);

        if ((portNumber < 0) || (65535 < portNumber)) {
            // not in the given port range

            setErrorPseudoClass(portTextField, true);
            validPortTextField = false;
            return;
        }

        // all checks have passed so the new content of the text field is valid
        setErrorPseudoClass(portTextField, false);
        validPortTextField = true;
    }

    /**
     * This Method is called when the user clicks on the item to confirm the entered
     * default port. Saves the entered port.
     */
    @FXML
    public void handleSubmitPort() {

        if (!validPortTextField) {
            // TODO visually highlight, that the textfield is not valid yet
            return;
        }

        int portNumber = Integer.parseInt(portTextField.getText());

        defaultSettings.setPort(portNumber);
    }

    /**
     * This Method is called when the user clicks on the item to restore the default
     * settings. Resets the settings.
     */
    @FXML
    public void handleRestoreDefaultSettings() {
        
        defaultSettings.reset();
        
        //TODO: reset all pseudo classes
        //initialize all settings again to display the reset
        this.initialize();
    }
}