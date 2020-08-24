package kodex.standardplugins.colorimageprocedure;

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
import kodex.exceptions.AlertWindowException;
import kodex.pluginutils.model.content.BinaryString;
import kodex.pluginutils.model.content.ColorImage;
import kodex.pluginutils.presenter.chainlink.BinaryStringPresenter;
import kodex.pluginutils.presenter.chainlink.ColorImageChainLinkPresenter;
import kodex.presenter.PresenterManager;
import kodex.presenter.ProcedureLayoutPresenter;
import kodex.standardplugins.bwimageprocedure.BWImageProcedureInformation;
import kodex.standardplugins.bwimageprocedure.BWImageProcedurePlugin;
import kodex.standardplugins.colorimageprocedure.presenter.ColorImageImportPresenter;

/**
 * This test class is responsible for the ColorImageProcedurePlugin class.
 * 
 * @author Raimon Gramlich
 */
@ExtendWith(ApplicationExtension.class)
class ColorImageProcedurePluginTest {
  
  private ColorImageProcedurePlugin colorImageProcedurePlugin;
  private PresenterManager pm;
  private ProcedureLayoutPresenter procedureLayoutPresenter;
  
  @Start
  void setUp(Stage stage) throws Exception {
    pm = Mockito.mock(PresenterManager.class);
    
    colorImageProcedurePlugin = new ColorImageProcedurePlugin();
    
    procedureLayoutPresenter = new ProcedureLayoutPresenter(pm, colorImageProcedurePlugin);

    BorderPane layout = new BorderPane();
    layout.setCenter(procedureLayoutPresenter.getView());
    stage.setScene(new Scene(layout));
    stage.show();
  }

  @AfterEach
  void tearDown() {
    colorImageProcedurePlugin = null;
    pm = null;
    procedureLayoutPresenter = null;
  }

  /**
   * Test method for {@link kodex.standardplugins.colorimageprocedure.ColorImageProcedurePlugin
   * #pluginDescriptionProperty()}.
   */
  @Test
  void testPluginDescriptionProperty() {
    String expectedDescription = "Das Kodierungsverfahren Bild-zu-Bitfolge.";

    assertEquals(expectedDescription, colorImageProcedurePlugin.pluginDescriptionProperty().get());
  }

  /**
   * Test method for{@link kodex.standardplugins.colorimageprocedure.ColorImageProcedurePlugin
   * #pluginNameProperty()}.
   */
  @Test
  void testPluginNameProperty() {
    String expectedPluginName = "Farbbildverfahrensplugin";
    
    assertEquals(expectedPluginName, colorImageProcedurePlugin.pluginNameProperty().get());
  }

  /**
   * Test method for
   * {@link kodex.standardplugins.colorimageprocedure.ColorImageProcedurePlugin#getChainHead()}.
   */
  @Test
  void testGetChainHead() {
    assertTrue(colorImageProcedurePlugin.getChainHead() instanceof ColorImageChainLinkPresenter);
  }

  /**
   * Test method for
   * {@link kodex.standardplugins.colorimageprocedure.ColorImageProcedurePlugin#getChainTail()}.
   */
  @Test
  void testGetChainTail() {
    assertTrue(colorImageProcedurePlugin.getChainTail() instanceof BinaryStringPresenter);
  }

  /**
   * Test method for {@link kodex.standardplugins.colorimageprocedure.ColorImageProcedurePlugin
   * #initDecodeProcedure(kodex.plugininterface.Content)}.
   * @throws AlertWindowException if an error happens when calculating the chain
   */
  @Test
  void testInitDecodeProcedure() throws AlertWindowException {
    int width = 1;
    int height = 1;
    int rgbUnitLength = 24;

    // initialize header
    HashMap<String, Object> header = new HashMap<>();
    header.put("width", width);
    header.put("height", height);
    header.put("unit-length", rgbUnitLength);

    // initialize content
    BinaryString content = new BinaryString("111111111111111111111111");
    content.setHeader(header);
    
    // call method to be tested
    colorImageProcedurePlugin.initDecodeProcedure(content);
    
    // get content of last calculated chain link
    ColorImage image = (ColorImage) (colorImageProcedurePlugin.getChainHead().getContent());
    
    // check if procedure is initialized
    assertTrue(image.getHeight() == height & image.getWidth() == width
        & image.getColor(width - 1, height - 1).equals(Color.WHITE));
  }

