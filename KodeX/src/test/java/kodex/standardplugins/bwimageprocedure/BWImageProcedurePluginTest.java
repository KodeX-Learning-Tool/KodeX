package kodex.standardplugins.bwimageprocedure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.scene.Scene;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kodex.pluginutils.model.content.BinaryString;
import kodex.pluginutils.model.content.BlackWhiteImage;
import kodex.pluginutils.presenter.chainlink.BWImageChainLinkPresenter;
import kodex.pluginutils.presenter.chainlink.BinaryStringPresenter;
import kodex.presenter.PresenterManager;
import kodex.presenter.ProcedureLayoutPresenter;
import kodex.standardplugins.bwimageprocedure.presenter.BWImageImportPresenter;
import kodex.standardplugins.colorimageprocedure.ColorImageProcedureInformation;
import kodex.standardplugins.colorimageprocedure.ColorImageProcedurePlugin;

/**
 * This test class is responsible for the BWImageProcedurePlugin class.
 * 
 * @author Raimon Gramlich
 *
 */
@ExtendWith(ApplicationExtension.class)
class BWImageProcedurePluginTest {
  
  private BWImageProcedurePlugin bwImageProcedurePlugin;
  private PresenterManager pm;
  private ProcedureLayoutPresenter procedureLayoutPresenter;
  
  @Start
  void setUp(Stage stage) throws Exception {
    pm = Mockito.mock(PresenterManager.class);
    
    bwImageProcedurePlugin = new BWImageProcedurePlugin();
    
    bwImageProcedurePlugin.createImportPresenter();
    
    procedureLayoutPresenter = new ProcedureLayoutPresenter(pm, bwImageProcedurePlugin);

    BorderPane layout = new BorderPane();
    layout.setCenter(procedureLayoutPresenter.getView());
    stage.setScene(new Scene(layout));
    stage.show();
  }
  
  @AfterEach
  void tearDown() {
    bwImageProcedurePlugin = null;
    pm = null;
    procedureLayoutPresenter = null;
  }

  /**
   * Test method for {@link kodex.standardplugins.bwimageprocedure.BWImageProcedurePlugin
   * #pluginDescriptionProperty()}.
   */
  @Test
  void testPluginDescriptionProperty() {
    String description = "Kodierungsverfahren";
    
    assertEquals(description, bwImageProcedurePlugin.pluginDescriptionProperty().get());
  }

  /**
   * Test method for {@link kodex.standardplugins.bwimageprocedure.BWImageProcedurePlugin
   * #pluginNameProperty()}.
   */
  @Test
  void testPluginNameProperty() {
    String pluginName = "Schwarz & Weiß Bild";
    
    assertEquals(pluginName, bwImageProcedurePlugin.pluginNameProperty().get());
  }

  /**
   * Test method for {@link kodex.standardplugins.bwimageprocedure.BWImageProcedurePlugin
   * #createImportPresenter()}.
   */
  @Test
  void testCreateImportPresenter() {
    assertTrue(bwImageProcedurePlugin.createImportPresenter() instanceof BWImageImportPresenter);
  }

  /**
   * Test method for {@link kodex.standardplugins.bwimageprocedure.BWImageProcedurePlugin
   * #createProcedureInformation()}.
   */
  @Test
  void testCreateProcedureInformation() {
    assertTrue(
        bwImageProcedurePlugin.createProcedureInformation() instanceof BWImageProcedureInformation);
  }

  /**
   * Test method for {@link kodex.standardplugins.bwimageprocedure.BWImageProcedurePlugin
   * #getChainHead()}.
   */
  @Test
  void testGetChainHead() {
    assertTrue(bwImageProcedurePlugin.getChainHead() instanceof BWImageChainLinkPresenter);
  }

  /**
   * Test method for {@link kodex.standardplugins.bwimageprocedure.BWImageProcedurePlugin
   * #getChainTail()}.
   */
  @Test
  void testGetChainTail() {
    assertTrue(bwImageProcedurePlugin.getChainTail() instanceof BinaryStringPresenter);
  }

