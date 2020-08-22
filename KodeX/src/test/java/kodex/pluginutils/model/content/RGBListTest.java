package kodex.pluginutils.model.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;
import kodex.exceptions.InvalidInputException;

class RGBListTest {
  private static int len = 10;
  private static RGBList rgblist;
  private static LinkedList<Color> testlist;
  
  @BeforeEach
  void setUp() throws Exception {
    rgblist = new RGBList();
    testlist = new LinkedList<Color>();
    for (int i = 0; i < len; i++) {
      testlist.add(new Color(Math.random(), Math.random(), Math.random(), 0));
    }
  }

  @Test
  void testExport() throws FileNotFoundException {
    rgblist.setList(testlist);
    File f = new File("export test");
    rgblist.export(f);
    Scanner scanner = new Scanner(f);
    assertEquals("HEADER", scanner.nextLine());
    assertEquals("CONTENT", scanner.nextLine());
    
    int y = 0;
    while (scanner.hasNextLine()) {
      String in = scanner.nextLine();
      String is = rgblist.get(y).toString().substring(0, 8);
      assertEquals(is, in);
      y++;
    }
    scanner.close();
    f.delete();
  }

  @Test
  void testIsValidObject() throws InvalidInputException {
    //invalid type
    assertThrows(InvalidInputException.class, () -> rgblist.isValid(1));
    //no list
    assertThrows(IllegalArgumentException.class, () -> rgblist.setList(null));
    assertThrows(InvalidInputException.class, () -> rgblist.isValid(null));
    //valid list
    rgblist.setList(testlist);
    assertTrue(rgblist.isValid(rgblist));
  }

}
