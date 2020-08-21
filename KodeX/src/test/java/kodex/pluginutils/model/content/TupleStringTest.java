package kodex.pluginutils.model.content;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import kodex.exceptions.InvalidInputException;

class TupleStringTest {
  private static TupleString tstr;
  private static String teststr;
  
  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    tstr = new TupleString();
    teststr = "A:2 B:1 C:12";
  }

  @Test
  void testExport() {
    fail("Not yet implemented");
  }

  @Test
  void testIsValidString() throws InvalidInputException {
    //no string
    assertThrows(IllegalArgumentException.class, () -> tstr.setString(null));
    assertThrows(InvalidInputException.class, () -> tstr.isValid(null));
    //valid string
    assertTrue(tstr.isValid(teststr));
    //valid common characters
    teststr = "Ü:2 ö:3 Ä:1 ß:4";
    assertTrue(tstr.isValid(teststr)); 
    //valid uncommon characters
    teststr = "Γ:1 ん:3 平:4 Д:2";
    assertTrue(tstr.isValid(teststr));
    //invalid characters
    teststr = "A:0";
    assertThrows(InvalidInputException.class, () -> tstr.isValid(teststr));
    teststr = "A:2 + B:2 , C:3";
    assertThrows(InvalidInputException.class, () -> tstr.isValid(teststr));
  }

}
