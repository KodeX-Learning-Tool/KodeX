package kodex.pluginutils.model.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kodex.exceptions.InvalidInputException;

class ByteMatrixTest {
  private static int width = 5;
  private static int height = 10;
  private static ByteMatrix bytemtx;
  private static Integer[][] testmtx;
  private static Integer[][] failmtx = {{1, 2, 3}, {1, 2}, {1}};
  
  @BeforeEach
  void setUp() throws Exception {
    bytemtx = new ByteMatrix(width, height);
    
    testmtx = new Integer[height][width];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        testmtx[y][x] = (int) (Math.random() * 256);
      }
    }
    
  }

  @SuppressWarnings("unchecked")
  @Test
  void testExport() throws FileNotFoundException {
    bytemtx.setMatrix(testmtx);
    HashMap<String, Integer> map = new HashMap<String, Integer>();
    map.put("height", height);
    map.put("width", width);
    bytemtx.setHeader(map);
    File f = new File("export test");
    bytemtx.export(f);
    Scanner scanner = new Scanner(f);
    assertEquals("HEADER", scanner.nextLine());
    assertEquals("width", scanner.next());
    assertEquals(width, Integer.parseInt(scanner.next()));
    assertEquals("height", scanner.next());
    assertEquals(height, Integer.parseInt(scanner.next()));
    assertEquals("CONTENT", scanner.next());
    scanner.nextLine();
    
    int y = 0;
    while (scanner.hasNextLine()) {
      String in = scanner.nextLine();
      String is = "";
      for (int x = 0; x < bytemtx.getWidth(); x++) {
        int i = bytemtx.get(x, y);
        if (i < 10) {
          is += i + "  " + " ";
        } else if (i < 100) {
          is += i + " " + " ";
        } else {
          is += i + "" + " ";
        }
      }
      is = is.substring(0, is.length() - 1);
      assertEquals(is, in);
      y++;
    }
    scanner.close();
    f.delete();
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
