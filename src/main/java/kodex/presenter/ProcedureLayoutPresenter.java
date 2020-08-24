package kodex.presenter;

import java.io.IOException;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import kodex.exceptions.AlertWindowException;
import kodex.exceptions.InvalidInputException;
import kodex.model.I18N;
import kodex.plugininterface.ChainLinkEditPresenter;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedurePlugin;

/**
 * This Presenter is responsible for the Procedure Plugin page. It manages a number of Presenters,
 * each responsible for a unique part in creating a Coding Procedure.
 *
 * @author Yannick Neubert
 * @author Raimon Gramlich
 * @version 1.0
 */
public class ProcedureLayoutPresenter extends Presenter {

  /**
   * This class represents the editor-window which slides in when the respective button is clicked.
   * It loads a template via fxml file and fills it with the view-object it has gotten from an
   * instance of the ChainLinkEditPresenter class.
   *
   * @author Raimon Gramlich
   * @version 1.0
   */
  private class Editor extends AnchorPane {

    /** The VBox which displays the concrete view for a chain link.. */
    @FXML private VBox editItemsBox;

    /** The submit button for applying the changes to the chain if they are valid. */
    @FXML private Button submitButton;

    /** The header label of the editor. */
    @FXML private Label editLabel;

    /** Contains the actual edit utils. */
    @FXML private BorderPane editContent;

    /** The Constant ANIMATION_LENGTH in millis. */
    private static final int ANIMATION_LENGTH = 500;

    /**
     * The Constant EDITOR_RATIO which determines the ratio between procedurelayout width and editor
     * width.
     */
    private static final int EDITOR_RATIO = 5;

    /** The Constant NORMAL_TRANSITION_RATE plays the transition at a normal rate. */
    private static final int NORMAL_TRANSITION_RATE = 1;

    /** The Constant REVERSE_TRANSITION_RATE plays the transition at a normal rate in reverse. */
    private static final int REVERSE_TRANSITION_RATE = -1;

    /** The TranslateTransition for sliding the editor window in and out. */
    private TranslateTransition editorTranslation;

    /** Whether the editor is shown. */
    private boolean editorShown = false;

    /** Whether the editor is moving at the moment. */
    private boolean moving = false;

    private ChainLinkEditPresenter editPresenter;

    /**
     * Creates a new Editor with a reference to a ChainLinkEditPresenter.
     *
     * @param editPresenter : The reference to the ChainLinkEditPresenter.
     */
    Editor() {
      // loads the template file
      String fileName = "editlayout.fxml";

      FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
      loader.setController(this);
      loader.setRoot(this);

      try {
        loader.load();
      } catch (IOException exc) {

        PresenterManager.showAlertDialog(
            AlertType.ERROR,
            I18N.get("alert.title.error"),
            I18N.get("alert.load.failed"),
            "Failed creating Editor-view with " + fileName + ".");
      }
    }

    /**
     * This method is executed if the user clicks on the button to close the Edit-Window. It closes
     * the Edit-Window.
     */
    @FXML
    private void handleCloseEditor() {
      hideEditor();
    }

    /** This method is executed if the user clicks on the button to submit the changes. 
     * @throws InvalidInputException if the user input is invalid
     */
    @FXML
    private void handleSubmit() {
      try {
        editPresenter.handleSubmit();
      } catch (AlertWindowException e) {
        PresenterManager.showAlertDialog(e.getType(), e.getTitle(), e.getHeader(), e.getContent());
      }
    }

    /** Plays the slide in animation at a normal rate in reverse. */
    public void hideEditor() {
      if (!moving && editorShown) {
        moving = true;
        editorTranslation.setRate(REVERSE_TRANSITION_RATE);
        editorTranslation.setOnFinished(
            event -> {
              editorShown = false;
              moving = false;
            });
        editorTranslation.play();
      }
    }

