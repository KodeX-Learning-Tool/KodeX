package kodex.standardplugins.greyscaleimageprocedure.presenter;

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
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kodex.plugininterface.InvalidImportException;
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
   * Test method for {@link kodex.standardplugins.greyscaleimageprocedure.presenter
   * .GreyScaleImageImportPresenter#handleDecodeImport()}.
   * @throws InvalidImportException if the imported file is invalid
   */
  @Test
  void testHandleDecodeImport() throws InvalidImportException {
    // test file
    File file = new File(GreyScaleImageImportPresenterTest.class.getResource("test.txt").getPath());
    
    // spy on ColorImageImportPresenter to return test file instead of opening a FileChooser
    GreyScaleImageImportPresenter spy = Mockito.spy(greyScaleImageImportPresenter);
    Mockito.doReturn(file).when(spy).importFile(Mockito.eq(false), Mockito.any(ArrayList.class));
    ProcedureLayoutPresenter plp = Mockito.mock(ProcedureLayoutPresenter.class);
    spy.setLayoutPresenter(plp);

    // verify if importing succeeded
    Mockito.doNothing().when(plp).switchToChainPresenter(false);
    spy.handleDecodeImport();
    Mockito.verify(plp, Mockito.times(1)).switchToChainPresenter(false);    
  }

  /**
   * Test method for {@link kodex.standardplugins.greyscaleimageprocedure.presenter
   * .GreyScaleImageImportPresenter#handleEncodeImport()}.
   * @throws InvalidImportException if the imported file is invalid
   */
  @Test
  void testHandleEncodeImport() throws InvalidImportException {
    // test file
    File file = new File(GreyScaleImageImportPresenterTest.class.getResource("test.png").getPath());
    
    // spy on ColorImageImportPresenter to return test file instead of opening a FileChooser
    GreyScaleImageImportPresenter spy = Mockito.spy(greyScaleImageImportPresenter);
    Mockito.doReturn(file).when(spy).importFile(Mockito.eq(true), Mockito.any(ArrayList.class));
    ProcedureLayoutPresenter plp = Mockito.mock(ProcedureLayoutPresenter.class);
    spy.setLayoutPresenter(plp);
    
    // verify if importing succeeded
    Mockito.doNothing().when(plp).switchToChainPresenter(true);
    spy.handleEncodeImport();
    Mockito.verify(plp, Mockito.times(1)).switchToChainPresenter(true);
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
