package kodex.presenter;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kodex.model.I18N;

@ExtendWith(ApplicationExtension.class)
class IndexPagePresenterTest {

  private PresenterManager pm;
  private PresenterFactory pf;
  private IndexPagePresenter indexPage;

  @Start
  void setUp(Stage stage) throws Exception {
    pm = Mockito.mock(PresenterManager.class);
    pf = Mockito.mock(PresenterFactory.class);

    indexPage = new IndexPagePresenter(pm, pf);

    Field view = indexPage.getClass().getSuperclass().getDeclaredField("view");
    view.setAccessible(true);

    BorderPane layout = new BorderPane();
    layout.setCenter((Node) view.get(indexPage));
    stage.setScene(new Scene(layout));
    stage.show();
  }

  @Test
  void testHeaderLabel() {
    FxAssert.verifyThat(
        ".default-page-title", LabeledMatchers.hasText(I18N.get("indexpage.header")));
  }

  @Test
  void testSearchPrompt() {
    FxAssert.verifyThat("#searchTextField", (TextField search)
        -> search.getPromptText().equals(I18N.get("indexpage.searchbar.prompt")));
  }

  @Test
  void testFilterPrompt() {
    FxAssert.verifyThat("#filterComboBox", (ComboBox<?> comboBox)
        -> comboBox.getPromptText().equals(I18N.get("indexpage.filterbox.prompt")));
  }
}