    /** Initializes the view-object created by the FXMLLoader. Sets up a TranslateTransition. */
    @FXML
    private void initialize() {
      // language support
      editLabel.textProperty().bind(I18N.createStringBinding("editlayout.edit.lbl"));
      submitButton.textProperty().bind(I18N.createStringBinding("editlayout.submitbutton"));

      // calculate editor width from total procedure view width
      // TODO: maybe use the prefered width of the editor as
      // long as it is smaller than a constant max width?
      int editorWidth = (int) Math.round(procedureRootPane.getWidth() / EDITOR_RATIO);

      // set size and hide the pane outside the visible area
      this.setPrefWidth(editorWidth);
      this.setPrefHeight(overlayPane.getHeight());
      this.setTranslateX(overlayPane.getWidth());

      // creates a new TranslateTransition to move the window on the x-axis
      editorTranslation = new TranslateTransition(Duration.millis(ANIMATION_LENGTH), this);

      editorTranslation.fromXProperty().bind(overlayPane.widthProperty().add(editorWidth));
      editorTranslation.toXProperty().bind(overlayPane.widthProperty().subtract(editorWidth));
    }

    /** Plays the slide in animation at normal rate. */
    public void showEditor() {
      if (!moving && !editorShown) {
        moving = true;
        editorTranslation.setRate(NORMAL_TRANSITION_RATE);
        editorTranslation.setOnFinished(
            event -> {
              editorShown = true;
              moving = false;
            });
        editorTranslation.play();
      }
    }

    /**
     * Sets the editor view.
     *
     * @param editorView the new editor view
     */
    private void setEditorPresenter(ChainLinkEditPresenter editorPresenter) {
      editPresenter = editorPresenter;

      // gets and adds the concrete editor items for the chain link
      editContent.setCenter(editorPresenter.getView());
    }
  }

  /**
   * This class represents a single overview item. It loads a template via fxml file and fills it
   * with the information it has gotten from an instance of the ChainLinkPresenter class.
   *
   * @author Raimon Gramlich
   * @version 1.0
   */
  private class OverviewItem extends Button {

    /** An icon or a thumb nail representing a chain link. */
    @FXML private ImageView thumbnail;

    /** The id of the chain link it represents in the chain. */
    private int id;

    private ChainLinkPresenter chainLinkPresenter;

    /**
     * Creates a new Procedure-Layout-Presenter with a reference to a Presenter-Manager and a
     * Procedure Plugin.
     *
     * @param chainLinkPresenter : The reference to the ChainLinkPresenter.
     * @param id : The id of the ChainLink the item is representing.
     */
    OverviewItem(ChainLinkPresenter chainLinkPresenter, int id) {
      this.id = id;
      this.chainLinkPresenter = chainLinkPresenter;

      // loads the template file
      String fileName = "overviewbuttontemplate.fxml";

      FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
      loader.setController(this);
      loader.setRoot(this);

      try {
        loader.load();
      } catch (IOException exc) {
        PresenterManager.showAlertDialog(
            AlertType.ERROR,
            I18N.get("alert.title.error"),
            I18N.get("alert.load.failed"),
            "Failed creating Overview item for "
                + chainLinkPresenter.getName()
                + " with "
                + fileName
                + ".");
      }
    }

    /**
     * This method is executed if the user clicks on a overview item. It jumps to the chosen chain
     * link in the chain view.
     */
    @FXML
    private void handleJumpTo() {
      ((ChainPresenter) activePresenter).jumpToChainLink(id);
    }

    /**
     * Initializes the view-object created by the FXMLLoader. If there is no image to display, it
     * sets a String as the text of the overview item instead.
     */
    @FXML
    private void initialize() {

      thumbnail.setPreserveRatio(true);
      thumbnail.fitHeightProperty().bind(this.heightProperty().subtract(16));
      thumbnail.fitWidthProperty().bind(this.widthProperty().subtract(16));
    }

    public boolean represents(ChainLinkPresenter link) {
      return this.chainLinkPresenter.equals(link);
    }

    public void updateView(Pane view) {
      Platform.runLater(
          () -> thumbnail.setImage(view.snapshot(null, null)));
    }
  }

  /** The HBox displaying the overview items. */
  @FXML private HBox overviewBox;

  /**
   * The BorderPane which displays the overview bar at the top and the view of the active presenter
   * in the center.
   */
  @FXML private BorderPane procedurePane;

  /** The Pane which makes sliding in the Editor possible. */
  @FXML private StackPane overlayPane;

  /** The procedure root pane which contains the whole procedure layout view. */
  @FXML private BorderPane procedureRootPane;

