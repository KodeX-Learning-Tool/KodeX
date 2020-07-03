package kodex.presenter;

import javafx.scene.layout.AnchorPane;
import kodex.model.DefaultSettings;

/**
 * Dieser Presenter ist für die Einstellungsseite zuständig. Hier kann der Benutzer 
 * Änderungen in den Einstellungen vornehmen.
 * 
 * @author Yannick Neubert
 * 
 * @version 1.0
 */
public class SettingsPresenter extends Presenter {

    /**
     * Die gewählten Einstellungen
     */
    private DefaultSettings defaultSettings;



    /**
     * Erstellt einen neuen SettingsPresenter mit Referenzen zu einem PresenterManger.
     * @param pm : Der PresenterManager
     */
    public void SettingsPresenter(PresenterManager pm) {
        // TODO implement here
    }

    /**
     * Die Methode wird ausgeführt, wenn eine andere Sprache ausgewählt wurde. 
     * Ändert die Sprache und versucht das Programm neu zu starten.
     */
    public void handleChangeLanguage() {
        // TODO implement here
    }

    /**
     * Die Methode wird ausgeführt, wenn der Dark-Mode (de-)aktiviert wurde. 
     * Ändert das Aussehen des Programms.
     */
    public void handleChangeSkin() {
        // TODO implement here
    }

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer auf die Schaltfläche zum Ändern des 
     * Standardpfads klickt. Öffnet ein Dialog zum Ändern des Pfads und speichert diesen ab.
     */
    public void handleChooseDefaultPath() {
        // TODO implement here
    }

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer auf die Schaltfläche zum Bestätigen 
     * des Standardports klickt. Speichert diesen ab.
     */
    public void handleSubmitPort() {
        // TODO implement here
    }

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer auf die Schaltfläche zum Wiederherstellen 
     * der Standardeinstellungen klickt. Setzt die Einstellungen zurück.
     */
    public void handleRestoreDefaultSettings() {
        // TODO implement here
    }

    /**
     * Fordert den Benutzer zum Neustarten des Programms auf
     */
    private void showRestartDialog() {
        // TODO implement here
    }

	@Override
	public AnchorPane getView() {
		// TODO Auto-generated method stub
		return null;
	}

}