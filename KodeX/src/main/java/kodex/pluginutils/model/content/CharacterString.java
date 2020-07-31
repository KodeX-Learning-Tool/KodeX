package kodex.pluginutils.model.content;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class holds data in string format. A CharacterString consists of characters. 
 * Extending AbstractString, it adds validation and exporting capabilities to Java's
 * String.
 * 
 * @author Raimon Gramlich
 * @author Parick Spiesberger
 * 
 * @version 1.0
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
    try {
      FileWriter writer = new FileWriter(file);

      //header
      writer.write("HEADER\n");
      header.forEach((key, value) -> { 
        try {
          writer.write(key + " " + value + "\n");
        } catch (IOException e) {
          e.printStackTrace();
        }
      });

      //content
      writer.write("CONTENT\n");
      writer.write(data);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
}
