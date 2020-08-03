package edu.kit.scc.git.kodex;

import edu.kit.scc.git.kodex.presenter.PresenterFactory;
import edu.kit.scc.git.kodex.presenter.PresenterManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class is the entry point of the application and is responsible for starting the JavaFX
 * application.
 *
 * @author Leonhard Kraft
 */
public class MainApp extends Application {

  /**
   * The entry point for this application.
   *
   * @param args not used
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Overrides the method in its superclass. The entry point for the execution of a
   * JavaFX-Application.
   *
   * @param rootStage The main Stage used for this application.
   */
  @Override
  public void start(Stage rootStage) {

    rootStage.setTitle("KodeX");
    rootStage.setResizable(true);

    /*
     * Min. width and height for the window. Source:
     * https://docs.microsoft.com/en-us/windows/win32/uxguide/top-violations
     */
    rootStage.setMinWidth(800);
    rootStage.setMinHeight(600);

    /*
     * Startup width and height of the window. Source:
     * https://docs.microsoft.com/en-us/windows/win32/uxguide/top-violations
     */
    rootStage.setWidth(1280);
    rootStage.setHeight(720);

    PresenterManager.setRootStage(rootStage);
    
    PresenterManager presenterManager = new PresenterManager();

    PresenterFactory factory = new PresenterFactory(presenterManager);

    // create and set SideMenuPresenter for the PresenterManager
    presenterManager.setSideMenuPresenter(factory.createSideMenuPresenter());

    // create and set IndexPagePresenter for the PresenterManager
    presenterManager.updatePresenter(factory.createIndexPagePresenter());

    rootStage.show();
  }
}
