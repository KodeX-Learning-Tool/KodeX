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

class LetterStringTest {
  private static LetterString lstr;
  private static String teststr;
  
  @BeforeEach
  void setUp() throws Exception {
    lstr = new LetterString();
    teststr = "abcXYZ";
  }

  @Test
  void testExport() throws FileNotFoundException {
    lstr.setString(teststr);
    File f = new File("export test");
    lstr.export(f);
    Scanner scanner = new Scanner(f);
    assertEquals("HEADER", scanner.nextLine());
    assertEquals("CONTENT", scanner.nextLine());
    assertEquals(teststr, scanner.nextLine());
    scanner.close();
  }

  @Test
  void testIsValidString() throws InvalidInputException {
    //no string
    assertThrows(IllegalArgumentException.class, () -> lstr.setString(null));
    assertThrows(InvalidInputException.class, () -> lstr.isValid(null));
    //valid string
    assertTrue(lstr.isValid(teststr));
    //valid common characters
    teststr = "üöäß";
    assertTrue(lstr.isValid(teststr));
    //valid uncommon characters
    teststr = "Γγん平仮名Дд";
    assertTrue(lstr.isValid(teststr));
    //invalid characters
    teststr = "1";
    assertThrows(InvalidInputException.class, () -> lstr.isValid(teststr));
    teststr = " ";
    assertThrows(InvalidInputException.class, () -> lstr.isValid(teststr));
  }

}
