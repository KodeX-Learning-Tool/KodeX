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

class BitListTest {
  private static int len = 10;
  private static BitList blist;
  private static LinkedList<Integer> testlist;
  
  @BeforeEach
  void setUp() throws Exception {
    blist = new BitList();
    testlist = new LinkedList<Integer>();
    for (int i = 0; i < len; i++) {
      testlist.add((int) (Math.random() * 2));
    }
  }

  @Test
  void testExport() throws FileNotFoundException {
    blist.setList(testlist);
    File f = new File("export test");
    blist.export(f);
    Scanner scanner = new Scanner(f);
    assertEquals("HEADER", scanner.nextLine());
    assertEquals("CONTENT", scanner.nextLine());
    
    int y = 0;
    while (scanner.hasNextLine()) {
      String in = scanner.nextLine();
      String is = blist.get(y).toString().substring(0, 1);
      assertEquals(is, in);
      y++;
    }
    scanner.close();
    f.delete();
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
