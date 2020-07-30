package kodex.presenter;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kodex.model.SideMenuTypes;

/**
 * This class manages the main presenters and keeps a reference to the root stage. It gets the views
 * for the root stage from its current presenter and side menu presenter.
 *
 * @author Leonhard Kraft
 * @author Yannick Neubert
 * @version 1.0
 */
public class PresenterManager {

  /** The root stage of the application. */
  private Stage rootStage;

  /** The current, active main presenter. */
  private Presenter currentPresenter;

  /** The side menu presenter. */
  private SideMenuPresenter sideMenuPresenter;

  /** The layout for the scene contained in the root stage. */
  private BorderPane rootLayout;

  /**
   * Creates a new PresenterManaegr with a reference to the root stage.
   *
   * @param rootStage The root stage of the application.
   */
  public PresenterManager(Stage rootStage) {

    this.rootStage = rootStage;

    // BorderPane is used, because it allowes to set the center (main page) and left
    // (side menu)
    this.rootLayout = new BorderPane();

    // create Scene with a BorderPane Layout for the root stage
    Scene rootScene = new Scene(rootLayout);
    rootStage.setScene(rootScene);
  }

  /**
   * This method sets the SideMenuPresenter and displays its view.
   *
   * @param sideMenuPresenter The SideMenuPresenter for the displayed side menu.
   */
  public void setSideMenuPresenter(SideMenuPresenter sideMenuPresenter) {

    this.sideMenuPresenter = sideMenuPresenter;
    updateSideMenuView();
  }
  
  /**
   * This method initiates the updating of the main view by setting the center of the BorderPane
   * layout with the view of the current presenter.
   */
  private void updateMainView() {

    this.rootLayout.setCenter(this.currentPresenter.getView());
  }

  /**
   * This method sets the presenter that is currently active and displays its view.
   *
   * @param newPresenter The main presenter that should display its view next.
   */
  public void updatePresenter(Presenter newPresenter) {

    this.currentPresenter = newPresenter;
    updateMainView();
  }

  /**
   * This method initiates the updating of the side menu view by setting the left side of the
   * BorderPane layout with the view of the SideMenuPresenter.
   */
  private void updateSideMenuView() {

    this.rootLayout.setLeft(this.sideMenuPresenter.getView());
  }

  /**
   * This method sets the desired side menu type in the SideMenuPresenter and refreshes the
   * displayed view.
   *
   * @param type The desired side menu type.
   */
  public void updateSideMenuView(SideMenuTypes type) {

    this.sideMenuPresenter.changeSideMenuType(type);
    updateSideMenuView();
  }
}
