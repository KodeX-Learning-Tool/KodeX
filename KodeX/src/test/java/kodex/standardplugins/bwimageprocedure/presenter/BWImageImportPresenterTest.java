package kodex.standardplugins.bwimageprocedure.presenter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kodex.plugininterface.InvalidImportException;
import kodex.presenter.ProcedureLayoutPresenter;
import kodex.standardplugins.bwimageprocedure.BWImageProcedurePlugin;

/**
 * This test class is responsible for the BWImageImportPresenter class.
 * 
 * @author Raimon Gramlich
 *
 */
@ExtendWith(ApplicationExtension.class)
class BWImageImportPresenterTest {
  
  private BWImageImportPresenter bwImageImportPresenter;
  private BWImageProcedurePlugin bwImageProcedurePlugin;
  
  @Start
  void setUp(Stage stage) throws Exception {
    bwImageProcedurePlugin = new BWImageProcedurePlugin();
    bwImageProcedurePlugin.createImportPresenter();
    bwImageImportPresenter = new BWImageImportPresenter(bwImageProcedurePlugin);

    BorderPane layout = new BorderPane();
    layout.setCenter(bwImageImportPresenter.getView());
    stage.setScene(new Scene(layout));
    stage.show();
  }
  
  @AfterEach
  void tearDown() {
    bwImageProcedurePlugin = null;
    bwImageImportPresenter = null;
  }

  /**
   * Test method for {@link kodex.standardplugins.bwimageprocedure.presenter.BWImageImportPresenter
   * #getView()}.
   */
  @Test
  void testGetView() {
    assertTrue(bwImageImportPresenter.getView() instanceof AnchorPane);
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
    bwImageImportPresenter.setLayoutPresenter(plp);
    Field p = bwImageImportPresenter.getClass().getSuperclass()
        .getDeclaredField("procedureLayoutPresenter");
    p.setAccessible(true);
    
    assertEquals(plp, p.get(bwImageImportPresenter));
  }

}
