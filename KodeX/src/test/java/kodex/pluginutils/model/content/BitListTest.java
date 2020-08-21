package kodex.pluginutils.model.content;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import kodex.exceptions.InvalidInputException;

class BitListTest {
  private static int len = 10;
  private static BitList blist;
  private static LinkedList<Integer> testlist;
  
  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    blist = new BitList();
    testlist = new LinkedList<Integer>();
    for (int i = 0; i < len; i++) {
      testlist.add((int) (Math.random() * 2));
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
    testlist.set(0, -1);
    blist.setList(testlist);
    assertThrows(InvalidInputException.class, () -> blist.isValid(blist));
    //invalid value
    testlist.set(0, 2);
    blist.setList(testlist);
    assertThrows(InvalidInputException.class, () -> blist.isValid(blist));
  }

}
