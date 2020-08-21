package kodex.pluginutils.model.content;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import kodex.exceptions.InvalidInputException;

class ByteMatrixTest {
  private static int width = 5;
  private static int height = 10;
  private static ByteMatrix bytemtx;
  private static Integer[][] testmtx;
  private static Integer[][] failmtx = {{1, 2, 3}, {1, 2}, {1}};
  
  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    bytemtx = new ByteMatrix(width, height);
    
    testmtx = new Integer[height][width];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        testmtx[y][x] = ((int) Math.random());
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
    assertThrows(InvalidInputException.class, () -> bytemtx.isValid(1));
    //not a matrix
    assertThrows(IllegalArgumentException.class, () -> bytemtx.setMatrix(failmtx));
    //no matrix
    assertThrows(IllegalArgumentException.class, () -> bytemtx.setMatrix(null));
    assertThrows(InvalidInputException.class, () -> bytemtx.isValid(null));
    //valid matrix
    bytemtx.setMatrix(testmtx);
    assertTrue(bytemtx.isValid(bytemtx));
    //invalid value
    testmtx[0][0] = -1;
    bytemtx.setMatrix(testmtx);
    assertThrows(InvalidInputException.class, () -> bytemtx.isValid(bytemtx));
    //invalid value
    testmtx[0][0] = 256;
    bytemtx.setMatrix(testmtx);
    assertThrows(InvalidInputException.class, () -> bytemtx.isValid(bytemtx));
    //not initialized
    testmtx = new Integer[1][1];
    bytemtx.setMatrix(testmtx);
    assertThrows(InvalidInputException.class, () -> bytemtx.isValid(bytemtx));
    //invalid size
    testmtx = new Integer[501][1];
    bytemtx.setMatrix(testmtx);
    assertThrows(InvalidInputException.class, () -> bytemtx.isValid(bytemtx));
  }

}
