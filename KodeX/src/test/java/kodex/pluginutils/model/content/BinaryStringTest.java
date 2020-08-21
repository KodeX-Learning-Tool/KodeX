package kodex.pluginutils.model.content;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import kodex.exceptions.InvalidInputException;

class BinaryStringTest {
  private static BinaryString bstr;
  private static String teststr;
  
  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    bstr = new BinaryString();
    teststr = "01100110100110011001100101100110";
    
  }

  @Test
  void testExport() {
    fail("Not yet implemented");
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
