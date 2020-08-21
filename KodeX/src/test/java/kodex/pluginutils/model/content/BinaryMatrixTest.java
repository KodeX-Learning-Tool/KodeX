package kodex.pluginutils.model.content;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import kodex.exceptions.InvalidInputException;

class BinaryMatrixTest {
  private static int width = 5;
  private static int height = 10;
  private static BinaryMatrix bmtx;
  private static Integer[][] testmtx;
  private static Integer[][] failmtx = {{1, 2, 3}, {1, 2}, {1}};
  
  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    bmtx = new BinaryMatrix(width, height);
    
    testmtx = new Integer[height][width];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        testmtx[y][x] = (int) (Math.random() * 2);
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
    assertThrows(InvalidInputException.class, () -> bmtx.isValid(1));
    //not a matrix
    assertThrows(IllegalArgumentException.class, () -> bmtx.setMatrix(failmtx));
    //no matrix
    assertThrows(IllegalArgumentException.class, () -> bmtx.setMatrix(null));
    assertThrows(InvalidInputException.class, () -> bmtx.isValid(null));
    //valid matrix
    bmtx.setMatrix(testmtx);
    assertTrue(bmtx.isValid(bmtx));
    //invalid value
    testmtx[0][0] = -1;
    bmtx.setMatrix(testmtx);
    assertThrows(InvalidInputException.class, () -> bmtx.isValid(bmtx));
    //not initialized
    testmtx = new Integer[1][1];
    bmtx.setMatrix(testmtx);
    assertThrows(InvalidInputException.class, () -> bmtx.isValid(bmtx));
    //invalid size
    testmtx = new Integer[501][1];
    bmtx.setMatrix(testmtx);
    assertThrows(InvalidInputException.class, () -> bmtx.isValid(bmtx));
  }

}
