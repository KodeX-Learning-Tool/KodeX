package kodex.standardplugins.greyscaleimageprocedure.presenter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.lang.reflect.Field;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kodex.presenter.ProcedureLayoutPresenter;
import kodex.standardplugins.greyscaleimageprocedure.GreyScaleImageProcedurePlugin;

/**
 * This test class is responsible for the GreyScaleImageImportPresenter class.
 * 
 * @author Raimon Gramlich
 */
@ExtendWith(ApplicationExtension.class)
class GreyScaleImageImportPresenterTest {
  
  private GreyScaleImageImportPresenter greyScaleImageImportPresenter;
  private GreyScaleImageProcedurePlugin greyScaleImageProcedurePlugin;
  
  @Start
  void setUp(Stage stage) throws Exception {
    greyScaleImageProcedurePlugin = new GreyScaleImageProcedurePlugin();
    greyScaleImageProcedurePlugin.createImportPresenter();
    greyScaleImageImportPresenter = new GreyScaleImageImportPresenter(
        greyScaleImageProcedurePlugin);

    BorderPane layout = new BorderPane();
    layout.setCenter(greyScaleImageImportPresenter.getView());
    stage.setScene(new Scene(layout));
    stage.show();
  }

  @AfterEach
  void tearDown() throws Exception {
    greyScaleImageImportPresenter = null;
    greyScaleImageProcedurePlugin = null;
  }

  /**
   * Test method for {@link kodex.standardplugins.greyscaleimageprocedure.presenter
   * .GreyScaleImageImportPresenter#getView()}.
   */
  @Test
  void testGetView() {
    assertTrue(greyScaleImageImportPresenter.getView() instanceof AnchorPane);
  }

  /**
   * Test method for {@link kodex.plugininterface.ImportPresenter
   * #setLayoutPresenter(kodex.presenter.ProcedureLayoutPresenter)}.
   * @throws NoSuchFieldException  if a field with the specified name is not found
   * @throws IllegalAccessException  if the Field object is enforcing Java language
   *      access control and the underlying field is inaccessible.
   */
  @Test
  void testSetLayoutPresenter() throws NoSuchFieldException, IllegalAccessException {
    ProcedureLayoutPresenter plp = Mockito.mock(ProcedureLayoutPresenter.class);
    greyScaleImageImportPresenter.setLayoutPresenter(plp);
    Field p = greyScaleImageImportPresenter.getClass().getSuperclass()
        .getDeclaredField("procedureLayoutPresenter");
    p.setAccessible(true);
    
    assertEquals(plp, p.get(greyScaleImageImportPresenter));
  }

}
