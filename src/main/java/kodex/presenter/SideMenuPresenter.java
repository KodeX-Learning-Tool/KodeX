package kodex.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import kodex.model.I18N;
import kodex.model.SideMenuTypes;

/**
 * This presenter is responsible for the side menu. By clicking on the navigation items the user can
 * switch between different main presenter classes.
 *
 * @author Leonhard Kraft
 * @author Yannick Neubert
 * @version 1.0
 */
public class SideMenuPresenter extends Presenter {

  @FXML private Button indexPageButton;

  @FXML private Button networkPageButton;

  @FXML private Button settingsPageButton;

  @FXML private Button pluginPageButton;

  @FXML private Button helpPageButton;
  
  private Button selectedPage;

  private SideMenuTypes currentType;

  private PresenterFactory presenterFactroy;
  
  private static final String SELECTED_STYLE_CLASS = "side-menu__button--selected";

  /**
   * Creates a new SideMenuPresenter with a reference to the PresenterManager and a
   * PresenterFactory.
   *
   * @param presenterManager The reference to the PresenterManager to switch between different main
   *     presenter classes.
   * @param presenterFactroy The reference to a PresenterFactory which is used to create new
   *     presenters.
   */
  public SideMenuPresenter(PresenterManager presenterManager, PresenterFactory presenterFactroy) {
    super(presenterManager, "sidemenu");
    this.currentType = SideMenuTypes.EXTENDED_MENU;
    this.presenterFactroy = presenterFactroy;
    changeSelectedPage(indexPageButton);
  }
  
  

  /**
   * This method changes the type of the side menu. It can be switched between a standard and a
   * minimized side menu. The type decides what FXML file should be loaded.
   *
   * @param type The type of side menu that should be loaded.
   */
  public void changeSideMenuType(SideMenuTypes type) {

    if (type.equals(currentType)) {
      return;
    }
    
    super.loadFXML(type.toString());
    this.currentType = type;
  }
  
  /**
   * This method is called when ever a button handle method is called.
   * 
   * @param pressed The button that has been pressed.
   */
  private void pageSelect(Button pressed) {
    changeSelectedPage(pressed);
  }
  
  private void changeSelectedPage(Button selected) {
    
    if (selected == null) {
      return;
    }
    
    if (selected.equals(selectedPage)) {
      return;
    }
    
    if (selectedPage != null) {
      selectedPage.getStyleClass().remove(SELECTED_STYLE_CLASS);
    }
    
    this.selectedPage = selected;
    selected.getStyleClass().add(SELECTED_STYLE_CLASS);
  }
  
  private void changeToExtendedMenu() {
    this.presenterManager.updateSideMenuView(SideMenuTypes.EXTENDED_MENU);
  }

  /**
   * This method is executed when the user clicks on the help button. It initiates the change to a
   * HelpPresenter.
   */
  public void handleHelp() {
    this.presenterManager.updatePresenter(this.presenterFactroy.createHelpPresenter());
    changeToExtendedMenu();
    pageSelect(helpPageButton);
  }

  /**
   * This method is executed when the user clicks on the index page button. It initiates the change
   * to a IndexPagePresenter.
   */
  public void handleIndexPage() {
    this.presenterManager.updatePresenter(this.presenterFactroy.createIndexPagePresenter());
    changeToExtendedMenu();
    pageSelect(indexPageButton);
  }

  /**
   * This method is executed when the user clicks on the network button. It initiates the change to
   * a NetworkPresenter.
   */
  public void handleNetwork() {
    this.presenterManager.updatePresenter(this.presenterFactroy.createNetworkPresenter());
    changeToExtendedMenu();
    pageSelect(networkPageButton);
  }

  /**
   * This method is executed when the user clicks on the plugins button. It initiates the change to
   * a PluginPresenter.
   */
  public void handlePlugins() {
    this.presenterManager.updatePresenter(this.presenterFactroy.createPluginMenuPresenter());
    changeToExtendedMenu();
    pageSelect(pluginPageButton);
  }

  /**
   * This method is executed when the user clicks on the settings button. It initiates the change to
   * a SettingsPresenter.
   */
  public void handleSettings() {
    this.presenterManager.updatePresenter(this.presenterFactroy.createSettingsPresenter());
    changeToExtendedMenu();
    pageSelect(settingsPageButton);
  }

  /** Initializes the view-object created by the FXMLLoader. */
  @FXML
  private void initialize() {
    indexPageButton.textProperty().bind(I18N.createStringBinding("sidemenu.indexpagebutton"));
    networkPageButton.textProperty().bind(I18N.createStringBinding("sidemenu.networkpagebutton"));
    settingsPageButton.textProperty().bind(I18N.createStringBinding("sidemenu.settingspagebutton"));
    pluginPageButton.textProperty().bind(I18N.createStringBinding("sidemenu.pluginpagebutton"));
    helpPageButton.textProperty().bind(I18N.createStringBinding("sidemenu.helppagebutton"));
  }
}
