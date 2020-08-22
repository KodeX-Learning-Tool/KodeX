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

import javafx.scene.paint.Color;
import kodex.exceptions.InvalidInputException;

class RGBMatrixTest {
  private static int width = 5;
  private static int height = 10;
  private static RGBMatrix rgbmtx;
  private static Color[][] testmtx;
  
  @BeforeEach
  void setUpBeforeClass() throws Exception {
    rgbmtx = new RGBMatrix(width, height);
    
    testmtx = new Color[height][width];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        testmtx[y][x] = new Color(Math.random(), Math.random(), Math.random(), 0);
      }
    }
    
  }

  @SuppressWarnings("unchecked")
  @Test
  void testExport() throws FileNotFoundException {
    rgbmtx.setMatrix(testmtx);
    HashMap<String, Integer> map = new HashMap<String, Integer>();
    map.put("height", height);
    map.put("width", width);
    rgbmtx.setHeader(map);
    File f = new File("export test");
    rgbmtx.export(f);
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
      for (int x = 0; x < rgbmtx.getWidth(); x++) {
        is += rgbmtx.get(x, y).toString().substring(0, 8) + " ";
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
