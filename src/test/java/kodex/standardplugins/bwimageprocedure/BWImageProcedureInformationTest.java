package kodex.standardplugins.bwimageprocedure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This test class is responsible for the BWImageProcedureInformation class.
 * 
 * @author Raimon Gramlich
 */
class BWImageProcedureInformationTest {

  private BWImageProcedureInformation bwImageProcedureInformation;
  
  @BeforeEach
  void setup() {
    bwImageProcedureInformation = new BWImageProcedureInformation();
  }
  
  
  
  @AfterEach
  void tearDown() {
    bwImageProcedureInformation = null;
  }

  /**
   * Test method for
   * {@link kodex.standardplugins.bwimageprocedure.BWImageProcedureInformation#getName()}.
   */
  @Test
  void testGetName() {
    assertEquals("Schwarz & Weiß - Bild", bwImageProcedureInformation.getName());
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedureInformation#getDescription()}.
   */
  @Test
  void testGetDescription() {
    String description = "Dieses Plugin zeigt den Weg von einem Bild in Schwarz/Weiß"
        + " bis zu der binären Zeichenkette";
    
    assertEquals(description, bwImageProcedureInformation.getDescription());
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedureInformation#getLabels()}.
   */
  @Test
  void testGetLabels() {
    ObservableList<String> expected = FXCollections.observableArrayList("7", "image",
        "encoding & decoding");
    ObservableList<String> actual = bwImageProcedureInformation.getLabels();
    
    assertEquals(expected, actual);
  }

}
