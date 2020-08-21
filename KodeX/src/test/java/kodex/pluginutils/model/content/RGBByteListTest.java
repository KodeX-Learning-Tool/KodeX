package kodex.pluginutils.model.content;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import kodex.exceptions.InvalidInputException;

class RGBByteListTest {
  private static int len = 12;
  private static RGBByteList rgblist;
  private static LinkedList<String> testlist;
  
  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    rgblist = new RGBByteList();
    testlist = new LinkedList<String>();
    for (int i = 0; i < len; i++) {
      String s = "";
      for (int j = 0; j < 8; j++) {
        s += (int) (Math.random() * 2);
      }
      testlist.add(s);
      System.out.println(testlist.get(i));
    }
  }

  @Test
  void testExport() {
    fail("Not yet implemented");
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
