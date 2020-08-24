package kodex.presenter;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javafx.scene.layout.AnchorPane;
import kodex.plugininterface.ChainLinkEditPresenter;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedurePlugin;

/**
 * Tests for the ProcedureLayoutPresenter class.
 * 
 * @author Leonhard Kraft
 */
class ProcedureLayoutPresenterTest {
  
  private static ProcedureLayoutPresenter plp;
  private static PresenterManager pm;
  private static ProcedurePlugin plugin;
  private static ImportPresenter importPresenter;
  private static AnchorPane someView;
  
  @BeforeAll
  static void setUp() {
    
    importPresenter = Mockito.mock(ImportPresenter.class);
    
    pm = Mockito.mock(PresenterManager.class);
    plugin = Mockito.mock(ProcedurePlugin.class);
    
    Mockito.when(plugin.createImportPresenter()).thenReturn(importPresenter);
    
    plp = new ProcedureLayoutPresenter(pm, plugin);
  }

  /**
   * Test method for {@link
   * kodex.presenter.ProcedureLayoutPresenter#ProcedureLayoutPresenter(
   * kodex.presenter.PresenterManager,
   * kodex.plugininterface.ProcedurePlugin)}.
   * @throws Exception Thrown by refelction.
   */
  @Test
  void testProcedureLayoutPresenterPluginIsSet() throws Exception {
    
    Field activePlugin = plp.getClass().getDeclaredField("activeProcedure");
    activePlugin.setAccessible(true);
    
    assertEquals((ProcedurePlugin) activePlugin.get(plp), plugin);
  }
  
  /**
   * Test method for {@link
   * kodex.presenter.ProcedureLayoutPresenter#ProcedureLayoutPresenter(
   * kodex.presenter.PresenterManager,
   * kodex.plugininterface.ProcedurePlugin)}.
   * 
   * @throws Exception Thrown by refelction.
   */
  @Test
  void testProcedureLayoutPresenterImportIsSet() throws Exception {
    
    Field activePresenter = plp.getClass().getDeclaredField("activePresenter");
    activePresenter.setAccessible(true);
    
    assertEquals((ImportPresenter) activePresenter.get(plp), importPresenter);
    
    Mockito.verify(importPresenter).setLayoutPresenter(plp);
    Mockito.verify(importPresenter).getView();
  }

  /**
   * Test method for {@link
   * kodex.presenter.ProcedureLayoutPresenter#setEditPresenter(
   * kodex.plugininterface.ChainLinkEditPresenter)}.
   * 
   * @throws Exception Thrown by refelction.
   */
  @Test
  void testSetEditPresenter() throws Exception {
    
    ChainLinkEditPresenter editPresenter = Mockito.mock(ChainLinkEditPresenter.class);
    
    plp.setEditPresenter(editPresenter);
    
    Field editor = plp.getClass().getDeclaredField("editor");
    editor.setAccessible(true);
    
    assertNotNull(editor.get(plp));
    
    Field editPresenterField = editor.get(plp).getClass().getDeclaredField("editPresenter");
    editPresenterField.setAccessible(true);
    
    assertEquals(editPresenterField.get(editor.get(plp)), editPresenter);
  }

  /**
   * Test method for {@link
   * kodex.presenter.ProcedureLayoutPresenter#switchToChainPresenter(boolean)}.
   */
  @Test
  void testSwitchToChainPresenter() {
  }
}
