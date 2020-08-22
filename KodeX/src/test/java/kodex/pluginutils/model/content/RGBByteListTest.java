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

import kodex.exceptions.InvalidInputException;

class RGBByteListTest {
  private static int len = 12;
  private static RGBByteList rgblist;
  private static LinkedList<String> testlist;
  
  @BeforeEach
  void setUp() throws Exception {
    rgblist = new RGBByteList();
    testlist = new LinkedList<String>();
    for (int i = 0; i < len; i++) {
      String s = "";
      for (int j = 0; j < 8; j++) {
        s += (int) (Math.random() * 2);
      }
      testlist.add(s);
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
      String is = rgblist.get(y * 3).substring(0, 8);
      is += " " + rgblist.get(y * 3 + 1).substring(0, 8);
      is += " " + rgblist.get(y * 3 + 2).substring(0, 8);
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
    //invalid value
    testlist.set(0, "abcdefgh");
    rgblist.setList(testlist);
    assertThrows(InvalidInputException.class, () -> rgblist.isValid(rgblist));
    //invalid value
    testlist.set(0, "111100001");
    rgblist.setList(testlist);
    assertThrows(InvalidInputException.class, () -> rgblist.isValid(rgblist));
    testlist.removeFirst();
    rgblist.setList(testlist);
    assertThrows(InvalidInputException.class, () -> rgblist.isValid(rgblist));
    //valid list
    testlist.removeFirst();
    testlist.removeFirst();
    rgblist.setList(testlist);
    assertTrue(rgblist.isValid(rgblist));
  }

}
