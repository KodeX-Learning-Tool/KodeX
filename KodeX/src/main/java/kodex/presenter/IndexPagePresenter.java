package kodex.presenter;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import kodex.model.Filter;
import kodex.model.I18N;
import kodex.model.IndexPage;
import kodex.model.SideMenuTypes;
import kodex.plugininterface.ProcedureInformation;
import kodex.plugininterface.ProcedurePlugin;

/**
 * This presenter is responsible for the index page. The view displays the all available coding
 * procedures, which can be adjusted by the search or filter/sort function. If a procedure is
 * selected, the ProcedureLayoutPresenter will display its view next.
 *
 * @author Yannick Neubert
 * @author Raimon Gramlich
 * @author Leonhard Kraft
 * @version 1.0
 */
public class IndexPagePresenter extends Presenter {

  /**
   * This class represents a single procedure button. It loads a template via fxml file and fills it
   * with the information it has gotten from an instance of the ProcedureInformation class.
   *
   * @author Raimon Gramlich
   * @author Leonhard Kraft
   * @version 1.0
   */
  private class ProcedureButton extends VBox {

    /** The width for all procedure images. */
    private static final int IMAGE_WIDTH = 240;

    /** The height for all procedure images. */
    private static final int IMAGE_HEIGHT = 240;

    private static final int INSETS = 32 * 2;

    /** The Label for displaying the procedure name. */
    @FXML private Label procedureLabel;

    /** The container for the image. */
    @FXML private Pane iconContainer;

    /** The ImageView for displaying the procedure icon. */
    @FXML private ImageView procedureIcon;

    /** The procedure which this button represents. */
    private ProcedurePlugin procedure;

    /** The information regarding the procedure. */
    private ProcedureInformation procedureInformation;

    /** The IndexPage (required for relevancy). */
    private IndexPage indexPage = new IndexPage();
    
    /**
     * Creates a new ProcedureButton with a reference to a ProcedurePlugin.
     *
     * @param procedurePlugin : The reference to the ProcedurePlugin.
     */
    ProcedureButton(ProcedurePlugin procedurePlugin) {

      this.procedure = procedurePlugin;
      this.procedureInformation = procedurePlugin.createProcedureInformation();

      // loads the template
      String fileName = "procedurebuttontemplate.fxml";
      
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
      loader.setController(this);
      loader.setRoot(this);
      try {
        loader.load();
      } catch (IOException exc) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
        alert.headerTextProperty().bind(I18N.createStringBinding("alert.load.failed"));
        alert.setContentText("Failed creating procedure button for " 
            + procedurePlugin.pluginNameProperty().get() + " with " + fileName + ".");
        PresenterManager.showAlertDialog(alert);
      }

      procedureLabel.setText(procedureInformation.getName());
      procedureIcon.setImage(procedureInformation.getIcon());

      /*
       * Insets are needed, because prefHeight and Width of a stack pane also include
       * the padding (left/right, top/bottom).
       */
      iconContainer.setPrefHeight(IMAGE_HEIGHT + INSETS);
      iconContainer.setPrefWidth(IMAGE_WIDTH + INSETS);

      /*
       * When fit width and height are both set, preserveRatio will try to create the
       * best fit for the image into the width and height "box" created by fit width
       * and height while keeping the aspect ratio
       */
      procedureIcon.setPreserveRatio(true);

      procedureIcon.setFitHeight(IMAGE_HEIGHT);
      procedureIcon.setFitWidth(IMAGE_WIDTH);
    }

