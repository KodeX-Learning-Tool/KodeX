package kodex.presenter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import kodex.model.I18N;

/**
 * This abstract class is a superclass for all main and side menu presenter. Main presenter are all
 * presenter that extend this class.
 *
 * @author Leonhard Kraft
 * @author Yannick Neubert
 * @version 1.0
 */
public abstract class Presenter implements IPresenter {

  /** The reference to the PresenterManager, needed to transition between main presenter classes. */
  protected PresenterManager presenterManager;

  /*
   * Contains the view of a presenter. This view is automatically set in the
   * constructor.
   */
  protected Pane view;

  /**
   * Creates a new main presenter with a reference to the PresenterManager.
   *
   * @param presenterManager The reference to the PresenterManger, needed for switching the main
   *     presenter.
   * @param fileName The name of the FXML file that should be loaded for the subclass of this
   *     presenter.
   */
  public Presenter(PresenterManager presenterManager, String fileName) {
    this.presenterManager = presenterManager;

    loadFXML(fileName);
  }

  @Override
  public Pane getView() {
    return this.view;
  }
  
  /**
   * This method is called by the PresenterManager when a presenter is no longer needed.
   */
  public void onExit() {
    
  }

  /*
   * Loads the FXML from the given FXML file name.
   */
  protected void loadFXML(String fileName) {

    FXMLLoader loader = new FXMLLoader(Presenter.class.getResource(fileName + ".fxml"));

    // set the controller for the FXMl object to make handling of GUI interactions
    // possible
    loader.setController(this);

    try {
      this.view = loader.load();

    } catch (IOException e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.load.failed"));
      alert.setContentText("Failed creating view for " + fileName + ".fxml.");

      // Create expandable Exception.
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      String exceptionText = sw.toString();

      TextArea textArea = new TextArea(exceptionText);
      textArea.setEditable(false);
      textArea.setWrapText(true);

      textArea.setMaxWidth(Double.MAX_VALUE);
      textArea.setMaxHeight(Double.MAX_VALUE);
      GridPane.setVgrow(textArea, Priority.ALWAYS);
      GridPane.setHgrow(textArea, Priority.ALWAYS);
      
      Label label = new Label("The exception stacktrace was:");

      GridPane expContent = new GridPane();
      expContent.setMaxWidth(Double.MAX_VALUE);
      expContent.add(label, 0, 0);
      expContent.add(textArea, 0, 1);

      // Set expandable Exception into the dialog pane.
      alert.getDialogPane().setExpandableContent(expContent);
      
      
      PresenterManager.showAlertDialog(alert);
    }
  }
}
