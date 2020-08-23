package kodex.presenter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kodex.model.I18N;

/**
 * Contains tests for the NetworkPresenter class.
 * 
 * @author Leonhard Kraft
 */
@ExtendWith(ApplicationExtension.class)
class NetworkPresenterTest {
  
  private PresenterManager pm;
  private NetworkPresenter networkPage;

  @Start
  void setUp(Stage stage) {
    pm = Mockito.mock(PresenterManager.class);

    networkPage = new NetworkPresenter(pm);

    BorderPane layout = new BorderPane();
    layout.setCenter(networkPage.getView());
    stage.setScene(new Scene(layout));
    stage.show();
  }

  @Test
  void testHeaderLabel() {
    FxAssert.verifyThat(
        "#header", LabeledMatchers.hasText(I18N.get("networkpage.header")));
  }
  
  @Test
  void testIpConnectLbl() {
    FxAssert.verifyThat(
        "#ipConnectLbl", LabeledMatchers.hasText(I18N.get("networkpage.connect.ip.lbl")));
  }
  
  @Test
  void testIpConnectTextField() {
    FxAssert.verifyThat(
        "#header", (TextField text) ->
        text.getPromptText().equals(I18N.get("networkpage.connect.ip.prompt")));
  }
  
  @Test
  void testPortConnectLbl() {
    FxAssert.verifyThat(
        "#portConnectLbl", LabeledMatchers.hasText(I18N.get("networkpage.connect.port.lbl")));
  }
  
  @Test
  void testPortConnectTextField() {
    FxAssert.verifyThat(
        "#header", (TextField text) ->
        text.getPromptText().equals(I18N.get("networkpage.connect.port.prompt")));
  }
  
  @Test
  void testConnectButton() {
    FxAssert.verifyThat(
        "connectButton", LabeledMatchers.hasText(I18N.get("networkpage.connect.button")));
  }
  
  @Test
  void testIpHostLbl() {
    FxAssert.verifyThat(
        "#ipHostLbl", LabeledMatchers.hasText(I18N.get("networkpage.host.ip.lbl")));
  }
  
  @Test
  void testPortHostLbl() {
    FxAssert.verifyThat(
        "#portHostLbl", LabeledMatchers.hasText(I18N.get("networkpage.host.port.lbl")));
  }
  
  @Test
  void testHostButton() {
    FxAssert.verifyThat(
        "#hostButton", LabeledMatchers.hasText(I18N.get("networkpage.host.button")));
  }
  
  @Test
  void testCancelButton() {
    FxAssert.verifyThat(
        "#cancelButton", LabeledMatchers.hasText(I18N.get("networkpage.host.button.cancel")));
  }
}