    /**
     * Gets called when the user clicks on a ProcedureButton. Switches the active presenter and
     * gives the reference of the procedure it represents to the PresenterManager.
     */
    @FXML
    private void handleProcedureSelected() {
      presenterManager.updateSideMenuView(SideMenuTypes.MINI_MENU);
      presenterManager.updatePresenter(presenterFactory.createProcedureLayoutPresenter(procedure));
      indexPage.increaseRelevancy(procedure);
    }
  }

  /** The List of procedures which will be displayed on the index page. */
  private ObservableList<ProcedurePlugin> selectedProcedures;

  /** A Label displaying the page title. */
  @FXML private Label lblHeader;

  /** A TextField which allows the user to input search terms. */
  @FXML private TextField searchTextField;

  /** A ComboBox for choosing categories for sorting or filtering the diplayed procedures. */
  @FXML private ComboBox<Filter> filterComboBox;

  /**
   * ProcedureButtons, to be shown on the index page, will be dynamically added to this TilePane.
   */
  @FXML private TilePane procedureButtonPane;

  /** Allows the creation of other presenter to which the IndexPagePresenter can switch with. */
  private PresenterFactory presenterFactory;

  /** Represents the model which stores the data for this presenter. */
  private IndexPage indexPage;

  /**
   * Creates a new IndexPagePresenter with references to a PresenterManger and a PresenterFactory.
   *
   * @param pm : The reference to the PresenterManger.
   * @param pf : The reference to the PresenterFactory.
   */
  public IndexPagePresenter(PresenterManager pm, PresenterFactory pf) {
    super(pm, "indexpage");
    this.presenterFactory = pf;
  }

  /**
   * Creates and adds all ProceduresButtons to the index page view.
   *
   * @param filter : The selected filter.
   */
  private void createProcedureButtons() {
    for (ProcedurePlugin procedure : selectedProcedures) {
      procedureButtonPane.getChildren().add(new ProcedureButton(procedure));
    }
  }

  /** Filters or sorts the list of enabled procedure plugins according to the selected filter. */
  private void filterProcedures() {
    indexPage.filterProcedures(filterComboBox.getSelectionModel().getSelectedItem());
    updateProcedureButtons();
  }

  /** Gets called when the user selects an item in the combo box. */
  @FXML
  private void handleFilterSelected() {
    filterProcedures();
    // TODO: keep search after filtering
  }

  /** Gets called when the user writes something in the search text field. */
  @FXML
  private void handleSearch() {
    searchProcedures();
    // TODO: keep filter after searching
  }

  /**
   * Initializes the view-object created by the FXMLLoader.
   *
   * @throws IOException is thrown when the fxml file couldn't get loaded properly.
   */
  @FXML
  private void initialize() {
    this.indexPage = new IndexPage();

    //
    lblHeader.textProperty().bind(I18N.createStringBinding("indexpage.header"));
    searchTextField
        .promptTextProperty()
        .bind(I18N.createStringBinding("indexpage.searchbar.prompt"));
    filterComboBox
        .promptTextProperty()
        .bind(I18N.createStringBinding("indexpage.filterbox.prompt"));

    // get procedures unordered and unfiltered
    selectedProcedures = indexPage.getSelectedProcedures();

    // initialize combo box items
    filterComboBox.setItems(FXCollections.observableArrayList(Filter.values()));

    // determines the how each item is displayed in the combo box
    Callback<ListView<Filter>, ListCell<Filter>> cellFactory =
        new Callback<ListView<Filter>, ListCell<Filter>>() {
          @Override
          public ListCell<Filter> call(ListView<Filter> p) {
            return new ListCell<Filter>() {
              @Override
              protected void updateItem(Filter item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                  setText("");
                } else {
                  setText(item.toString());
                }
              }
            };
          }
        };

    filterComboBox.setCellFactory(cellFactory);
    filterComboBox.setButtonCell(cellFactory.call(null));

    // adds the ProcedureButtons for each enabled procedure plugin
    createProcedureButtons();
  }

  /**
   * Searches in the list of enabled procedure plugins for a procedure name which matches the given
   * term.
   */
  private void searchProcedures() {
    indexPage.findProcedures(searchTextField.getText());
    updateProcedureButtons();
  }

  /** Clears all procedureButtons and renews the displayed buttons. */
  private void updateProcedureButtons() {
    // TODO not good practice to delete everything and create it again
    procedureButtonPane.getChildren().clear();
    createProcedureButtons();
  }
}
