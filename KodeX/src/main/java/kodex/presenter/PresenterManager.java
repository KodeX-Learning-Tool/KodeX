package kodex.presenter;

import java.util.*;

/**
 * 
 */
public class PresenterManager {

    /**
     * Default constructor
     */
    public PresenterManager() {
    }

    /**
     * 
     */
    private Stage rootStage;

    /**
     * 
     */
    private Presenter currentPresenter;

    /**
     * 
     */
    private SideMenuPresenter sideMenuPresenter;

    /**
     * 
     */
    private AnchorPane mainView;

    /**
     * 
     */
    private AnchorPane sideMenuView;

    /**
     * 
     */
    private BorderPane rootLayout;









    /**
     * @param rootStage
     */
    public void PresenterManager(Stage rootStage) {
        // TODO implement here
    }

    /**
     * 
     */
    public void updateMainView() {
        // TODO implement here
    }

    /**
     * @param type
     */
    public void updateSideMenuView(SideMenuTypes type) {
        // TODO implement here
    }

    /**
     * @param newPresenter
     */
    public void updatePresenter(Presenter newPresenter) {
        // TODO implement here
    }

    /**
     * @param smp
     */
    public void setSideMenuPresenter(SideMenuPresenter smp) {
        // TODO implement here
    }

}