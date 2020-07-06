package kodex.model.validator;

import java.util.regex.Pattern;

/**
 * This class is a utility singleton used to validate an ip address.
 * 
 * @author Leonhard Kraft
 * 
 * @version 1.0
 *
 */
public class IPAddrValidator implements Validatable {

    private static final String ZERO_TO_255 = "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])";

    private static final String IP_V4_REGEX = ZERO_TO_255 + "\\."
                                            + ZERO_TO_255 + "\\."
                                            + ZERO_TO_255 + "\\."
                                            + ZERO_TO_255;

    // use precompiled pattern to boost performance
    private static final Pattern IP_V4_PATTERN = Pattern.compile(IP_V4_REGEX);

    private static IPAddrValidator instance = new IPAddrValidator();

    private IPAddrValidator() {
    }

    /**
     * Returns the instance of this class.
     * 
     * @return The PortNumValidator instance.
     */
    public static IPAddrValidator getInstance() {
        return instance;
    }

    @Override
    public boolean isValid(String input) {

        /*
         * checks input against the ipv4 pattern and checks if input is null
         */
        return (input != null) && (IP_V4_PATTERN.matcher(input).matches());
    }

}
