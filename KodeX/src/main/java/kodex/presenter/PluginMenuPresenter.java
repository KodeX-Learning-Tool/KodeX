package kodex.presenter;

import java.io.File;
import java.util.List;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.TableRow;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import kodex.model.I18N;
import kodex.model.PluginLoader;
import kodex.plugininterface.Pluginable;

/**
 * This presenter is responsible for the plugins menu. In this menu the plugins are displayed in a
 * table, where you can activate or deactivate the plugins as well as add new plugins or remove
 * already loaded plugins from the program entirely.
 *
 * @author Yannick Neubert
 * @author Raimon Gramlich
 * @version 1.0
 */
public class PluginMenuPresenter extends Presenter {

  /** The Table which displays information about the plugins. */
  @FXML TableView<Pluginable> pluginTable;

  /** The Column with check boxes allows the user to enable or disable selected plugins. */
  @FXML TableColumn<Pluginable, Boolean> checkBoxColumn;

  /** The Column which displays the names of the plugin. */
  @FXML TableColumn<Pluginable, String> nameColumn;

  /** The Column which displays the descriptions of the plugins. */
  @FXML TableColumn<Pluginable, String> descriptionColumn;

  /** The Button which allows the user to import new plugins. */
  @FXML Button addPluginButton;

  /** The Button which allows the user to remove currently loaded plugins. */
  @FXML Button removePluginButton;

  /** The instance of the PluginLoader class which you can use to work with the loaded plugins. */
  private PluginLoader pluginLoader;
  
  /** The list of default-plugin names. */
  private List<String> defaultPlugins;

  /**
   * Creates a new PluginPresenter with a reference to a PresenterManger.
   *
   * @param pm : The reference to the PresenterManager
   */
  public PluginMenuPresenter(PresenterManager pm) {
    super(pm, "pluginpage");
  }

  /**
   * Gets called when the user clicks on the button for adding new plugins. Opens the
   * system-explorer to let the user choose the plugin and loads it if possible.
   */
  @FXML
  private void handleAddPlugin() {
    // create new FileChooser
    FileChooser chooser = new FileChooser();
    chooser.titleProperty().bind(I18N.createStringBinding("pluginpage.import.title"));
    chooser.getExtensionFilters().add(new ExtensionFilter(I18N.get("files.jar"), "*.jar"));

    // add the plugin located at the given path
    File file = PresenterManager.showOpenFileChooser(chooser);
    if (file != null) {
      PluginLoader.getInstance().importPlugin(file);
    }
  }

  /**
   * Gets called when the user clicks on the button for refreshing the plugin list. Reloads the
   * whole plugin folder.
   */
  @FXML
  public void handleRefreshPlugins() {
    pluginLoader.loadExternalPlugins();
  }

  /**
   * Gets called when the user clicks on the button for removing plugins. Deletes the selected
   * plugin if possible.
   */
  @FXML
  private void handleRemovePlugin() {
    // get selected table row
    Pluginable plugin = pluginTable.getSelectionModel().getSelectedItem();
    if (plugin == null) {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.information"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.operation.invalid"));
      alert.setContentText("No plugin selected.");
      PresenterManager.showAlertDialog(alert);
    } else if (defaultPlugins.contains(plugin.pluginNameProperty().get())) {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.information"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.operation.invalid"));
      alert.setContentText("Default plugins can't be removed.");
      PresenterManager.showAlertDialog(alert);
    } else {
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.confirmation"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.delete.plugin"));
      alert.setContentText("Are you sure you want to delete " + plugin.pluginNameProperty().get()
          + ".jar? The plugin will be permanently deleted from the plugins folder.");
      Optional<ButtonType> result = PresenterManager.showAlertDialog(alert);
      
      if (result.isPresent() && result.get() == ButtonType.OK) {
        pluginLoader.removePlugin(plugin);
      }      
    }
  }

  /**
   * Initializes the view-object created by the FXMLLoader. It fills the table with data gathered
   * from the PluginLoader.
   */
  @FXML
  private void initialize() {
    // bind the text properties to the language file values
    checkBoxColumn.textProperty().bind(I18N.createStringBinding("pluginpage.activeColumn.header"));
    nameColumn.textProperty().bind(I18N.createStringBinding("pluginpage.nameColumn.header"));
    descriptionColumn
        .textProperty()
        .bind(I18N.createStringBinding("pluginpage.descriptionColumn.header"));
    addPluginButton.textProperty().bind(I18N.createStringBinding("pluginpage.addbutton"));
    removePluginButton.textProperty().bind(I18N.createStringBinding("pluginpage.removebutton"));

    // get a PluginLoader instance this way since PluginLoader uses the singleton pattern
    pluginLoader = PluginLoader.getInstance();
    
    defaultPlugins = pluginLoader.getDefaultPluginNames();

    // defines the check box column
    checkBoxColumn.setCellValueFactory(
        c -> {
          c.getValue()
              .activatedProperty()
              .addListener(
                  (observer, oldValue, newValue) -> {
                    if (newValue.booleanValue()) {
                      pluginLoader.activatePlugin(c.getValue());
                    } else {
                      pluginLoader.deactivatePlugin(c.getValue());
                    }
                  });
          return c.getValue().activatedProperty();
        });  

    checkBoxColumn.setCellFactory(column ->
        new CheckBoxTableCell<Pluginable, Boolean>() {
    
          @Override
          public void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
    
            // disables check-boxes for default / protected plugins
            TableRow<Pluginable> currentRow = getTableRow();
            this.setDisable(false); // it is required to fit default state
            if (currentRow != null && currentRow.getItem() != null && !empty
                && defaultPlugins.contains(currentRow.getItem().pluginNameProperty().get())) {
              this.setDisable(true);
              this.getStyleClass().add("plugin__check-box-cell");
            }
          }
    });

    // defines the name and description column
    nameColumn.setCellValueFactory(cellData -> cellData.getValue().pluginNameProperty());
    descriptionColumn.setCellValueFactory(
        cellData -> cellData.getValue().pluginDescriptionProperty());

    // fills in the with the plugin information
    pluginTable.setItems(pluginLoader.getPlugins());
    pluginTable.setEditable(true);
  }
}
