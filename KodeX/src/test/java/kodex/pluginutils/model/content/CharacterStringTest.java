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

class CharacterStringTest {
  private static CharacterString cstr;
  private static String teststr;
  
  @BeforeEach
  void setUp() throws Exception {
    cstr = new CharacterString();
    teststr = "012abcXYZ";
  }

  @Test
  void testExport() throws FileNotFoundException {
    cstr.setString(teststr);
    File f = new File("export test");
    cstr.export(f);
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
    assertThrows(IllegalArgumentException.class, () -> cstr.setString(null));
    assertThrows(InvalidInputException.class, () -> cstr.isValid(null));
    //valid string
    assertTrue(cstr.isValid(teststr));
    //valid common characters
    teststr = ",._-#*+?<>$%&!/()=ß";
    assertTrue(cstr.isValid(teststr));
    teststr = "Γγん平仮名Дд";
    assertTrue(cstr.isValid(teststr));
    //valid uncommon characters
    teststr = "!§$%&/()=?`QWERTZUIOPÜ*ASDFGHJKLÖÄ'>YXCVBNM;:_¡";
    assertTrue(cstr.isValid(teststr));
    teststr = "¶¢[]|{}≠¿«∑€®†Ω¨⁄øπ•±å‚∂ƒ©ªº∆@œæ‘≤¥≈ç√∫~µ∞…–";
    assertTrue(cstr.isValid(teststr));
    teststr = "¬#£ﬁ^˜·¯˙˚»„ÛØ∏°ÅÍ™ÏÌıˆﬂŒÆ’≥‡◊›˘˛÷—\\";
    assertTrue(cstr.isValid(teststr));
  }
}
