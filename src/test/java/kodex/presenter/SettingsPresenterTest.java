package kodex.presenter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;
import java.util.Random;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxService;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;
import org.testfx.service.finder.NodeFinder;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

  @Stop
  static void cleanUp() throws Exception {
    DefaultSettings.getInstance().reset();
  }

  /**
   * Test method for {@link kodex.presenter.SettingsPresenter#handleBrowsePath()}. 
   */
  @Disabled("Not on FxThread exception is thrown when executing.")
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

      assertEquals(locale, I18N.getLocale());
    }
  }

  /** 
   * Test method for {@link kodex.presenter.SettingsPresenter#handleRestoreDefaultSettings()}. 
   */
  @Disabled
  @Test
  void testHandleRestoreDefaultSettings() {
    // TODO: How can we catch an alert?
  }

  /**
   * Test method for {@link kodex.presenter.SettingsPresenter#handleSubmitPort()}. 
   */
  @Test
  void testHandleSubmitPort(FxRobot robot) {

    NodeFinder nodeFinder = FxService.serviceContext().getNodeFinder();
    TextField portTextField = (TextField) nodeFinder.lookup("#portTextField").query();

    Random r = new Random();

    int randomInt = r.nextInt(65535);

    portTextField.setText(Integer.toString(randomInt));

    robot.clickOn("#submitPortButton");

    assertEquals(randomInt, DefaultSettings.getPort());
  }
  
  @Disabled("TestFx can't handle alerts.")
  @Test
  void testPortToHigh(FxRobot robot) {
    // TODO: How to handle alerts?
    NodeFinder nodeFinder = FxService.serviceContext().getNodeFinder();
    TextField portTextField = (TextField) nodeFinder.lookup("#portTextField").query();

    portTextField.clear();

    int port = 65536;

    int previousPort = DefaultSettings.getPort();

    robot.clickOn("#portTextField").write(Integer.toString(port));

    robot.clickOn("#submitPortButton");

    assertEquals(previousPort, DefaultSettings.getPort());
  }

  @Test
  void testPortToLow(FxRobot robot) {
    // TODO: How to handle alerts?
    NodeFinder nodeFinder = FxService.serviceContext().getNodeFinder();
    TextField portTextField = (TextField) nodeFinder.lookup("#portTextField").query();

    portTextField.clear();

    int port = -1;

    robot.clickOn("#portTextField").write(Integer.toString(port));

    robot.clickOn("#submitPortButton");

    // you need to type - and 1 so - is not accepted but 1
    assertEquals(1, DefaultSettings.getPort());
  }
  
  @Disabled("TestFx can't handle alerts.")
  @Test
  void testPortNotANumber(FxRobot robot) {
    // TODO: How to handle alerts?
    NodeFinder nodeFinder = FxService.serviceContext().getNodeFinder();
    TextField portTextField = (TextField) nodeFinder.lookup("#portTextField").query();

    portTextField.clear();

    int previousPort = DefaultSettings.getPort();

    robot.clickOn("#portTextField").write("SomeText");

    robot.clickOn("#submitPortButton");

    assertEquals(previousPort, DefaultSettings.getPort());
  }

  @Test
  void testHeaderLabel() {
    FxAssert.verifyThat(
        "#lblHeader", (Label header) -> header.getText().equals(I18N.get("settingspage.header")));
  }

  @Test
  void testLanguageHeaderLabel() {
    FxAssert.verifyThat(
        "#lblLanguage",
        (Label header) -> header.getText().equals(I18N.get("settingspage.language.header")));
  }

  @Test
  void testDefaultPortHeaderLabel() {
    FxAssert.verifyThat(
        "#lblDefaultPort",
        (Label header) -> header.getText().equals(I18N.get("settingspage.defaultport.header")));
  }

  @Test
  void testDefaultPortDescriptionLabel() {
    FxAssert.verifyThat(
        "#lblDefaultPortDescription",
        (Label header) -> header.getText().equals(I18N.get("settingspage.defaultport.lbl")));
  }

  @Test
  void testDefaultPathHeaderLabel() {
    FxAssert.verifyThat(
        "#lblDefaultPath",
        (Label header) -> header.getText().equals(I18N.get("settingspage.defaultpath.header")));
  }

  @Test
  void testDefaultPathDescriptionLabel() {
    FxAssert.verifyThat(
        "#lblDefaultPathDescription",
        (Label header) -> header.getText().equals(I18N.get("settingspage.defaultpath.lbl")));
  }

  @Test
  void testResetButtonLabel() {
    FxAssert.verifyThat(
        "#resetButton",
        (Button button) -> button.getText().equals(I18N.get("settingspage.resetbutton")));
  }
}
