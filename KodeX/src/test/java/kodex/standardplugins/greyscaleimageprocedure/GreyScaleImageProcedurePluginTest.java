package kodex.standardplugins.greyscaleimageprocedure;

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
import kodex.pluginutils.model.content.GreyScaleImage;
import kodex.pluginutils.presenter.chainlink.BinaryStringPresenter;
import kodex.pluginutils.presenter.chainlink.GreyScaleImageChainLinkPresenter;
import kodex.presenter.PresenterManager;
import kodex.presenter.ProcedureLayoutPresenter;
import kodex.standardplugins.bwimageprocedure.BWImageProcedureInformation;
import kodex.standardplugins.bwimageprocedure.BWImageProcedurePlugin;
import kodex.standardplugins.greyscaleimageprocedure.presenter.GreyScaleImageImportPresenter;

/**
 * This test class is responsible for the GreyScaleImageProcedurePlugin class.
 * 
 * @author Raimon Gramlich
 */
@ExtendWith(ApplicationExtension.class)
class GreyScaleImageProcedurePluginTest {
  
  private GreyScaleImageProcedurePlugin greyScaleImageProcedurePlugin;
  private PresenterManager pm;
  private ProcedureLayoutPresenter procedureLayoutPresenter;
  
  @Start
  void setUp(Stage stage) throws Exception {
    pm = Mockito.mock(PresenterManager.class);
    
    greyScaleImageProcedurePlugin = new GreyScaleImageProcedurePlugin();
    
    greyScaleImageProcedurePlugin.createImportPresenter();
    
    procedureLayoutPresenter = new ProcedureLayoutPresenter(pm, greyScaleImageProcedurePlugin);

    BorderPane layout = new BorderPane();
    layout.setCenter(procedureLayoutPresenter.getView());
    stage.setScene(new Scene(layout));
    stage.show();
  }
  
  @AfterEach
  void tearDown() throws Exception {
    greyScaleImageProcedurePlugin = null;
    pm = null;
    procedureLayoutPresenter = null;
  }

  /**
   * Test method for {@link kodex.standardplugins.greyscaleimageprocedure
   * .GreyScaleImageProcedurePlugin#pluginDescriptionProperty()}.
   */
  @Test
  void testPluginDescriptionProperty() {
    String expectedDescription = "Kodierungsverfahren";

    assertEquals(expectedDescription,
        greyScaleImageProcedurePlugin.pluginDescriptionProperty().get());
  }

  /**
   * Test method for {@link kodex.standardplugins.greyscaleimageprocedure
   * .GreyScaleImageProcedurePlugin#pluginNameProperty()}.
   */
  @Test
  void testPluginNameProperty() {
    String expectedPluginName = "Graustufen Bild";
    
    assertEquals(expectedPluginName, greyScaleImageProcedurePlugin.pluginNameProperty().get());
  }

  /**
   * Test method for {@link kodex.standardplugins.greyscaleimageprocedure
   * .GreyScaleImageProcedurePlugin#createImportPresenter()}.
   */
  @Test
  void testCreateImportPresenter() {
    assertTrue(greyScaleImageProcedurePlugin
        .createImportPresenter() instanceof GreyScaleImageImportPresenter);
  }

  /**
   * Test method for {@link kodex.standardplugins.greyscaleimageprocedure
   * .GreyScaleImageProcedurePlugin#createProcedureInformation()}.
   */
  @Test
  void testCreateProcedureInformation() {
    assertTrue(greyScaleImageProcedurePlugin.createProcedureInformation()
        instanceof GreyScaleProcedureInformation);
  }

  /**
   * Test method for {@link kodex.standardplugins.greyscaleimageprocedure
   * .GreyScaleImageProcedurePlugin#getChainHead()}.
   */
  @Test
  void testGetChainHead() {
    assertTrue(
        greyScaleImageProcedurePlugin.getChainHead() instanceof GreyScaleImageChainLinkPresenter);
  }

