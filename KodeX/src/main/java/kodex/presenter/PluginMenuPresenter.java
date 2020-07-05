package kodex.presenter;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import kodex.model.PluginLoader;
import kodex.plugininterface.Pluginable;

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
	
	@FXML
	TableView<Pluginable> pluginTable;
	
	@FXML
	TableColumn<Pluginable, Boolean> checkBoxColumn;
	
	@FXML 
	TableColumn<Pluginable, String> nameColumn;
	
	@FXML 
	TableColumn<Pluginable, String> descriptionColumn;
	
    /**
     * Die Instanz des PluginLoaders mit seiner liste an Plugins
     */
    private PluginLoader pluginLoader;



    /**
     * Erstellt einen neuen PluginPresenter mit einer Referenz zu einem PresenterManger.
     * @param pm : Der PresenterManager
     */
    public PluginMenuPresenter(PresenterManager pm) {
        super(pm, "pluginpage");
        
        pluginLoader = PluginLoader.getInstance();
    }
    
	@FXML
	private void initialize() {		
		/*
		 * checkBoxColumn.setCellValueFactory(c -> {
		 * c.getValue().activatedProperty().addListener((observer, oldValue, newValue)
		 * -> { if (newValue.booleanValue()) {
		 * pluginLoader.activatePlugin(c.getValue()); } else {
		 * pluginLoader.deactivatePlugin(c.getValue()); } }); return
		 * c.getValue().activatedProperty(); });
		 * checkBoxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxColumn
		 * ));
		 * 
		 * nameColumn.setCellValueFactory(cellData ->
		 * cellData.getValue().nameProperty());
		 * descriptionColumn.setCellValueFactory(cellData ->
		 * cellData.getValue().shortDescriptionProperty());
		 * 
		 * pluginTable.setItems(pluginLoader.getEnabledPlugins());
		 * pluginTable.setEditable(true);
		 */
	}

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer auf die Schaltfläche zum Hinzufügen 
     * eines Plugins klickt. Öffnet den System-Explorer zum Auswählen des Plugins und lädt diesen falls möglich.
     */
	@FXML
	private void handleAddPlugin() throws IOException {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Plugin zum importieren auswählen.");
		chooser.getExtensionFilters().add(new ExtensionFilter("JAR-Datei", "*.jar"));
		File file = chooser.showOpenDialog(null);
		if (file != null) {
			pluginLoader.addPlugin(file);	
		} else {
			System.out.println("No plugin chosen.");
		}
	}

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer auf die Schaltfläche zum Entfernen 
     * eines Plugins klickt. Löscht das Plugin aus dem Plugins-Ordner.
     */
	@FXML
	private void handleRemovePlugin() throws IOException {
		Pluginable plugin = pluginTable.getSelectionModel().getSelectedItem();
		if (plugin != null) {
			pluginLoader.removePlugin(plugin);	
		} else {
			System.out.println("No plugin selected.");
		}
	}

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer auf die Schaltfläche zum Aktualisieren 
     * eines Plugins klickt. Lädt alle Plugins im Plugin-Ordner.
     */
	@FXML
    public void handleRefreshPlugins() {
        // TODO implement here
    }
}