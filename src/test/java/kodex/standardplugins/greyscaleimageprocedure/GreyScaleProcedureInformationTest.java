package kodex.standardplugins.greyscaleimageprocedure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This test class is responsible for the ColorImageProcedureInformation class.
 * 
 * @author Raimon Gramlich
 */
class GreyScaleProcedureInformationTest {

  private GreyScaleProcedureInformation greyScaleProcedureInformation;
  
  @BeforeEach
  void setup() {
    greyScaleProcedureInformation = new GreyScaleProcedureInformation();
  }
  
  @AfterEach
  void tearDown() throws Exception {
    greyScaleProcedureInformation = null;
  }

  /**
   * Test method for {@link kodex.standardplugins.greyscaleimageprocedure
   * .GreyScaleProcedureInformation#getName()}.
   */
  @Test
  void testGetName() {
    assertEquals("Graustufen - Bild", greyScaleProcedureInformation.getName());
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedureInformation#getDescription()}.
   */
  @Test
  void testGetDescription() {
    String expectedDescription = "Dieses Plugin zeigt den Weg von einem Bild in verschieden"
        + " Graustufen bis zu der binären Zeichenkette";
    
    assertEquals(expectedDescription, greyScaleProcedureInformation.getDescription());
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedureInformation#getLabels()}.
   */
  @Test
  void testGetLabels() {
    ObservableList<String> expected =
        FXCollections.observableArrayList("7", "image", "encoding & decoding");
    ObservableList<String> actual = greyScaleProcedureInformation.getLabels();

    assertEquals(expected, actual);
  }

}
