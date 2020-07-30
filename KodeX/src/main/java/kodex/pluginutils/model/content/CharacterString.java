package kodex.pluginutils.model.content;

import java.io.File;

/**
 * This class holds data in string format. A CharacterString consists of characters. 
 * Extending AbstractString, it adds validation and exporting capabilities to Java's
 * String.
 */
public class CharacterString extends AbstractString {

  /** Instantiates a new CharacterString. */
  public CharacterString() {
    super.data = "";
  }

  /** Creates a new CharacterString and sets it's value.
   * 
   * @param str The String to be used as data 
   */
  public CharacterString(String str) {
    super.data = str;
  }

  @Override
  public boolean isValid(String input) {
    if (input == null) {
      return false;
    }
    return true;
  }

  @Override
  public void export(File file) {
    // TODO Auto-generated method stub
  }
  
}