  /**
   * Test method for {@link kodex.standardplugins.colorimageprocedure.ColorImageProcedurePlugin
   * #initEncodeProcedure(kodex.plugininterface.Content)}.
   * @throws AlertWindowException if an error happens when calculating the chain
   */
  @Test
  void testInitEncodeProcedure() throws AlertWindowException {
    int width = 1;
    int height = 2;
    
    // initialize header
    HashMap<String, Object> header = new HashMap<>();
    header.put("width", Double.valueOf(width));
    header.put("heigth", Double.valueOf(height));

    // initialize content
    WritableImage writableImage = new WritableImage(width, height);
    PixelWriter pixelWriter = writableImage.getPixelWriter();
    pixelWriter.setColor(width - 1, height - 2, Color.web("0xFF00FF"));
    pixelWriter.setColor(width - 1, height - 1, Color.web("0xFFFFFF"));
    ColorImage content = new ColorImage(writableImage);
    content.setHeader(header);
    
    // call method to be tested
    colorImageProcedurePlugin.initEncodeProcedure(content);
    
    // get content of last calculated chain link
    BinaryString binaryString =
        (BinaryString) (colorImageProcedurePlugin.getChainTail().getContent());
    
    // check if procedure is initialized
    int rgbUnitLength = 24;
    int pixelNumber = 2;
    assertTrue(binaryString.length() == pixelNumber * rgbUnitLength
        && binaryString.getString().equals("111111110000000011111111111111111111111111111111"));
  }

  /**
   * Test method for{@link kodex.standardplugins.colorimageprocedure.ColorImageProcedurePlugin
   * #createImportPresenter()}.
   */
  @Test
  void testCreateImportPresenter() {
    assertTrue(colorImageProcedurePlugin.createImportPresenter()
        instanceof ColorImageImportPresenter);
  }

  /**
   * Test method for {@link kodex.standardplugins.colorimageprocedure.ColorImageProcedurePlugin
   * #createProcedureInformation()}.
   */
  @Test
  void testCreateProcedureInformation() {
    assertTrue(colorImageProcedurePlugin.createProcedureInformation()
        instanceof ColorImageProcedureInformation);
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedurePlugin#hashCode()}.
   */
  @Test
  void testHashCode() {
    int hash = 7 * 17 + colorImageProcedurePlugin.pluginNameProperty().get().hashCode();
    assertEquals(hash, colorImageProcedurePlugin.hashCode());
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedurePlugin#activatedProperty()}.
   */
  @Test
  void testActivatedProperty() {
    colorImageProcedurePlugin.activatedProperty().set(true);
    assertTrue(colorImageProcedurePlugin.activatedProperty().get());
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedurePlugin#equals(java.lang.Object)}.
   */
  @Test
  void testEqualsObject() {
    ColorImageProcedurePlugin secondInstance = new ColorImageProcedurePlugin();
    
    assertEquals(colorImageProcedurePlugin, secondInstance);
  }

  /**
   * Test method for 
   * {@link kodex.plugininterface.ProcedurePlugin#compareTo(kodex.plugininterface.ProcedurePlugin)}.
   */
  @Test
  void testCompareTo() {
    int expectedResult = -13;
    BWImageProcedurePlugin secondProcedurePlugin = Mockito.mock(BWImageProcedurePlugin.class);
    BWImageProcedureInformation bwInformation = Mockito.mock(BWImageProcedureInformation.class);
    Mockito.when(bwInformation.getName()).thenReturn("Schwarz & Weiß - Bild");
    Mockito.when(secondProcedurePlugin.createProcedureInformation())
          .thenReturn(bwInformation);

    assertEquals(expectedResult, colorImageProcedurePlugin.compareTo(secondProcedurePlugin));
  }

}
