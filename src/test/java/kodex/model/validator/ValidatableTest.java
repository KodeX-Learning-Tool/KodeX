package kodex.model.validator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ValidatableTest {

  private Validatable port = PortNumValidator.getInstance();
  private Validatable ip = IpAddrValidator.getInstance();
  
  
  @Test
  void testInstances() {
    assertTrue(port instanceof PortNumValidator && ip instanceof IpAddrValidator);
  }
  
  @Test
  void testIsValid() {
    assertTrue(port.isValid("1337") && ip.isValid("0.0.0.0"));
  }

}
