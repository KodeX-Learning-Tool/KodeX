package kodex.standardplugins.rle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This test class is responsible for the TextRLEProcedureInformation class.
 * 
 * @author Raimon Gramlich
 *
 */
class TextRLEProcedureInformationTest {

  private TextRLEProcedureInformation textRLEProcedureInformation;
  
  @BeforeEach
  void setUp() {
    textRLEProcedureInformation = new TextRLEProcedureInformation();
  }
  
  @AfterEach
  void tearDown() throws Exception {
    textRLEProcedureInformation = null;
  }

  /**
   * Test method for {@link kodex.standardplugins.rle.TextRLEProcedureInformation#getName()}.
   */
  @Test
  void testGetName() {
    assertEquals("Lauflängencodierung", textRLEProcedureInformation.getName());
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedureInformation#getDescription()}.
   */
  @Test
  void testGetDescription() {
    String expectedDescription = "In diesem Verfahren kann ein Text"
        + "mit der Lauflängencodierung komprimiert oder dekomprimiert werden.";
    assertEquals(expectedDescription, textRLEProcedureInformation.getDescription());
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedureInformation#getLabels()}.
   */
  @Test
  void testGetLabels() {
    ObservableList<String> expected = FXCollections.observableArrayList("8",
        "Komprimierungsverfahren", "Kodieren und Dekodieren");
    ObservableList<String> actual = textRLEProcedureInformation.getLabels();

    assertEquals(expected, actual);
  }

}
