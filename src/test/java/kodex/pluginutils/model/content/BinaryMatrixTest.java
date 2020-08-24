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

class BinaryMatrixTest {
  private static int width = 5;
  private static int height = 10;
  private static BinaryMatrix bmtx;
  private static Integer[][] testmtx;
  private static Integer[][] failmtx = {{1, 2, 3}, {1, 2}, {1}};
  
  @BeforeEach
  void setUp() throws Exception {
    bmtx = new BinaryMatrix(width, height);
    testmtx = new Integer[height][width];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        testmtx[y][x] = (int) (Math.random() * 2);
      }
    }
  }
  
  @SuppressWarnings("unchecked")
  @Test
  void testExport() throws FileNotFoundException {
    bmtx.setMatrix(testmtx);
    HashMap<String, Integer> map = new HashMap<String, Integer>();
    map.put("height", height);
    map.put("width", width);
    bmtx.setHeader(map);
    File f = new File("export test");
    bmtx.export(f);
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
      for (int x = 0; x < bmtx.getWidth(); x++) {
        is += bmtx.get(x, y).toString() + " ";
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