  /**
   * Test method for {@link kodex.standardplugins.bwimageprocedure.BWImageProcedurePlugin
   * #initDecodeProcedure(kodex.plugininterface.Content)}.
   */
  @Test
  void testInitDecodeProcedure() {
    int width = 3;
    int height = 1;
    int bwUnitLength = 1;

    // initialize header
    HashMap<String, Object> header = new HashMap<>();
    header.put("width", width);
    header.put("height", height);
    header.put("unit-length", bwUnitLength);

    // initialize content
    BinaryString content = new BinaryString("101");
    content.setHeader(header);
    
    // call method to be tested
    bwImageProcedurePlugin.initDecodeProcedure(content);
    
    // get content of last calculated chain link
    BlackWhiteImage image = (BlackWhiteImage) (bwImageProcedurePlugin.getChainHead().getContent());
    
    // check if procedure is initialized
    assertTrue(image.getHeight() == height & image.getWidth() == width
        & image.getColor(width - 2, height - 1).equals(Color.BLACK));
  }

  /**
   * Test method for {@link kodex.standardplugins.bwimageprocedure.BWImageProcedurePlugin
   * #initEncodeProcedure(kodex.plugininterface.Content)}.
   */
  @Test
  void testInitEncodeProcedure() {
    int width = 1;
    int height = 2;
    
    // initialize header
    HashMap<String, Object> header = new HashMap<>();
    header.put("width", Double.valueOf(width));
    header.put("heigth", Double.valueOf(height));

    // initialize content
    WritableImage writableImage = new WritableImage(width, height);
    PixelWriter pixelWriter = writableImage.getPixelWriter();
    pixelWriter.setColor(width - 1, height - 2, Color.web("0x000000"));
    pixelWriter.setColor(width - 1, height - 1, Color.web("0xFFFFFF"));
    BlackWhiteImage content = new BlackWhiteImage(writableImage);
    content.setHeader(header);
    
    // call method to be tested
    bwImageProcedurePlugin.initEncodeProcedure(content);
    
    // get content of last calculated chain link
    BinaryString binaryString =
        (BinaryString) (bwImageProcedurePlugin.getChainTail().getContent());
    
    // check if procedure is initialized
    int bwUnitLength = 1;
    int pixelNumber = 2;
    assertTrue(binaryString.length() == pixelNumber * bwUnitLength
        && binaryString.getString().equals("01"));
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedurePlugin#hashCode()}.
   */
  @Test
  void testHashCode() {
    int hash = 7 * 17 + bwImageProcedurePlugin.pluginNameProperty().get().hashCode();
    assertEquals(hash, bwImageProcedurePlugin.hashCode());
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedurePlugin#activatedProperty()}.
   */
  @Test
  void testActivatedProperty() {
    bwImageProcedurePlugin.activatedProperty().set(true);
    assertTrue(bwImageProcedurePlugin.activatedProperty().get());
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedurePlugin#equals(java.lang.Object)}.
   */
  @Test
  void testEqualsObject() {
    BWImageProcedurePlugin secondInstance = new BWImageProcedurePlugin();
    
    assertEquals(bwImageProcedurePlugin, secondInstance);
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedurePlugin
   * #compareTo(kodex.plugininterface.ProcedurePlugin)}.
   */
  @Test
  void testCompareTo() {
    int expectedResult = 13;
    ColorImageProcedurePlugin secondProcedurePlugin = Mockito.mock(ColorImageProcedurePlugin.class);
    ColorImageProcedureInformation colorImageInformation = Mockito
        .mock(ColorImageProcedureInformation.class);
    Mockito.when(colorImageInformation.getName()).thenReturn("Farbbildverfahren");
    Mockito.when(secondProcedurePlugin.createProcedureInformation())
        .thenReturn(colorImageInformation);

    assertEquals(expectedResult, bwImageProcedurePlugin.compareTo(secondProcedurePlugin));
  }

}
