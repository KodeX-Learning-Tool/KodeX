package kodex.pluginutils.model.content;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;
import kodex.exceptions.InvalidInputException;

class RGBListTest {
  private static int len = 10;
  private static RGBList rgblist;
  private static LinkedList<Color> testlist;
  
  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    rgblist = new RGBList();
    testlist = new LinkedList<Color>();
    for (int i = 0; i < len; i++) {
      testlist.add(new Color(Math.random(), Math.random(), Math.random(), 0));
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
  }

}
