package kodex.presenter;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import kodex.model.PluginLoader;
import kodex.plugininterface.Pluginable;

/**
 * This presenter is responsible for the plugins menu. In this menu the plugins are displayed
 * in a table, where you can activate or deactivate the plugins as well as add new plugins or remove
 * already loaded plugins from the program entirely.
 * 
 * @author Yannick Neubert
 * @author Raimon Gramlich
 * 
 * @version 1.0
 */
public class PluginMenuPresenter extends Presenter {
	
	/** The Table which displays information about the plugins.*/
	@FXML
	TableView<Pluginable> pluginTable;
	
	/** The Column with check boxes allows the user to enable or disable selected plugins.*/
	@FXML
	TableColumn<Pluginable, Boolean> checkBoxColumn;
	
	/** The Column which displays the names of the plugin.*/
	@FXML 
	TableColumn<Pluginable, String> nameColumn;
	
	/** The Column which displays the descriptions of the plugins.*/
	@FXML 
	TableColumn<Pluginable, String> descriptionColumn;
	
    /** The instance of the PluginLoader class which you can use to work with the loaded plugins. */
    private PluginLoader pluginLoader;

    /**
     * Creates a new PluginPresenter with a reference to a PresenterManger.
     * 
     * @param pm : The reference to the PresenterManager
     */
    public PluginMenuPresenter(PresenterManager pm) {
        super(pm, "pluginpage");
        
        // get a PluginLoader instance this way since PluginLoader uses the singleton pattern
        pluginLoader = PluginLoader.getInstance();
    }
    
    /**
     * Initializes the view-object created by the FXMLLoader.
     * It fills the table with data gathered from the PluginLoader.
     */
	@FXML
	private void initialize() {		
		/* // defines the check box column
		 * checkBoxColumn.setCellValueFactory(c -> {
		 * c.getValue().activatedProperty().addListener((observer, oldValue, newValue)
		 * -> { if (newValue.booleanValue()) {
		 * pluginLoader.activatePlugin(c.getValue()); } else {
		 * pluginLoader.deactivatePlugin(c.getValue()); } }); return
		 * c.getValue().activatedProperty(); });
		 * checkBoxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxColumn
		 * ));
		 * 
		 * // defines the name and description column
		 * nameColumn.setCellValueFactory(cellData ->
		 * cellData.getValue().nameProperty());
		 * descriptionColumn.setCellValueFactory(cellData ->
		 * cellData.getValue().shortDescriptionProperty());
		 * 
		 * // fills in the with the plugin information
		 * pluginTable.setItems(pluginLoader.getEnabledPlugins());
		 * pluginTable.setEditable(true);
		 */
	}

    /**
     * Gets called when the user clicks on the button for adding new plugins.
     * Opens the system-explorer to let the user choose the plugin and loads it if possible.
     */
	@FXML
	private void handleAddPlugin() throws IOException {
		// create new FileChooser
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Plugin zum importieren auswählen.");
		chooser.getExtensionFilters().add(new ExtensionFilter("JAR-Datei", "*.jar"));
		
		// add the plugin located at the given path
		File file = chooser.showOpenDialog(null);
		if (file != null) {
			PluginLoader.loadExternalPlugin(file);	
		} else {
			System.out.println("No plugin chosen.");
		}
	}

    /**
     * Gets called when the user clicks on the button for removing plugins.
     * Deletes the selected plugin if possible.
     */
	@FXML
	private void handleRemovePlugin() throws IOException {
		// get selected table row
		Pluginable plugin = pluginTable.getSelectionModel().getSelectedItem();
		if (plugin != null) {
			pluginLoader.removePlugin(plugin);	
		} else {
			System.out.println("No plugin selected.");
		}
	}

    /**
     * Gets called when the user clicks on the button for refreshing the plugin list. 
     * Reloads the whole plugin folder.
     */
	@FXML
    public void handleRefreshPlugins() {
        pluginLoader.load();
    }
}