package kodex.pluginutils.model.content;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BinaryMatrixTest {
  private static int width = 5;
  private static int height = 10;
  private static BinaryMatrix bmtx;
  private static Integer[][] mtx;
  private static int[][] failmtx = {{1, 2, 3}, {1, 2}, {1}};
  
  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    bmtx = new BinaryMatrix(width, height);
    
    mtx = new Integer[height][width];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        mtx[y][x] = ((int) Math.random() * width * height);
      }
    }
  }

  @AfterAll
  static void tearDownAfterClass() throws Exception {
  }

  @Test
  void testExport() {
    fail("Not yet implemented");
  }

  @Test
  void testIsValidObject() {
    fail("Not yet implemented");
  }
}
