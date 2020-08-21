package kodex.pluginutils.model.content;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;
import kodex.exceptions.InvalidInputException;

class RGBMatrixTest {
  private static int width = 5;
  private static int height = 10;
  private static RGBMatrix rgbmtx;
  private static Color[][] testmtx;
  
  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    rgbmtx = new RGBMatrix(width, height);
    
    testmtx = new Color[height][width];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        testmtx[y][x] = new Color(Math.random(), Math.random(), Math.random(), 0);
      }
    }
    
  }

  @Test
  void testExport() {
    fail("Not yet implemented");
  }

  @Test
  void testIsValidObject() throws InvalidInputException {
    //invalid type
    assertThrows(InvalidInputException.class, () -> rgbmtx.isValid(1));
    //no matrix
    assertThrows(IllegalArgumentException.class, () -> rgbmtx.setMatrix(null));
    assertThrows(InvalidInputException.class, () -> rgbmtx.isValid(null));
    //valid matrix
    rgbmtx.setMatrix(testmtx);
    assertTrue(rgbmtx.isValid(rgbmtx));
    //not initialized
    testmtx = new Color[1][1];
    rgbmtx.setMatrix(testmtx);
    assertThrows(InvalidInputException.class, () -> rgbmtx.isValid(rgbmtx));
    //invalid size
    testmtx = new Color[1][501];
    rgbmtx.setMatrix(testmtx);
    assertThrows(InvalidInputException.class, () -> rgbmtx.isValid(rgbmtx));
  }

}
