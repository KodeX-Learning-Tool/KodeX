package kodex.presenter;

import java.io.File;
import java.io.IOException;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane.Divider;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import kodex.model.I18N;
import kodex.plugininterface.ChainLinkHeaderPresenter;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ProcedurePlugin;

/**
 * This Presenter is responsible for the Coding Chain. This Page shows the Coding Chain in its
 * entirety and is responsible for interactions regarding the display of said Coding Chain. These
 * are creating the view for a given Procedure-Plugin, hiding a Chain Link, and jumping to a Chain
 * Link.
 *
 * @author Yannick Neubert
 * @author Raimon Gramlich
 * @version 1.0
 */
public class ChainPresenter implements IPresenter {

  /**
   * This class represents a single chain link. It loads a template via fxml file and fills it with
   * the content it has gotten from an instance of the ChainLinkPresenter class.
   *
   * @author Raimon Gramlich
   * @version 1.0
   */
  private class ChainItem extends BorderPane {

    /** The Label for displaying the name of the chain link. */
    @FXML private Label titleLabel;

    /** The BorderPane to the ProcedureLayoutPresenter. */
    @FXML private BorderPane chainLinkPane;

    /** The BorderPane containing the interactions and content as well as header. */
    @FXML private BorderPane chainLinkContainer;

    /** The VBox containing information like the header and the export button. */
    @FXML private BorderPane informationBox;

    /** The Icon displaying whether or not the chain item is expanded. */
    @FXML private FontIcon hideButtonIcon;

    /** The titled pane containing further information. */
    @FXML private TitledPane informationTitledPane;

    /** The export button. */
    @FXML private Button exportButton;

    /** The edit button. */
    @FXML private Button editButton;

    /** The BorderPane which is displayed when the content is hidden. */
    @FXML private BorderPane hiddenPane;

    /** The Label which is displayed when the content is hidden. */
    @FXML private Label hiddenLabel;

    /** This Boolean represents the state of the chain item. */
    private boolean hidden;

    /** The IconLiteral of the hidden-icon. */
    private String hiddenIcon = "mdi-chevron-down";

    /** The IconLiteral of the expanded-icon. */
    private String shownIcon = "mdi-chevron-right";

    /** The reference to the ChainLinkPresenter. */
    private ChainLinkPresenter chainLinkPresenter;

    /**
     * Creates a new ChainItem with a reference to its ChainLinkPresenter.
     *
     * @param chainLinkPresenter : The reference to the ChainLinkPresenter.
     */
    ChainItem(ChainLinkPresenter chainLinkPresenter) {
      this.chainLinkPresenter = chainLinkPresenter;

      hidden = false;

      // loads template file
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chainlinktemplate.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
      } catch (IOException exc) {
        exc.printStackTrace();
        System.err.println("The file chainlinktemplate.fxml was not found!");
      }
    }

    /**
     * This method is executed when the users clicks on the button to edit a Chain Link. It opens
     * the editor window.
     */
    @FXML
    private void handleEdit() {
      procedureLayoutPresenter.setEditPresenter(chainLinkPresenter.getChainLinkEditPresenter());
    }

    /**
     * This method is executed when the users clicks on the button to export the content of a chain
     * link. It writes the content to the disk in a file specified by the user.
     */
    @FXML
    private void handleExport() {
      // opens save dialog to choose where you want to save the file
      FileChooser chooser = new FileChooser();
      chooser.setTitle("Choose export location");
      File exportLocation = PresenterManager.showSaveFileChooser(chooser);
      if (exportLocation == null) {
        return;
      }
      // initiates exporting the chain link content
      chainLinkPresenter.export(exportLocation);
    }

    /**
     * This method is executed when the users clicks on the button to hide a Chain Link. It
     * minimizes the Chain Link in the Split-Pane.
     */
    @FXML
    private void handleHideChainLink() {
      toggleHide();
    }

    /**
     * Initializes the view-object created by the FXMLLoader. Sets the chain item name and adds the
     * header view object to the displayed view.
     */
    @FXML
    private void initialize() {
      // titleLabel.setText("Kodierungsstufe: " + chainLinkPresenter.getName());

      // add language support
      informationTitledPane
          .textProperty()
          .bind(I18N.createStringBinding("chainlinktemplate.information.header"));
      exportButton.textProperty().bind(I18N.createStringBinding("chainlinktemplate.exportbutton"));

      // replace horizontal lines with vertical ones
      String chainLinkName = chainLinkPresenter.getName();
      chainLinkName = chainLinkName.replace("-", "|");

      // enable language support and display the name
      hiddenLabel
          .textProperty()
          .bind(
              I18N.createStringBinding("chainlinktemplate.hiddenlabel")
                  .concat(" " + chainLinkName));

      // bind the visibility to the managed property and hide the hiddenPane
      chainLinkPane.visibleProperty().bind(chainLinkPane.managedProperty());
      hiddenPane.visibleProperty().bind(hiddenPane.managedProperty());
      hiddenPane.setManaged(false);

      ChainLinkHeaderPresenter header = chainLinkPresenter.getChainLinkHeader();

      // display the header if available
      if (header != null) {
        informationBox.setCenter(header.getView());
      }

      // display the chain link content
      chainLinkContainer.setCenter(chainLinkPresenter.getView());

      // disables the edit button if there is no edit presenter for the chain link
      if (chainLinkPresenter.getChainLinkEditPresenter() == null) {
        editButton.setDisable(true);
      }
    }

