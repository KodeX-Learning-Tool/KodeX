package kodex.standardplugins.colorimageprocedure.presenter;

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
import kodex.standardplugins.colorimageprocedure.ColorImageProcedurePlugin;

/**
 * This test class is responsible for the ColorImageImportPresenter class.
 * 
 * @author Raimon Gramlich
 */
@ExtendWith(ApplicationExtension.class)
class ColorImageImportPresenterTest {
  
  private ColorImageImportPresenter colorImageImportPresenter;
  private ColorImageProcedurePlugin colorImageProcedurePlugin;
  
  @Start
  void setUp(Stage stage) throws Exception {
    colorImageProcedurePlugin = new ColorImageProcedurePlugin();
    colorImageProcedurePlugin.createImportPresenter();
    colorImageImportPresenter = new ColorImageImportPresenter(colorImageProcedurePlugin);

    BorderPane layout = new BorderPane();
    layout.setCenter(colorImageImportPresenter.getView());
    stage.setScene(new Scene(layout));
    stage.show();
  }

  @AfterEach
  void tearDown() {
    colorImageProcedurePlugin = null;
    colorImageImportPresenter = null;
  }

  /**
   * Test method for {@link kodex.standardplugins.colorimageprocedure.presenter
   * .ColorImageImportPresenter#getView()}.
   */
  @Test
  void testGetView() {
    assertTrue(colorImageImportPresenter.getView() instanceof AnchorPane);
  }

  /**
   * Test method for {@link kodex.standardplugins.colorimageprocedure.presenter
   * .ColorImageImportPresenter#ColorImageImportPresenter(kodex.plugininterface.ProcedurePlugin)}.
   * 
   * @throws NoSuchFieldException  if a field with the specified name is not found
   * @throws IllegalAccessException  if the Field object is enforcing Java language
   *      access control and the underlying field is inaccessible.
   */
  @Test
  void testColorImageImportPresenter() throws NoSuchFieldException, IllegalAccessException {
    ColorImageImportPresenter colorImageImportPresenter =
        new ColorImageImportPresenter(colorImageProcedurePlugin);
    Field plugin = colorImageImportPresenter.getClass().getSuperclass()
        .getDeclaredField("plugin");
    plugin.setAccessible(true);
    
    assertEquals(colorImageProcedurePlugin, plugin.get(colorImageImportPresenter));
  }

  /**
   * Test method for {@link kodex.plugininterface.ImportPresenter
   * #setLayoutPresenter(kodex.presenter.ProcedureLayoutPresenter)}.
   * 
   * @throws NoSuchFieldException  if a field with the specified name is not found
   * @throws IllegalAccessException  if the Field object is enforcing Java language
   *      access control and the underlying field is inaccessible.
   */
  @Test
  void testSetLayoutPresenter() throws NoSuchFieldException, IllegalAccessException {
    ProcedureLayoutPresenter plp = Mockito.mock(ProcedureLayoutPresenter.class);
    colorImageImportPresenter.setLayoutPresenter(plp);
    Field p = colorImageImportPresenter.getClass().getSuperclass()
        .getDeclaredField("procedureLayoutPresenter");
    p.setAccessible(true);
    
    assertEquals(plp, p.get(colorImageImportPresenter));
  }

}