  @FXML private ScrollPane overviewScroll;

  /**
   * The active Coding Procedure. It contains the data for the Coding Chain and the Presenters for
   * editing the data as well as an Import Presenter.
   */
  private ProcedurePlugin activeProcedure;

  /** The active Presenter. Is either an Import Presenter or a ChainPresenter. */
  private IPresenter activePresenter;

  /** The editor displays the concrete edit-view. */
  private Editor editor;
  
  /**
   * Creates a new Procedure-Layout-Presenter with a reference to a Presenter-Manager and a
   * Procedure Plugin.
   *
   * @param pm : The reference to the Presenter-Manager.
   * @param activePlugin : The active Procedure-Plugin.
   */
  public ProcedureLayoutPresenter(PresenterManager pm, ProcedurePlugin activePlugin) {
    super(pm, "procedurelayout");
    this.activeProcedure = activePlugin;

    // creates and displays a new import presenter based on the selected procedure
    this.activePresenter = activeProcedure.createImportPresenter();
    this.activeProcedure.initializeProcedure();
    ((ImportPresenter) activePresenter).setLayoutPresenter(this);
    procedurePane.setCenter(activePresenter.getView());
  }

  /** This creates and adds the items to the overview bar. */
  private void addOverviewItems(boolean encoding) {

    ChainLinkPresenter chainLinkPresenter =
        encoding ? activeProcedure.getChainHead() : activeProcedure.getChainTail();

    Double padding = overviewBox.getPadding().getTop() + overviewBox.getPadding().getBottom();
    DoubleBinding boxSize = overviewBox.heightProperty().subtract(padding);

    // loops over the chain link presenter and keeps adding newly created overview
    // items
    int i = 0;
    while (chainLinkPresenter != null) {

      OverviewItem overviewItem = new OverviewItem(chainLinkPresenter, i);
      
      overviewItem.minWidthProperty().bind(boxSize);
      overviewItem.maxWidthProperty().bind(boxSize);
      overviewItem.minHeightProperty().bind(boxSize);
      overviewItem.maxHeightProperty().bind(boxSize);

      overviewBox.getChildren().add(overviewItem);

      chainLinkPresenter = encoding ? chainLinkPresenter.getNext() : chainLinkPresenter.getPrev();
      i++;
    }
  }

  /**
   * Used to signal that the view of a ChainLink has changed and the corresponding OverviewItem
   * should be updated.
   *
   * @param link The ChainLink that has changed.
   * @param view The new view.
   */
  public void updateOverviewItem(ChainLinkPresenter link, Pane view) {

    for (Node item : overviewBox.getChildren()) {
      if (((OverviewItem) item).represents(link)) {
        ((OverviewItem) item).updateView(view);
      }
    }
  }

  /**
   * This method sets the Edit-Presenter in order to show an Edit-Window.
   *
   * @param editPresenter : The Edit-Presenter from which the editor gets the concrete view for a
   *     chain link.
   */
  public void setEditPresenter(ChainLinkEditPresenter editPresenter) {
    
    if (editPresenter == null) {
      return;
    }
    
    // create a new editor if it is the first time
    if (editor == null) {
      editor = new Editor();
      overlayPane.getChildren().add(editor);
    }

    editor.setEditorPresenter(editPresenter);
    editor.showEditor();
  }

  /** This method displays the chain view with the fitting items in the overview bar. 
   * 
   *  @throws FailedLoadException if the program failed loading the FXML file
   */
  public void switchToChainPresenter(boolean encoding) {
    // switches the import presenter with a new chain presenter
    try {
      activePresenter = new ChainPresenter(activeProcedure.getChainHead(), this);
    } catch (FailedLoadException e) {
      PresenterManager.showAlertDialog(e.getType(), e.getTitle(), e.getHeader(), e.getContent());
    }
    

    // fills the overview bar with items
    addOverviewItems(encoding);

    // fills the chain view with chain links, then display it
    try {
      ((ChainPresenter) activePresenter).createChainView(activeProcedure, encoding);
      procedurePane.setCenter(activePresenter.getView());
    } catch (FailedLoadException e) {
      PresenterManager.showAlertDialog(e.getType(), e.getTitle(), e.getHeader(), e.getContent());
    }
  }
}
