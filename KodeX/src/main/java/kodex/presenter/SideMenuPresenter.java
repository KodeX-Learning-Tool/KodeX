package kodex.presenter;

import javafx.scene.layout.AnchorPane;
import kodex.model.SideMenuTypes;

/**
 * Dieser Presenter ist für das Seitenmenü zuständig. 
 * Durch Klicken der Schaltflächen des Menüs kann der Benutzer 
 * schnell zu anderen Haupt-Presentern wechseln.
 * 
 * @author Yannick Neubert
 * 
 * @version 1.0
 */
public class SideMenuPresenter extends Presenter {

    /**
     * Erstellt einen neuen SideMenuPresenter mit Referenzen zu dem PresenterManager 
     * und einer PresenterFactory.
     * @param pm : Die Referenz zum PresenterManger, um zu anderen Fenstern wechseln zu können.
     * @param pf : Die Referenz zur PresenterFactory mit welcher der Presenter andere Presenter erstellen kann.
     */
    public SideMenuPresenter(PresenterManager pm, PresenterFactory pf) {
        // TODO implement here
    }

    /**
     * Die Methode ändert den Typ des Seitenmenüs. Hierbei kann man zwischen dem standardmäßigen
     * oder dem minimierten Seitenmenü wählen.
     * @param type : Der Typ des Seitenmenüs.
     */
    public void changeSideMenuType(SideMenuTypes type) {
        // TODO implement here
    }

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer auf die Startfenster-Schaltfläche klickt. 
     * Wechselt zum IndexPagePresenter und benachrichtigt den PresenterManger.
     */
    public void handleIndexPage() {
        // TODO implement here
    }

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer auf die Netzwerkfenster-Schaltfläche klickt. 
     * Wechselt zum NetworkPresenter und benachrichtigt den PresenterManger.
     */
    public void handleNetwork() {
        // TODO implement here
    }

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer auf die Einstellungen-Schaltfläche klickt. 
     * Wechselt zum SettingsPresenter und benachrichtigt den PresenterManger.
     */
    public void handleSettings() {
        // TODO implement here
    }

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer auf die Plugins-Schaltfläche klickt. 
     * Wechselt zum PluginsMenuPresenter und benachrichtigt den PresenterManger.
     */
    public void handlePlugins() {
        // TODO implement here
    }

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer auf die Hilfe-Schaltfläche klickt. 
     * Wechselt zum HelpPresenter und benachrichtigt den PresenterManger.
     */
    public void handleHelp() {
        // TODO implement here
    }

	@Override
	public AnchorPane getView() {
		// TODO Auto-generated method stub
		return null;
	}

}