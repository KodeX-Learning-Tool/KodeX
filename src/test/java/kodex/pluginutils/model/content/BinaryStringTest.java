package kodex.pluginutils.model.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kodex.exceptions.InvalidInputException;

class BinaryStringTest {
  private static BinaryString bstr;
  private static String teststr;
  
  @BeforeEach
  void setUp() throws Exception {
    bstr = new BinaryString();
    teststr = "01100110100110011001100101100110";
  }

  @Test
  void testExport() throws FileNotFoundException {
    bstr.setString(teststr);
    File f = new File("export test");
    bstr.export(f);
    Scanner scanner = new Scanner(f);
    assertEquals("HEADER", scanner.nextLine());
    assertEquals("CONTENT", scanner.nextLine());
    assertEquals(teststr, scanner.nextLine());
    scanner.close();
    f.delete();
  }

  @Test
  void testIsValidObject() throws InvalidInputException {
    //no string
    assertThrows(IllegalArgumentException.class, () -> bstr.setString(null));
    assertThrows(InvalidInputException.class, () -> bstr.isValid(null));
    //valid string
    assertTrue(bstr.isValid(teststr));
    //invalid value
    teststr += "2";
    assertThrows(InvalidInputException.class, () -> bstr.isValid(teststr));
    teststr = "A";
    assertThrows(InvalidInputException.class, () -> bstr.isValid(teststr));
  }

}
