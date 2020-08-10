package kodex.model;

import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import kodex.plugininterface.ProcedureInformation;
import kodex.plugininterface.ProcedurePlugin;
import kodex.presenter.PresenterManager;

/**
 * This class contains the list of selected procedure to be displayed on the start page. It can
 * perform operations such as sorting and searching on the list. Every time a new instance of this
 * class is created, it gets the current list of all activated procedures from the Singleton
 * instance of the PluginLoader and is therefore always up to date.
 *
 * @author Patrick Spiesberger
 * @author Leonhard Kraft
 * @version 1.0
 */
public class IndexPage {

  /* List of all aviable procedures */
  private ObservableList<ProcedurePlugin> availableProcedures = FXCollections.observableArrayList();

  /* List of all procedures according to the desired restriction */
  private ObservableList<ProcedurePlugin> selectedProcedures = FXCollections.observableArrayList();

  /* Map with all procedures and the number of times they were clicked */
  private HashMap<ProcedurePlugin, Integer> valueProcedure 
      = new HashMap<ProcedurePlugin, Integer>();
  
  /**
   * Constructor of class IndexPage. Loads the current list of all activated procededures from
   * PluginLoader.
   */
  public IndexPage() {
    availableProcedures = PluginLoader.getInstance().getEnabledProcedurePlugins();

    // all procedures are selected at the start
    selectedProcedures = FXCollections.observableArrayList(availableProcedures);
    
    for (ProcedurePlugin procedure : selectedProcedures) {
      valueProcedure.put(procedure, 0);
    }
  }

  /**
   * Returns a list of those procedures related to the selected one Filter methods fit. It uses a
   * strategy that uses exactly the filtering method that matches the selected filter.
   *
   * @param filter : concrete strategy
   */
  public void filterProcedures(Filter filter) {

    FilterStrategy strategy = null;

    if (filter.equals(Filter.ALPHABETIC)) {
      strategy = new AlphaNumericalSort();

    } else if (filter.equals(Filter.GRADE)) {
      strategy = new LabelSort();

    } else if (filter.equals(Filter.RELEVANCE)) {
      strategy = new RelevancySort();

    } else if (filter.equals(Filter.NO_FILTER)) {
      resetSelectedProcedures();
      return;

    } else {
      Alert alert = new Alert(AlertType.ERROR);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
      alert.setContentText("no suitable filter found");
      PresenterManager.showAlertDialog(alert);
      resetSelectedProcedures();
      return;
    }

    strategy.filterProcedures(selectedProcedures);
  }

  /**
   * Returns a list of those procedures related to the one entered Keyword match.
   *
   * @param searchTerm : search query
   */
  public void findProcedures(String searchTerm) {

    ProcedureInformation info;
    String nameLower;
    String searchLower = searchTerm.toLowerCase();

    for (ProcedurePlugin plugin : availableProcedures) {

      info = plugin.createProcedureInformation();
      nameLower = info.getName().toLowerCase();

      if (nameLower.contains(searchLower)
          || info.getLabels().stream().anyMatch(s -> s.equalsIgnoreCase(searchTerm))) {
        // plugin is a valid search result

        if (!selectedProcedures.contains(plugin)) {
          // list should be unique
          selectedProcedures.add(plugin);
        }
      } else {
        selectedProcedures.remove(plugin);
      }
    }
  }

  public ObservableList<ProcedurePlugin> getSelectedProcedures() {
    return FXCollections.unmodifiableObservableList(selectedProcedures);
  }

  /**
   * Increases the entered value for relevance for the given plugin.
   *
   * @param plugin : The procedure whose relevance should be increased
   */
  public void increaseRelevancy(ProcedurePlugin plugin) {
    if (!valueProcedure.containsKey(plugin)) {
      valueProcedure.put(plugin, 1);
    } else {
      valueProcedure.put(plugin, valueProcedure.get(plugin) + 1);
    }
  }
  
  /**
   * Returns HashMap "valueProcedure".
   * 
   * @return valueProcedure
   */
  public HashMap<ProcedurePlugin, Integer> getValueProcedure() {
    return valueProcedure;
  }

  private void resetSelectedProcedures() {
    // clear and add to avoid creation of a new list instance which would destroy
    // all references
    selectedProcedures.clear();
    selectedProcedures.addAll(availableProcedures);
  }
}
