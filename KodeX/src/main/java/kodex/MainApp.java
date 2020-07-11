package kodex;

import javafx.application.Application;
import javafx.stage.Stage;
import kodex.model.DefaultSettings;
import kodex.model.I18N;
import kodex.model.PluginLoader;
import kodex.presenter.PresenterFactory;
import kodex.presenter.PresenterManager;

/**
 * This class is the entry point of the application and is responsible for
 * starting the JavaFX application.
 * 
 * @author Leonhard Kraft
 */
public class MainApp extends Application {

    /**
     * The entry point for this application.
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Overrides the method of its superclass. This method is called before the
     * start of the JavaFX application. It initializes the PluginLoader and
     * Settings.
     */
    @Override
    public void init() {

        // creates a unused instance of the PluginLoader to initiate the loading of the
        // plugins.
        PluginLoader.getInstance();
        DefaultSettings.getInstance();
    }

    /**
     * Overrides the method in its superclass. The entry point for the execution of
     * a JavaFX-Application.
     * 
     * @param rootStage The main Stage used for this application.
     */
    @Override
    public void start(Stage rootStage) {

        rootStage.setTitle("KodeX");

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
        rootStage.setWidth(1024);
        rootStage.setHeight(768);

        PresenterManager presenterManager = new PresenterManager(rootStage);

        PresenterFactory factory = new PresenterFactory(presenterManager);

        // create and set SideMenuPresenter for the PresenterManager
        presenterManager.setSideMenuPresenter(factory.createSideMenuPresenter());

        // create and set IndexPagePresenter for the PresenterManager
        presenterManager.updatePresenter(factory.createIndexPagePresenter());

        rootStage.show();
    }
}