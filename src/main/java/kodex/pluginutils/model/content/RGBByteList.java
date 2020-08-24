package kodex.pluginutils.model.content;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javafx.scene.control.Alert.AlertType;
import kodex.exceptions.InvalidInputException;
import kodex.model.I18N;

/**
 * This class holds data in LinkedList format. An RGBByteList consists of RGB triplets in exactly
 * that order in binary. Extending AbstractList, it adds validation and exporting capabilities to
 * Java's List.
 * 
 * @author Yannick Neubert
 * @version 1.0
 */
public class RGBByteList extends AbstractList<String> {

  /** Creates a new RGBByteList. */
  public RGBByteList() {
    super.list = new LinkedList<String>();
  }

  @Override
  public boolean isValid(Object input) throws InvalidInputException { 
    RGBByteList object;
    
    if (input == null) {
      throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
          I18N.get("alert.input.invalid"), 
          "Content validation input is empty");
    }

    try {
      object = ((RGBByteList) input);
    } catch (ClassCastException e) {
      throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
          I18N.get("alert.input.invalid"), 
          "Input is of wrong type");
    }

    if (object.size() % 3 != 0) {
      throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
          I18N.get("alert.input.invalid"), 
          "Invalid input, input does not excludingly contain rgb-triplets");
    }

    for (int i = 0; i < object.size(); i++) {
      String rgb = object.get(i);
      if (rgb.length() != 8) {
        throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
            I18N.get("alert.input.invalid"), 
            "Invalid input, input does not excludingly contain rgb values");
      }

      for (int j = 0; j < rgb.length(); j++) {
        if (rgb.charAt(j) != '0' && rgb.charAt(j) != '1') {
          throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
              I18N.get("alert.input.invalid"), 
             "Invalid import, import does not excludingly contain rgb values");
        }
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
      if (header != null) {
        @SuppressWarnings("unchecked")
        HashMap<String, Object> map = (HashMap<String, Object>) header;
        map.forEach((key, value) -> { 
          try {
            writer.write(key + " " + value + "\n");
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
      }

      //content
      writer.write("CONTENT\n");
      for (int y = 0; y < size() / 3; y++) {

        String row = get(y * 3).substring(0, 8);
        row += " " + get(y * 3 + 1).substring(0, 8);
        row += " " + get(y * 3 + 2).substring(0, 8);

        if (y != size() - 1) {
          writer.write(row + "\n");
        } else {
          writer.write(row);
        }
      }

      writer.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

}