    /**
     * Toggles between the flag isHidden between true and false, while changing the icon
     * accordingly.
     */
    private void toggleHide() {
      if (hidden) {
        // change the icon
        hideButtonIcon.setIconLiteral(shownIcon);

        // reverse max width
        this.setMaxWidth(chainLinkPane.getMaxWidth());

        // show chain item content and hide the hidden pane
        hiddenPane.setManaged(false);
        chainLinkPane.setManaged(true);
        hidden = false;

      } else {
        // change the icon
        hideButtonIcon.setIconLiteral(hiddenIcon);

        // force the chain item to be thinner
        this.setMaxWidth(hiddenPane.getPrefWidth());

        // hide chain item content and show the hidden pane
        hiddenPane.setManaged(true);
        chainLinkPane.setManaged(false);
        hidden = true;
      }
    }
  }

  /** The scrollable Pane in which the split pane is nested. */
  @FXML private ScrollPane viewScrollPane;

  /** The split pane in which the Chain Links are displayed. */
  @FXML private ChainSplitPane chainSplitPane;

  /** The reference to the first ChainLinkPresenter. */
  private ChainLinkPresenter firstChainLinkPresenter;

  /** The reference to the ProcedureLayoutPresenter. */
  private ProcedureLayoutPresenter procedureLayoutPresenter;

  /** The dividers of the SplitPane chainSplitPane. */
  private ObservableList<Divider> dividers;

  /**
   * Creates a new ChainPresenter with a reference to the first ChainLinkPresenter and a
   * ProcedureLayoutPresenter.
   *
   * @param chainLinkPresenter : The reference to the first ChainLinkPresenter.
   * @param procedureLayoutPresenter : The reference to a ProcedureLayoutPresenter.
   */
  public ChainPresenter(
      ChainLinkPresenter chainLinkPresenter, ProcedureLayoutPresenter procedureLayoutPresenter) {
    
    this.firstChainLinkPresenter = chainLinkPresenter;
    this.procedureLayoutPresenter = procedureLayoutPresenter;
    
    // loads the template file
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chainlayout.fxml"));
    fxmlLoader.setController(this);
    try {
      viewScrollPane = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method creates the view for a given Procedure-Plugin.
   *
   * @param activeProcedure the active Procedure-Plugin.
   */
  public void createChainView(ProcedurePlugin activeProcedure, boolean encoding) {
    ChainLinkPresenter chainLinkPresenter = 
        encoding ? firstChainLinkPresenter : activeProcedure.getChainTail();
    
    // add a newly created chain item for each ChainLinkPresenter
    while (chainLinkPresenter != null) {
      chainSplitPane.getItems().add(new ChainItem(chainLinkPresenter));
      
      chainLinkPresenter = encoding ? chainLinkPresenter.getNext() : chainLinkPresenter.getPrev();
    }

    dividers = chainSplitPane.getDividers();
    
    double positionDelta = 1d / (dividers.size() + 1d);
    
    //set initial divider position, to be evenly distributed
    for (int i = 0; i < dividers.size(); i++) {

      Divider divider = dividers.get(i);
      
      divider.setPosition(positionDelta * (double) (i + 1));
      
    }
  }

  @Override
  public ScrollPane getView() {
    return viewScrollPane;
  }

  /** Initializes the view-object created by the FXMLLoader. */
  @FXML
  private void initialize() {}

  /**
   * This method jumps to the Chain Link with the given ID.
   *
   * @param id : The ID of the Chain Link.
   */
  public void jumpToChainLink(int id) {
    // This sets the position of the vertical dividers between the Chain Links.
    // TODO: expand the chain item

    Node chainItem = chainSplitPane.getItems().get(id);

    // calculate the hValue for the view port of the scroll pane
    double scrollPanewidth = viewScrollPane.getContent().getBoundsInLocal().getWidth();
    double x =
        (chainItem.getParent().getBoundsInParent().getMaxX()
                + chainItem.getParent().getBoundsInParent().getMinX())
            / 2.0;
    double viewPortWdith = viewScrollPane.getViewportBounds().getWidth();
    viewScrollPane.setHvalue(
        viewScrollPane.getHmax() * ((x - 0.5 * viewPortWdith) / (scrollPanewidth - viewPortWdith)));
  }
}
