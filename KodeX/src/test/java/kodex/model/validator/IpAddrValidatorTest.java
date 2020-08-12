package kodex.model.validator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class IpAddrValidatorTest {

  private IpAddrValidator instance = IpAddrValidator.getInstance();

  @Test
  void testGetInstance() {
    assertTrue(instance instanceof IpAddrValidator);
  }

  @Test
  void testIsValidMin() {
    assertTrue(instance.isValid("0.0.0.0"));
  }
  
  @Test
  void testIsValidMax() {
    assertTrue(instance.isValid("255.255.255.255"));
  }
  
  @Test
  void testIsValidDifferentLength() {
    assertTrue(instance.isValid("255.0.12.201"));
  }
  
  @Test
  void testIsValidInvalidLength() {
    assertTrue(!instance.isValid("123.123.123.123.1"));
  }
  
  @Test
  void testIsValidInvalidLengthShort() {
    assertTrue(!instance.isValid("123.123.12"));
  }
  
  @Test
  void testIsValidWrongDots() {
    assertTrue(!instance.isValid("255.255..255.255"));
  }
  
  @Test
  void testIsValidInvalidDigit() {
    assertTrue(!instance.isValid("123.532.123.42"));
  }
  
  @Test
  void testIsValidNoInt() {
    assertTrue(!instance.isValid("abc.def.ghi.jkl"));
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
