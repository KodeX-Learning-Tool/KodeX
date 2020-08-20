/** */
package kodex.presenter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Locale;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxService;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.service.finder.NodeFinder;

import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kodex.model.DefaultSettings;
import kodex.model.I18N;

/**
 * Tests for the SettingsPresenter class.
 *
 * @author Leonhard Kraft
 */
@ExtendWith(ApplicationExtension.class)
class SettingsPresenterTest {

  private PresenterManager pm;
  private SettingsPresenter settingsPresenter;

  @Start
  void setUp(Stage stage) {
    pm = Mockito.mock(PresenterManager.class);

    settingsPresenter = new SettingsPresenter(pm);

    BorderPane layout = new BorderPane();
    layout.setCenter(settingsPresenter.getView());
    stage.setScene(new Scene(layout));
    stage.show();
  }

  @AfterAll
  static void celanUp() {
    DefaultSettings.getInstance().reset();
  }

  /** Test method for {@link kodex.presenter.SettingsPresenter#handleBrowsePath()}. */
  @Test
  void testHandleBrowsePath() {

    // DirectoryChooser is final and showDirectoryChooser is static so we can't mock them
  }

  @Test
  void testLanguageLoaded() {
    Locale language = I18N.getLocale();

    FxAssert.verifyThat(
        "#languageChoiceBox",
        (ChoiceBox<?> choiceBox) ->
            choiceBox.getSelectionModel().getSelectedItem().equals(language));
  }

  @Test
  void testLanguageChange(FxRobot robot) {

    NodeFinder nodeFinder = FxService.serviceContext().getNodeFinder();
    ChoiceBox<Locale> choiceBox =
        (ChoiceBox<Locale>) nodeFinder.lookup("#languageChoiceBox").query();

    for (Locale locale : choiceBox.getItems()) {
      robot.clickOn("#languageChoiceBox").clickOn(locale.getDisplayLanguage(locale));

      assertEquals(I18N.getLocale(), locale);
    }
  }

  /** Test method for {@link kodex.presenter.SettingsPresenter#handleChangeLanguage()}. */
  @Test
  void testHandleChangeLanguage() {
    fail("Not yet implemented");
  }

  /** Test method for {@link kodex.presenter.SettingsPresenter#handleRestoreDefaultSettings()}. */
  @Test
  void testHandleRestoreDefaultSettings() {
    fail("Not yet implemented");
  }

  /** Test method for {@link kodex.presenter.SettingsPresenter#handleSubmitPort()}. */
  @Test
  void testHandleSubmitPort() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link kodex.presenter.Presenter#Presenter(kodex.presenter.PresenterManager,
   * java.lang.String)}.
   */
  @Test
  void testPresenter() {
    fail("Not yet implemented");
  }

  /** Test method for {@link kodex.presenter.Presenter#getView()}. */
  @Test
  void testGetView() {
    fail("Not yet implemented");
  }

  /** Test method for {@link kodex.presenter.Presenter#onExit()}. */
  @Test
  void testOnExit() {
    fail("Not yet implemented");
  }

  /** Test method for {@link kodex.presenter.Presenter#loadFXML(java.lang.String)}. */
  @Test
  void testLoadFXML() {
    fail("Not yet implemented");
  }
}
