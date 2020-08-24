package kodex.standardplugins.qrcode.presenter;

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
import kodex.standardplugins.qrcode.TextQRCodeProcedurePlugin;

/**
 * This test class is responsible for the TextQRCodeImportPresenter class.
 * 
 * @author Raimon Gramlich
 */
@ExtendWith(ApplicationExtension.class)
class TextQRCodeImportPresenterTest {

  private TextQRCodeImportPresenter textQRCodeImportPresenter;
  private TextQRCodeProcedurePlugin textQRCodeProcedurePlugin;
  
  @Start
  void setUp(Stage stage) throws Exception {
    textQRCodeProcedurePlugin = new TextQRCodeProcedurePlugin();
    textQRCodeProcedurePlugin.createImportPresenter();
    textQRCodeImportPresenter = new TextQRCodeImportPresenter(textQRCodeProcedurePlugin);

    BorderPane layout = new BorderPane();
    layout.setCenter(textQRCodeImportPresenter.getView());
    stage.setScene(new Scene(layout));
    stage.show();
  }

  @AfterEach
  void tearDown() throws Exception {
    textQRCodeProcedurePlugin = null;
    textQRCodeImportPresenter = null;
  }

  /**
   * Test method for {@link kodex.standardplugins.qrcode.presenter.TextQRCodeImportPresenter
   * #getView()}.
   */
  @Test
  void testGetView() {
    assertTrue(textQRCodeImportPresenter.getView() instanceof AnchorPane);
  }

  /**
   * Test method for {@link kodex.standardplugins.qrcode.presenter.TextQRCodeImportPresenter
   * #TextQRCodeImportPresenter(kodex.plugininterface.ProcedurePlugin)}.
   * 
   * @throws NoSuchFieldException  if a field with the specified name is not found
   * @throws IllegalAccessException  if the Field object is enforcing Java language
   *      access control and the underlying field is inaccessible.
   */
  @Test
  void testTextQRCodeImportPresenter() throws NoSuchFieldException, IllegalAccessException {
    TextQRCodeImportPresenter textQRCodeImportPresenter =
        new TextQRCodeImportPresenter(textQRCodeProcedurePlugin);
    Field plugin = textQRCodeImportPresenter.getClass().getSuperclass()
        .getDeclaredField("plugin");
    plugin.setAccessible(true);
    
    assertEquals(textQRCodeProcedurePlugin, plugin.get(textQRCodeImportPresenter));
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
    textQRCodeImportPresenter.setLayoutPresenter(plp);
    Field p = textQRCodeImportPresenter.getClass().getSuperclass()
        .getDeclaredField("procedureLayoutPresenter");
    p.setAccessible(true);
    
    assertEquals(plp, p.get(textQRCodeImportPresenter));
  }

}
