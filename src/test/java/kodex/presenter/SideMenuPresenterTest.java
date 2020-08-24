package kodex.presenter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kodex.model.I18N;
import kodex.model.SideMenuTypes;

/**
 * Tests for the SideMenuPresenter class.
 *
 * @author Leonhard Kraft
 */
@ExtendWith(ApplicationExtension.class)
class SideMenuPresenterTest {

  private SideMenuPresenter smp;
  private PresenterManager pm;
  private PresenterFactory pf;

  @Start
  void setUp(Stage stage) {
    pm = Mockito.mock(PresenterManager.class);
    pf = Mockito.mock(PresenterFactory.class);
    this.smp = new SideMenuPresenter(pm, pf);

    BorderPane layout = new BorderPane();
    layout.setLeft(smp.getView());
    stage.setScene(new Scene(layout));
    stage.show();
  }

  /**
   * Test method for {@link
   * kodex.presenter.SideMenuPresenter#SideMenuPresenter(kodex.presenter.PresenterManager,
   * kodex.presenter.PresenterFactory)}.
   */
  @Test
  void testSideMenuPresenter() {

    FxAssert.verifyThat(
        "#indexPageButton",
        menuButton -> menuButton.getStyleClass().contains("side-menu__button--selected"));
  }

  /**
   * Test method for {@link
   * kodex.presenter.SideMenuPresenter#changeSideMenuType(kodex.model.SideMenuTypes)}.
   */
  @Test
  void testChangeSideMenuType() throws Exception {

    Field currentType = smp.getClass().getDeclaredField("currentType");
    currentType.setAccessible(true);

    assertEquals(SideMenuTypes.EXTENDED_MENU, currentType.get(smp));
  }

  /** 
   * Test method for {@link kodex.presenter.SideMenuPresenter#handleHelp()}. 
   */
  @Test
  void testHandleHelp(FxRobot robot) {

    robot.clickOn("#helpPageButton");
    Mockito.verify(pm).updateSideMenuView(SideMenuTypes.EXTENDED_MENU);

    FxAssert.verifyThat(
        "#helpPageButton",
        menuButton -> menuButton.getStyleClass().contains("side-menu__button--selected"));
  }
  
  @Test
  void testSelectSelected(FxRobot robot) {

    robot.clickOn("#indexPageButton");

    FxAssert.verifyThat(
        "#indexPageButton",
        menuButton -> menuButton.getStyleClass().contains("side-menu__button--selected"));
    
    robot.clickOn("#indexPageButton");
    
    FxAssert.verifyThat(
        "#indexPageButton",
        menuButton -> menuButton.getStyleClass().contains("side-menu__button--selected"));
  }
  
  /** 
   * Test method for {@link kodex.presenter.SideMenuPresenter#handleIndexPage()}. 
   */
  @Test
  void testHandleIndexPage(FxRobot robot) {

    robot.clickOn("#indexPageButton");
    Mockito.verify(pm).updateSideMenuView(SideMenuTypes.EXTENDED_MENU);

    FxAssert.verifyThat(
        "#indexPageButton",
        menuButton -> menuButton.getStyleClass().contains("side-menu__button--selected"));
  }

  /** 
   * Test method for {@link kodex.presenter.SideMenuPresenter#handleNetwork()}. 
   */
  @Test
  void testHandleNetwork(FxRobot robot) {

    robot.clickOn("#networkPageButton");

    Mockito.verify(pm).updateSideMenuView(SideMenuTypes.EXTENDED_MENU);

    FxAssert.verifyThat(
        "#networkPageButton",
        menuButton -> menuButton.getStyleClass().contains("side-menu__button--selected"));
  }

  /** 
   * Test method for {@link kodex.presenter.SideMenuPresenter#handlePlugins()}. 
   */
  @Test
  void testHandlePlugins(FxRobot robot) {

    robot.clickOn("#pluginPageButton");
    Mockito.verify(pm).updateSideMenuView(SideMenuTypes.EXTENDED_MENU);

    FxAssert.verifyThat(
        "#pluginPageButton",
        menuButton -> menuButton.getStyleClass().contains("side-menu__button--selected"));
  }

  /** 
   * Test method for {@link kodex.presenter.SideMenuPresenter#handleSettings()}. 
   */
  @Test
  void testHandleSettings(FxRobot robot) {

    robot.clickOn("#settingsPageButton");
    Mockito.verify(pm).updateSideMenuView(SideMenuTypes.EXTENDED_MENU);

    FxAssert.verifyThat(
        "#settingsPageButton",
        menuButton -> menuButton.getStyleClass().contains("side-menu__button--selected"));
  }

  @Test
  void testButtonLabels() {
    FxAssert.verifyThat(
        "#indexPageButton", LabeledMatchers.hasText(I18N.get("sidemenu.indexpagebutton")));
    FxAssert.verifyThat(
        "#networkPageButton", LabeledMatchers.hasText(I18N.get("sidemenu.networkpagebutton")));
    FxAssert.verifyThat(
        "#settingsPageButton", LabeledMatchers.hasText(I18N.get("sidemenu.settingspagebutton")));
    FxAssert.verifyThat(
        "#pluginPageButton", LabeledMatchers.hasText(I18N.get("sidemenu.pluginpagebutton")));
    FxAssert.verifyThat(
        "#helpPageButton", LabeledMatchers.hasText(I18N.get("sidemenu.helppagebutton")));
  }
}
