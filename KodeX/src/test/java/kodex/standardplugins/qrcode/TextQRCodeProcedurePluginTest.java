package kodex.standardplugins.qrcode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.File;
import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kodex.exceptions.AlertWindowException;
import kodex.pluginutils.model.content.CharacterString;
import kodex.pluginutils.model.content.QRCode;
import kodex.pluginutils.presenter.chainlink.CharacterStringChainLinkPresenter;
import kodex.pluginutils.presenter.chainlink.QRCodeChainLinkPresenter;
import kodex.presenter.PresenterManager;
import kodex.presenter.ProcedureLayoutPresenter;
import kodex.standardplugins.bwimageprocedure.BWImageProcedureInformation;
import kodex.standardplugins.bwimageprocedure.BWImageProcedurePlugin;
import kodex.standardplugins.qrcode.presenter.TextQRCodeImportPresenter;

/**
 * This test class is responsible for the TextQRCodeProcedurePlugin class.
 * 
 * @author Raimon Gramlich
 */
@ExtendWith(ApplicationExtension.class)
class TextQRCodeProcedurePluginTest {
  
  private TextQRCodeProcedurePlugin textQRCodeProcedurePlugin;
  private PresenterManager pm;
  private ProcedureLayoutPresenter procedureLayoutPresenter;
  
  @Start
  void setUp(Stage stage) throws Exception {
    pm = Mockito.mock(PresenterManager.class);
    
    textQRCodeProcedurePlugin = new TextQRCodeProcedurePlugin();
    
    textQRCodeProcedurePlugin.createImportPresenter();
    
    procedureLayoutPresenter = new ProcedureLayoutPresenter(pm, textQRCodeProcedurePlugin);

    BorderPane layout = new BorderPane();
    layout.setCenter(procedureLayoutPresenter.getView());
    stage.setScene(new Scene(layout));
    stage.show();
  }
  
  @AfterEach
  void tearDown() throws Exception {
    textQRCodeProcedurePlugin = null;
    pm = null;
    procedureLayoutPresenter = null;
  }

  /**
   * Test method for {@link kodex.standardplugins.qrcode.TextQRCodeProcedurePlugin
   * #pluginDescriptionProperty()}.
   */
  @Test
  void testPluginDescriptionProperty() {
    String expectedDescription = "Das Kodierungs-Verfahren zum Generieren eines QRCodes.";

    assertEquals(expectedDescription, textQRCodeProcedurePlugin.pluginDescriptionProperty().get());
  }

  /**
   * Test method for {@link kodex.standardplugins.qrcode.TextQRCodeProcedurePlugin
   * #pluginNameProperty()}.
   */
  @Test
  void testPluginNameProperty() {
    String expectedPluginName = "QR-Code";
    
    assertEquals(expectedPluginName, textQRCodeProcedurePlugin.pluginNameProperty().get());
  }

  /**
   * Test method for {@link kodex.standardplugins.qrcode.TextQRCodeProcedurePlugin
   * #createImportPresenter()}.
   */
  @Test
  void testCreateImportPresenter() {
    assertTrue(textQRCodeProcedurePlugin.createImportPresenter()
        instanceof TextQRCodeImportPresenter);
  }

  /**
   * Test method for {@link kodex.standardplugins.qrcode.TextQRCodeProcedurePlugin
   * #createProcedureInformation()}.
   */
  @Test
  void testCreateProcedureInformation() {
    assertTrue(textQRCodeProcedurePlugin.createProcedureInformation()
        instanceof TextQRCodeProcedureInformation);
  }

  /**
   * Test method for {@link kodex.standardplugins.qrcode.TextQRCodeProcedurePlugin#getChainHead()}.
   */
  @Test
  void testGetChainHead() {
    assertTrue(
        textQRCodeProcedurePlugin.getChainHead() instanceof CharacterStringChainLinkPresenter);
  }

