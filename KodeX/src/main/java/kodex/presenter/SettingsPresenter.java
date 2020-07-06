package kodex.presenter;

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
     * This Method is called when a new Language is selected. Changes the language.
     */
    public void handleChangeLanguage() {
        // TODO implement here
    }

    /**
     * This Method is called when the Dark-Mode has been de-/activated. Changes the
     * appearance of the application by changing the loaded css file.
     */
    public void handleChangeSkin() {
        // TODO implement here
    }

    /**
     * This Method is called when the user clicks on the item to change the default
     * path for saving files. Opens a dialog to change the path and saves it.
     */
    public void handleBrowsePath() {
        // TODO implement here
    }

    /**
     * This Method is called when the user enters text into the text field for the
     * default port.
     */
    public void handlePortText() {
        // TODO implement here
    }

    /**
     * This Method is called when the user clicks on the item to confirm the entered
     * default port. Saves the entered port.
     */
    public void handleSubmitPort() {
        // TODO implement here
    }

    /**
     * This Method is called when the user clicks on the item to restore the default
     * settings. Resets the settings.
     */
    public void handleRestoreDefaultSettings() {
        // TODO implement here
    }
}