package kodex.standardplugins.rle.presenter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import kodex.standardplugins.rle.TextRLEProcedurePlugin;

/**
 * This test class is responsible for the RLEImportPresenter class.
 * 
 * @author Raimon Gramlich
 */
@ExtendWith(ApplicationExtension.class)
class RLEImportPresenterTest {

  private RLEImportPresenter rleImportPresenter;
  private TextRLEProcedurePlugin textRLEProcedurePlugin;
  
  @Start
  void setUp(Stage stage) throws Exception {
    textRLEProcedurePlugin = new TextRLEProcedurePlugin();
    textRLEProcedurePlugin.createImportPresenter();
    rleImportPresenter = new RLEImportPresenter(textRLEProcedurePlugin);

    BorderPane layout = new BorderPane();
    layout.setCenter(rleImportPresenter.getView());
    stage.setScene(new Scene(layout));
    stage.show();
  }
  
  @AfterEach
  void tearDown() throws Exception {
    textRLEProcedurePlugin = null;
    rleImportPresenter = null;
  }

  /**
   * Test method for {@link kodex.standardplugins.rle.presenter.RLEImportPresenter#getView()}.
   */
  @Test
  void testGetView() {
    assertTrue(rleImportPresenter.getView() instanceof AnchorPane);
  }

  /**
   * Test method for {@link kodex.standardplugins.rle.presenter.RLEImportPresenter
   * #handleDecodeImport()}.
   * @throws InvalidImportException if the imported file is invalid
   */
  @Test
  void testHandleDecodeImport() throws InvalidImportException {
    // test file
    File file = new File(RLEImportPresenterTest.class.getResource("test_rle.txt").getPath());
    
    // spy on ColorImageImportPresenter to return test file instead of opening a FileChooser
    RLEImportPresenter spy = Mockito.spy(rleImportPresenter);
    Mockito.doReturn(file).when(spy).importFile(Mockito.eq(false), Mockito.any(ArrayList.class));
    ProcedureLayoutPresenter plp = Mockito.mock(ProcedureLayoutPresenter.class);
    spy.setLayoutPresenter(plp);

    // verify if importing succeeded
    Mockito.doNothing().when(plp).switchToChainPresenter(false);
    spy.handleDecodeImport();
    Mockito.verify(plp, Mockito.times(1)).switchToChainPresenter(false);
  }

  /**
   * Test method for {@link kodex.standardplugins.rle.presenter.RLEImportPresenter
   * #handleEncodeImport()}.
   * @throws InvalidImportException if the imported file is invalid
   */
  @Test
  void testHandleEncodeImport() throws InvalidImportException {
    // test file
    File file = new File(RLEImportPresenterTest.class.getResource("test.txt").getPath());
    
    // spy on ColorImageImportPresenter to return test file instead of opening a FileChooser
    RLEImportPresenter spy = Mockito.spy(rleImportPresenter);
    Mockito.doReturn(file).when(spy).importFile(Mockito.eq(true), Mockito.any(ArrayList.class));
    ProcedureLayoutPresenter plp = Mockito.mock(ProcedureLayoutPresenter.class);
    spy.setLayoutPresenter(plp);
    
    // verify if importing succeeded
    Mockito.doNothing().when(plp).switchToChainPresenter(true);
    spy.handleEncodeImport();
    Mockito.verify(plp, Mockito.times(1)).switchToChainPresenter(true);
  }

  /**
   * Test method for {@link kodex.standardplugins.rle.presenter.RLEImportPresenter
   * #RLEImportPresenter(kodex.plugininterface.ProcedurePlugin)}.
   * 
   * @throws NoSuchFieldException  if a field with the specified name is not found
   * @throws IllegalAccessException  if the Field object is enforcing Java language
   *      access control and the underlying field is inaccessible.
   */
  @Test
  void testRLEImportPresenter() throws NoSuchFieldException, IllegalAccessException {
    RLEImportPresenter rleImportPresenter =
        new RLEImportPresenter(textRLEProcedurePlugin);
    Field plugin = rleImportPresenter.getClass().getSuperclass()
        .getDeclaredField("plugin");
    plugin.setAccessible(true);
    
    assertEquals(textRLEProcedurePlugin, plugin.get(rleImportPresenter));
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
    rleImportPresenter.setLayoutPresenter(plp);
    Field p = rleImportPresenter.getClass().getSuperclass()
        .getDeclaredField("procedureLayoutPresenter");
    p.setAccessible(true);
    
    assertEquals(plp, p.get(rleImportPresenter));
  }

}
