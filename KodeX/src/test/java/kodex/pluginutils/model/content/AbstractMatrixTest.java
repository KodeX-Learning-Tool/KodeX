package kodex.pluginutils.model.content;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AbstractMatrixTest {
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

  @Test
  void testSetAndGetDimesions() {
    assertEquals(bmtx.getHeight(), height);
    assertEquals(bmtx.getWidth(), width);
  }
  
  @Test
  void testSetandGet() {
    bmtx.setMatrix(mtx);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        assertEquals(mtx[y][x].intValue(), bmtx.get(x, y));
        assertEquals(mtx[y][x].intValue(), bmtx.getMatrix()[y][x].intValue());
      }
    }
    bmtx.set(width -  1, height - 1, 0);
    assertEquals(0, bmtx.get(width - 1, height - 1));
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> bmtx.set(width, height, 0));
    bmtx.setSize(height, width);
    assertEquals(bmtx.getHeight(), width);
    assertEquals(bmtx.getWidth(), height);
    assertNull(bmtx.get(height - 1, width - 1));
    assertThrows(IllegalArgumentException.class, () -> bmtx.setSize(0, 0));
  }  
}
