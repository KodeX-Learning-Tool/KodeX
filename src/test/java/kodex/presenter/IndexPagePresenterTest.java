package kodex.presenter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxService;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.service.finder.NodeFinder;

import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kodex.model.Filter;
import kodex.model.I18N;

/**
 * Test class for IndexPagePresenter.
 *
 * @author Leonhard Kraft
 */
@ExtendWith(ApplicationExtension.class)
class IndexPagePresenterTest {

  private PresenterManager pm;
  private PresenterFactory pf;
  private IndexPagePresenter indexPage;

  @Start
  void setUp(Stage stage) {
    pm = Mockito.mock(PresenterManager.class);
    pf = Mockito.mock(PresenterFactory.class);

    indexPage = new IndexPagePresenter(pm, pf);

    BorderPane layout = new BorderPane();
    layout.setCenter(indexPage.getView());
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
    FxAssert.verifyThat(
        "#searchTextField",
        (TextField search) ->
            search.getPromptText().equals(I18N.get("indexpage.searchbar.prompt")));
  }

  @Test
  void testFilterPrompt() {
    FxAssert.verifyThat(
        "#filterComboBox",
        (ComboBox<?> comboBox) ->
            comboBox.getPromptText().equals(I18N.get("indexpage.filterbox.prompt")));
  }
  
  @Test
  void testSearch(FxRobot robot) {
    
    final String SEARCH_TEXT = "verfahren";
    
    robot.clickOn("#searchTextField").write(SEARCH_TEXT);
    FxAssert.verifyThat(
        "#searchTextField",
        (TextField search) ->
            search.getText().equals(SEARCH_TEXT));
  }
  
  @Test
  void testFilter(FxRobot robot) {
    
    NodeFinder nodeFinder = FxService.serviceContext().getNodeFinder();
    ComboBox<Filter> comboBox = (ComboBox<Filter>) nodeFinder.lookup("#filterComboBox").query();
    
    for (Filter filter : comboBox.getItems()) {
      
      robot.clickOn("#filterComboBox").clickOn(filter.toString());
      
      assertEquals(comboBox.getSelectionModel().getSelectedItem(), filter);
    }
  }

  /**
   * Test method for {@link
   * kodex.presenter.IndexPagePresenter#IndexPagePresenter(kodex.presenter.PresenterManager,
   * kodex.presenter.PresenterFactory)}.
   * @throws Exception Thrown from reflection.
   */
  @Test
  void testIndexPagePresenter() throws Exception {

    assertNotNull(indexPage.getView());

    Field presenterFactory =
        indexPage.getClass().getDeclaredField("presenterFactory");
    presenterFactory.setAccessible(true);

    assertEquals(presenterFactory.get(indexPage), pf);
  }

  /** 
   * Test method for {@link kodex.presenter.Presenter#getView()}. 
   */
  @Test
  void testGetView() {
    assertNotNull(indexPage.getView());
  }
}