package kodex.pluginutils.model.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AbstractStringTest {
  private static int length = 10;
  private static BinaryString bitstring;
  private static String bits;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    bitstring = new BinaryString();
    
    bits = "";
    for (int i = 0; i < length; i++) {
      bits += ((int) Math.random());
    }
  }

  @Test
  void testSetAndGet() {
    assertThrows(IllegalArgumentException.class, () -> bitstring.setString(null));
    bitstring.setString(bits);
    assertEquals(bits, bitstring.getString());
    bitstring.setString(bits + "01");
    assertEquals(length + 2, bitstring.length());
  }

}
