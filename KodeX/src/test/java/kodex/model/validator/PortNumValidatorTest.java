package kodex.model.validator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PortNumValidatorTest {

  private PortNumValidator instance = PortNumValidator.getInstance();

  @Test
  void testGetInstance() {
    assertTrue(instance instanceof PortNumValidator);
  }

  @Test
  void testIsValidMin() {
    assertTrue(instance.isValid("0"));
  }
  
  @Test
  void testIsValidMax() {
    assertTrue(instance.isValid("65535"));
  }

  @Test
  void testIsValidDefault() {
    assertTrue(instance.isValid("1337"));
  }
  
  @Test
  void testIsValidInvalidLength() {
    assertTrue(!instance.isValid("133742"));
  }
  
  @Test
  void testIsValidInvalidDigit() {
    assertTrue(!instance.isValid("65567"));
  }
  
  @Test
  void testIsValidIntParseError() {
    assertTrue(!instance.isValid("65567583574835643958345435634594384357"));
  }
  
  @Test
  void testIsValidNoInt() {
    assertTrue(!instance.isValid("abcdefg"));
  }
  
  @Test
  void testIsValidNull() {
    assertTrue(!instance.isValid(null));
  }
  
  @Test
  void testIsValidEmpty() {
    assertTrue(!instance.isValid(""));
  }
}
