package kodex.presenter;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
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
      alert.titleProperty().bind(I18N.createStringBinding("alert.error.title"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.load.failed"));
      alert.setContentText("Failed creating view for " + fileName + ".fxml.");
      PresenterManager.showAlertDialog(alert);
    }
  }
}