  /**
   * Test method for {@link kodex.standardplugins.greyscaleimageprocedure
   * .GreyScaleImageProcedurePlugin#getChainTail()}.
   */
  @Test
  void testGetChainTail() {
    assertTrue(
        greyScaleImageProcedurePlugin.getChainTail() instanceof BinaryStringPresenter);
  }
  
  /**
   * Test method for {@link kodex.standardplugins.greyscaleimageprocedure
   * .GreyScaleImageProcedurePlugin#initDecodeProcedure(kodex.plugininterface.Content)}.
   * @throws AlertWindowException if an error happens when calculating the chain
   */
  @Test
  void testInitDecodeProcedure() throws AlertWindowException {
    int width = 1;
    int height = 1;
    int greyScaleUnitLength = 24; // TODO: should be 8

    // initialize header
    HashMap<String, Object> header = new HashMap<>();
    header.put("width", width);
    header.put("height", height);
    header.put("unit-length", greyScaleUnitLength);

    // initialize content
    BinaryString content = new BinaryString("100000001000000010000000");
    content.setHeader(header);
    
    // call method to be tested
    greyScaleImageProcedurePlugin.initDecodeProcedure(content);
    
    // get content of last calculated chain link
    GreyScaleImage image = (GreyScaleImage) (greyScaleImageProcedurePlugin.getChainHead()
        .getContent());
    
    // check if procedure is initialized
    assertTrue(image.getHeight() == height & image.getWidth() == width
        & image.getColor(width - 1, height - 1).equals(Color.GREY));
  }

  /**
   * Test method for {@link kodex.standardplugins.greyscaleimageprocedure
   * .GreyScaleImageProcedurePlugin#initEncodeProcedure(kodex.plugininterface.Content)}.
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
    pixelWriter.setColor(width - 1, height - 2, Color.web("0x333333"));
    pixelWriter.setColor(width - 1, height - 1, Color.web("0x111111"));
    GreyScaleImage content = new GreyScaleImage(writableImage);
    content.setHeader(header);
    
    // call method to be tested
    greyScaleImageProcedurePlugin.initEncodeProcedure(content);
    
    // get content of last calculated chain link
    BinaryString binaryString =
        (BinaryString) (greyScaleImageProcedurePlugin.getChainTail().getContent());
    
    // check if procedure is initialized
    int greyScaleUnitLength = 8;
    int pixelNumber = 2;
    assertTrue(binaryString.length() == pixelNumber * greyScaleUnitLength
        && binaryString.getString().equals("0011001100010001"));
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedurePlugin#hashCode()}.
   */
  @Test
  void testHashCode() {
    int hash = 7 * 17 + greyScaleImageProcedurePlugin.pluginNameProperty().get().hashCode();
    assertEquals(hash, greyScaleImageProcedurePlugin.hashCode());
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedurePlugin#activatedProperty()}.
   */
  @Test
  void testActivatedProperty() {
    greyScaleImageProcedurePlugin.activatedProperty().set(true);
    assertTrue(greyScaleImageProcedurePlugin.activatedProperty().get());
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedurePlugin#equals(java.lang.Object)}.
   */
  @Test
  void testEqualsObject() {
    GreyScaleImageProcedurePlugin secondInstance = new GreyScaleImageProcedurePlugin();
    
    assertEquals(greyScaleImageProcedurePlugin, secondInstance);
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedurePlugin
   * #compareTo(kodex.plugininterface.ProcedurePlugin)}.
   */
  @Test
  void testCompareTo() {
    int expectedResult = -12;
    BWImageProcedurePlugin secondProcedurePlugin = Mockito.mock(BWImageProcedurePlugin.class);
    BWImageProcedureInformation bwInformation = Mockito.mock(BWImageProcedureInformation.class);
    Mockito.when(bwInformation.getName()).thenReturn("Schwarz & Weiß - Bild");
    Mockito.when(secondProcedurePlugin.createProcedureInformation())
          .thenReturn(bwInformation);

    assertEquals(expectedResult, greyScaleImageProcedurePlugin.compareTo(secondProcedurePlugin));
  }

}
