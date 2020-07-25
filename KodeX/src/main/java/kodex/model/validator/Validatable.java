package kodex.model.validator;

/**
 * This interface describes the functionality of a validator. A validator has to be able, to
 * validate a given sting.
 *
 * @author Leonhard Kraft
 * @version 1.0
 */
public interface Validatable {

  /**
   * Checks whether a given String is valid.
   *
   * @param input The String to be validated.
   * @return true if the input String is valid, otherwise false
   */
  public boolean isValid(String input);
}
