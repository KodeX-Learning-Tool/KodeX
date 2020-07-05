package kodex.presenter;

import javafx.scene.layout.AnchorPane;
import kodex.model.PluginLoader;

/**
 * Dieser Presenter ist für die Pluginmenüseite zuständig. Auf dieser Seite werden die Plugins 
 * in einer Tabelle mit weiteren Information aufgelistet, wo man sie (de-)aktivieren 
 * oder auch neue Plugins hinzufügen bzw. geladene Plugins entfernen kann.
 * 
 * @author Yannick Neubert
 * 
 * @version 1.0
 */
public class PluginMenuPresenter extends Presenter {
	
    /**
     * Die Instanz des PluginLoaders mit seiner liste an Plugins
     */
    private PluginLoader plugins;



    /**
     * Erstellt einen neuen PluginPresenter mit einer Referenz zu einem PresenterManger.
     * @param pm : Der PresenterManager
     */
    public PluginMenuPresenter(PresenterManager pm) {
        // TODO implement here
    }

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer auf die Schaltfläche zum Hinzufügen 
     * eines Plugins klickt. Öffnet den System-Explorer zum Auswählen des Plugins und lädt diesen falls möglich.
     */
    public void handleAddPlugin() {
        // TODO implement here
    }

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer auf die Schaltfläche zum Entfernen 
     * eines Plugins klickt. Löscht das Plugin aus dem Plugins-Ordner.
     */
    public void handleRemovePlugin() {
        // TODO implement here
    }

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer auf die Schaltfläche zum Aktualisieren 
     * eines Plugins klickt. Lädt alle Plugins im Plugin-Ordner.
     */
    public void handleRefreshPlugins() {
        // TODO implement here
    }

	@Override
	public AnchorPane getView() {
		// TODO Auto-generated method stub
		return null;
	}

}