  /**
   * Test method for
   * {@link kodex.standardplugins.qrcode.TextQRCodeProcedurePlugin#getChainTail()}.
   */
  @Test
  void testGetChainTail() {
    assertTrue(textQRCodeProcedurePlugin.getChainTail() instanceof QRCodeChainLinkPresenter);
  }

  /**
   * Test method for {@link kodex.standardplugins.qrcode.TextQRCodeProcedurePlugin
   * #initDecodeProcedure(kodex.plugininterface.Content)}.
   * @throws AlertWindowException if an error happens when calculating the chain
   */
  @Test
  void testInitDecodeProcedure() throws AlertWindowException {
    // initialize content
    QRCode content = new QRCode();
    
    File data = new File(
        TextQRCodeProcedurePluginTest.class.getResource("test_qr_code.png").getPath());
    
    content.setBinaryBitmap(data);    
    content.setHeader(new HashMap<>());
    
    // call method to be tested
    textQRCodeProcedurePlugin.initDecodeProcedure(content);
    
    // get content of last calculated chain link
    CharacterString characterString =
        (CharacterString) (textQRCodeProcedurePlugin.getChainHead().getContent());
    
    // check if procedure is initialized
    String expectedString = "KodeX";
    assertEquals(expectedString, characterString.getString());
  }

  /**
   * Test method for {@link kodex.standardplugins.qrcode.TextQRCodeProcedurePlugin
   * #initEncodeProcedure(kodex.plugininterface.Content)}.
   * @throws AlertWindowException if an error happens when calculating the chain
   */
  @Test
  void testInitEncodeProcedure() throws AlertWindowException {
    String testString = "KodeX";
    int size = 0;
    
    // initialize content
    CharacterString content = new CharacterString(testString);
    content.setHeader(new HashMap<>());
    
    // call method to be tested
    textQRCodeProcedurePlugin.initEncodeProcedure(content);
    
    // get content of last calculated chain link
    QRCode qrCode = (QRCode) (textQRCodeProcedurePlugin.getChainTail().getContent());
    
    // prepare control QR Code
    QRCode expectedQRCode = new QRCode();    
    QRCodeWriter qrCodeWriter = new QRCodeWriter();

    try {
      BitMatrix bitMatrix = qrCodeWriter.encode(testString, BarcodeFormat.QR_CODE, size, size);
      expectedQRCode.setBitMatrix(bitMatrix);
    } catch (WriterException e) {
      fail("Couldn't encode expected QR-Code.", e);
    }
    
    // check if procedure is initialized
    assertEquals(expectedQRCode.getBitMatrix(), qrCode.getBitMatrix());
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedurePlugin#hashCode()}.
   */
  @Test
  void testHashCode() {
    int hash = 7 * 17 + textQRCodeProcedurePlugin.pluginNameProperty().get().hashCode();
    assertEquals(hash, textQRCodeProcedurePlugin.hashCode());
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedurePlugin#activatedProperty()}.
   */
  @Test
  void testActivatedProperty() {
    textQRCodeProcedurePlugin.activatedProperty().set(true);
    assertTrue(textQRCodeProcedurePlugin.activatedProperty().get());
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedurePlugin#equals(java.lang.Object)}.
   */
  @Test
  void testEqualsObject() {
    TextQRCodeProcedurePlugin secondInstance = new TextQRCodeProcedurePlugin();
    
    assertEquals(textQRCodeProcedurePlugin, secondInstance);
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedurePlugin
   * #compareTo(kodex.plugininterface.ProcedurePlugin)}.
   */
  @Test
  void testCompareTo() {
    int expectedResult = -2;
    BWImageProcedurePlugin secondProcedurePlugin = Mockito.mock(BWImageProcedurePlugin.class);
    BWImageProcedureInformation bwInformation = Mockito.mock(BWImageProcedureInformation.class);
    Mockito.when(bwInformation.getName()).thenReturn("Schwarz & Weiß - Bild");
    Mockito.when(secondProcedurePlugin.createProcedureInformation())
          .thenReturn(bwInformation);

    assertEquals(expectedResult, textQRCodeProcedurePlugin.compareTo(secondProcedurePlugin));
  }

}
