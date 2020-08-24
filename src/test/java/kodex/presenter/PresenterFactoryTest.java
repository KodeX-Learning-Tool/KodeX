package kodex.presenter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javafx.application.Platform;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedurePlugin;

/**
 * Tests for PresenterFactory class.
 *
 * @author Leonhard Kraft
 */
class PresenterFactoryTest {

  private PresenterFactory pf;

  @BeforeEach
  void setUp() {
    PresenterManager pm = Mockito.mock(PresenterManager.class);
    this.pf = new PresenterFactory(pm);
  }

  /**
   * Test method for {@link kodex.presenter.PresenterFactory#createHelpPresenter()}.
   */
  @Test
  void testCreateHelpPresenter() {
    assertTrue(pf.createHelpPresenter() instanceof HelpPresenter);
  }

  /**
   * Test method for {@link kodex.presenter.PresenterFactory#createIndexPagePresenter()}. 
   */
  @Test
  void testCreateIndexPagePresenter() {
    assertTrue(pf.createIndexPagePresenter() instanceof IndexPagePresenter);
  }

  /** 
   * Test method for {@link kodex.presenter.PresenterFactory#createNetworkPresenter()}. 
   */
  @Test
  void testCreateNetworkPresenter() {
    assertTrue(pf.createNetworkPresenter() instanceof NetworkPresenter);
  }

  /** 
   * Test method for {@link kodex.presenter.PresenterFactory#createPluginMenuPresenter()}. 
   */
  @Test
  void testCreatePluginMenuPresenter() {
    Platform
        .runLater(() -> assertTrue(pf.createPluginMenuPresenter() instanceof PluginMenuPresenter));
  }

  /**
   * Test method for {@link
   * kodex.presenter.PresenterFactory#createProcedureLayoutPresenter(
   * kodex.plugininterface.ProcedurePlugin)}.
   */
  @Test
  void testCreateProcedureLayoutPresenter() {
    ProcedurePlugin plugin = Mockito.mock(ProcedurePlugin.class);
    
    Mockito.when(plugin.createImportPresenter()).thenReturn(Mockito.mock(ImportPresenter.class));

    assertTrue(pf.createProcedureLayoutPresenter(plugin) instanceof ProcedureLayoutPresenter);
  }

  /** 
   * Test method for {@link kodex.presenter.PresenterFactory#createSettingsPresenter()}. 
   */
  @Test
  void testCreateSettingsPresenter() {
    assertTrue(pf.createSettingsPresenter() instanceof SettingsPresenter);
  }

  /** 
   * Test method for {@link kodex.presenter.PresenterFactory#createSideMenuPresenter()}. 
   */
  @Test
  void testCreateSideMenuPresenter() {
    assertTrue(pf.createSideMenuPresenter() instanceof SideMenuPresenter);
  }
}
