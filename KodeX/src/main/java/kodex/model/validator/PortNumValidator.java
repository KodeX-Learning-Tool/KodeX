package kodex.model.validator;

import java.util.regex.Pattern;

/**
 * This class is a utility singleton used to validate a pot number.
 *
 * @author Leonhard Kraft
 * @version 1.0
 */
public class PortNumValidator implements Validatable {

  private static final String PORT_INT_REGEX = "\\d{1,5}";

  private static final Pattern PORT_INT_PATTERN = Pattern.compile(PORT_INT_REGEX);

  private static PortNumValidator instance = new PortNumValidator();

  /*
   * Port value range is set by ServerSocket class.
   *
   * Source: https://docs.oracle.com/javase/7/docs/api/java/net/ServerSocket.html
   */
  private static final int MIN_PORT_VALUE = 0;
  private static final int MAX_PORT_VALUE = 65535;

  /**
   * Returns the instance of this class.
   *
   * @return The PortNumValidator instance.
   */
  public static PortNumValidator getInstance() {
    return instance;
  }

  private PortNumValidator() {
  }

  @Override
  public boolean isValid(String input) {

    /*
     * Validate portNumber:
     *
     * -is not null
     *
     * -text consists only of numerical digits
     *
     * -text length is in the range [0, 5]
     *
     * -text as integer is in the accepted port range [0, 65535]
     */

    if (input == null) {
      return false;
    }

    if (!PORT_INT_PATTERN.matcher(input).matches()) {
      // input is not an integer with length in range [1, 5]
      return false;
    }

    int portNumber = Integer.parseInt(input);

    // checks if text as integer is in the accepted port range [0, 65535]
    return !((portNumber < MIN_PORT_VALUE) || (MAX_PORT_VALUE < portNumber));
  }
}
