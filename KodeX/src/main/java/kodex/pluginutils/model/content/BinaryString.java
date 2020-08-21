package kodex.pluginutils.model.content;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javafx.scene.control.Alert.AlertType;
import kodex.exceptions.InvalidInputException;
import kodex.model.I18N;

/**
 * This class holds data in string format. A BinaryString consists of only 1's and 0's. Extending
 * AbstractString, it adds validation and exporting capabilities to Java's String.
 * 
 * @author Patrick Spiesberger
 * @version 1.0
 */
public class BinaryString extends AbstractString {

  /** Creates a new BinaryString. */
  public BinaryString() {
    super.data = "";
    header = new HashMap<>();
  }

  /** Creates a new BinaryString with the given input. */
  public BinaryString(String input) {
    super.data = input;
  }

  @Override
  public boolean isValid(String input) throws InvalidInputException {
    if (input == null) {
      throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
          I18N.get("alert.input.invalid"), 
          "Content validation input is empty");
    }
    for (int i = 0; i < input.length(); i++) {
      if (input.charAt(i) != '0' && input.charAt(i) != '1') {
        return false;
      }
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
