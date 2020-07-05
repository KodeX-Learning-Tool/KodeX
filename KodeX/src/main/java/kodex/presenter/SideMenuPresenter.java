package kodex.presenter;

import kodex.model.SideMenuTypes;

/**
 * This presenter is responsible for the side menu. By clicking on the
 * navigation items the user can switch between different main presenter
 * classes.
 * 
 * @author Leonhard Kraft
 * @author Yannick Neubert
 * 
 * @version 1.0
 */
public class SideMenuPresenter extends Presenter {

    private PresenterFactory presenterFactroy;

    /**
     * Creates a new SideMenuPresenter with a reference to the PresenterManager and
     * a PresenterFactory.
     * 
     * @param presenterManager The reference to the PresenterManager to switch
     *                         between different main presenter classes.
     * @param presenterFactroy The reference to a PresenterFactory which is used to
     *                         create new presenters.
     */
    public SideMenuPresenter(PresenterManager presenterManager, PresenterFactory presenterFactroy) {
        super(presenterManager, "sidemenu");
        this.presenterFactroy = presenterFactroy;
    }

    /**
     * This method changes the type of the side menu. It can be switched between a
     * standard and a minimized side menu.
     * The type decides what FXML file should be loaded.
     * 
     * @param type The type of side menu that should be loaded.
     */
    public void changeSideMenuType(SideMenuTypes type) {
        
        super.loadFXML(type.toString()); //TODO us toString or if clause?
    }

    /**
     * This method is executed when the user clicks on the index page button.
     * It initiates the change to a IndexPagePresenter.
     */
    public void handleIndexPage() {
        this.presenterManager.updatePresenter(this.presenterFactroy.createIndexPagePresenter());
    }

    /**
     * This method is executed when the user clicks on the network button.
     * It initiates the change to a NetworkPresenter.
     */
    public void handleNetwork() {
        this.presenterManager.updatePresenter(this.presenterFactroy.createNetworkPresenter());
    }

    /**
     * This method is executed when the user clicks on the settings button.
     * It initiates the change to a SettingsPresenter.
     */
    public void handleSettings() {
        this.presenterManager.updatePresenter(this.presenterFactroy.createSettingsPresenter());
    }

    /**
     * This method is executed when the user clicks on the plugins button.
     * It initiates the change to a PluginPresenter.
     */
    public void handlePlugins() {
        this.presenterManager.updatePresenter(this.presenterFactroy.createPluginMenuPresenter());
    }

    /**
     * This method is executed when the user clicks on the help button.
     * It initiates the change to a HelpPresenter.
     */
    public void handleHelp() {
        this.presenterManager.updatePresenter(this.presenterFactroy.createHelpPresenter());
    }
}