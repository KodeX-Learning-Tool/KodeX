package kodex.pluginutils.model.content;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import kodex.exceptions.InvalidInputException;

class LetterStringTest {
  private static LetterString lstr;
  private static String teststr;
  
  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    lstr = new LetterString();
    teststr = "abcXYZ";
  }

  @Test
  void testExport() {
    fail("Not yet implemented");
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
