package kodex.pluginutils.model.content;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import kodex.exceptions.InvalidInputException;

class ByteListTest {
  private static int len = 10;
  private static ByteList blist;
  private static LinkedList<String> testlist;
  
  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    blist = new ByteList();
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
    assertThrows(InvalidInputException.class, () -> blist.isValid(1));
    //no list
    assertThrows(IllegalArgumentException.class, () -> blist.setList(null));
    assertThrows(InvalidInputException.class, () -> blist.isValid(null));
    //valid list
    blist.setList(testlist);
    assertTrue(blist.isValid(blist));
    //invalid value
    testlist.set(0, "abcdefgh");
    blist.setList(testlist);
    assertThrows(InvalidInputException.class, () -> blist.isValid(blist));
    //invalid value
    testlist.set(0, "111100001");
    blist.setList(testlist);
    assertThrows(InvalidInputException.class, () -> blist.isValid(blist));
  }

}
