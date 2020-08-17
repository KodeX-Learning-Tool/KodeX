package kodex.standardplugins.colorimageprocedure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This test class is responsible for the ColorImageProcedureInformation class.
 * 
 * @author Raimon Gramlich
 */
class ColorImageProcedureInformationTest {
  
  ColorImageProcedureInformation colorImageProcedureInformation;

  @BeforeEach
  void setUp() {
    colorImageProcedureInformation = new ColorImageProcedureInformation();
  }

  @AfterEach
  void tearDown() {
    colorImageProcedureInformation = null;
  }

  /**
   * Test method for
   * {@link kodex.standardplugins.colorimageprocedure.ColorImageProcedureInformation#getName()}.
   */
  @Test
  void testGetName() {
    assertEquals("Farbbildverfahren", colorImageProcedureInformation.getName());
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedureInformation#getDescription()}.
   */
  @Test
  void testGetDescription() {
    String description = "Es wird zunächst das Bild in eine Matrix umgewandelt."
        + " Daraufhin wandelt man diese in eine Liste um. Am Ende erhält man eine Binärfolge.";
    assertEquals(description, colorImageProcedureInformation.getDescription());
  }

  /**
   * Test method for {@link kodex.plugininterface.ProcedureInformation#getLabels()}.
   */
  @Test
  void testGetLabels() {
    ObservableList<String> expected =
        FXCollections.observableArrayList("7", "Kodierungsverfahren", "Kodieren und Dekodieren");
    ObservableList<String> actual = colorImageProcedureInformation.getLabels();

    assertEquals(expected, actual);
  }
}
