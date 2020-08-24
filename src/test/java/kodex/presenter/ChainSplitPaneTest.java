package kodex.presenter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.stage.Stage;

/**
 * Tests for the ChainSplitPane class.
 * 
 * @author Leonhard Kraft
 *
 */
@ExtendWith(ApplicationExtension.class)
class ChainSplitPaneTest {

  private ChainSplitPane csp;
  
  @Start
  void setUp(Stage stage) {

    this.csp = new ChainSplitPane();
  }

  /**
   * Test method for {@link kodex.presenter.ChainSplitPane#createDefaultSkin()}.
   */
  @Test
  void testCreateDefaultSkin() {
    assertTrue(csp.createDefaultSkin() instanceof ChainSplitPaneSkin);
  }

  /**
   * Test method for {@link kodex.presenter.ChainSplitPane#setInitialWidth(double)}.
   */
  @Test
  void testSetInitialWidth() throws Exception {

    double randomWidt = 1000d * Math.random();

    csp.setInitialWidth(randomWidt);

    Field setWidth = csp.getClass().getDeclaredField("initialWidth");
    setWidth.setAccessible(true);

    assertEquals(setWidth.get(csp), randomWidt);
  }

  /**
   * Test method for {@link kodex.presenter.ChainSplitPane#getInitialWidth()}.
   */
  @Test
  void testGetInitialWidth()  throws Exception  {
    
    double randomWidt = 1000d * Math.random();
    
    Field setWidth = csp.getClass().getDeclaredField("initialWidth");
    setWidth.setAccessible(true);
    
    setWidth.set(csp, randomWidt);

    assertEquals(csp.getInitialWidth(), randomWidt);
  }

  /**
   * Test method for {@link kodex.presenter.ChainSplitPane#disableDivider(int)}.
   */
  @Test
  void testDisableDivider(){
    //TODO how can you control the state of a divider?
  }

  /**
   * Test method for {@link kodex.presenter.ChainSplitPane#enableDivider(int)}.
   */
  @Test
  void testEnableDivider(){
    //TODO how can you control the state of a divider?
  }
}
