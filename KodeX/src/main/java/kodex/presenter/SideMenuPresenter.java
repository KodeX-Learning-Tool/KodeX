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
    
    private SideMenuTypes currentType;

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
        this.currentType = SideMenuTypes.EXTENDED_MENU;
        this.presenterFactroy = presenterFactroy;
    }

    /**
     * This method changes the type of the side menu. It can be switched between a
     * standard and a minimized side menu. The type decides what FXML file should be
     * loaded.
     * 
     * @param type The type of side menu that should be loaded.
     */
    public void changeSideMenuType(SideMenuTypes type) {
        
        if (type.equals(currentType)) {
            return;
        }

        super.loadFXML(type.toString());
    }

    /**
     * This method is executed when the user clicks on the index page button. It
     * initiates the change to a IndexPagePresenter.
     */
    public void handleIndexPage() {
        this.presenterManager.updatePresenter(this.presenterFactroy.createIndexPagePresenter());
        changeToExtendedMenu();
    }

    /**
     * This method is executed when the user clicks on the network button. It
     * initiates the change to a NetworkPresenter.
     */
    public void handleNetwork() {
        this.presenterManager.updatePresenter(this.presenterFactroy.createNetworkPresenter());
        changeToExtendedMenu();
    }

    /**
     * This method is executed when the user clicks on the settings button. It
     * initiates the change to a SettingsPresenter.
     */
    public void handleSettings() {
        this.presenterManager.updatePresenter(this.presenterFactroy.createSettingsPresenter());
        changeToExtendedMenu();
    }

    /**
     * This method is executed when the user clicks on the plugins button. It
     * initiates the change to a PluginPresenter.
     */
    public void handlePlugins() {
        this.presenterManager.updatePresenter(this.presenterFactroy.createPluginMenuPresenter());
        changeToExtendedMenu();
    }

    /**
     * This method is executed when the user clicks on the help button. It initiates
     * the change to a HelpPresenter.
     */
    public void handleHelp() {
        this.presenterManager.updatePresenter(this.presenterFactroy.createHelpPresenter());
        changeToExtendedMenu();
    }
    
    private void changeToExtendedMenu() {
        this.presenterManager.updateSideMenuView(SideMenuTypes.EXTENDED_MENU);
    }
}