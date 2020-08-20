package kodex.standardplugins.qrcode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This test class is responsible for the TextQRCodeProcedureInformation class.
 * 
 * @author Raimon Gramlich
 */
class TextQRCodeProcedureInformationTest {
  
  private TextQRCodeProcedureInformation textQRCodeProcedureInformation;
  
  @BeforeEach
  void setUp() {
    textQRCodeProcedureInformation = new TextQRCodeProcedureInformation();
  }
  
  @AfterEach
  void tearDown() throws Exception {
    textQRCodeProcedureInformation = null;
  }

  /**
   * Test method for {@link kodex.standardplugins.qrcode.TextQRCodeProcedureInformation#getName()}.
   */
  @Test
  void testGetName() {
    assertEquals("QR-Code", textQRCodeProcedureInformation.getName());
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedureInformation#getDescription()}.
   */
  @Test
  void testGetDescription() {
    String expectedDescription = 
        "In diesem Verfahren kann ein Text zu einem QR-Code umgewandelt werden."
        + "Umgedreht kann auch ein QR-Code wieder in einen Text übersetzt werden";
    assertEquals(expectedDescription, textQRCodeProcedureInformation.getDescription());
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedureInformation#getLabels()}.
   */
  @Test
  void testGetLabels() {
    ObservableList<String> expected =
        FXCollections.observableArrayList("8", "Kodierungsverfahren", "Kodieren und Dekodieren");
    ObservableList<String> actual = textQRCodeProcedureInformation.getLabels();

    assertEquals(expected, actual);
  }

}
