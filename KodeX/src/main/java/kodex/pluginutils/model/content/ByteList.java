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
 * This class holds data in LinkedList format. An List consists of Strings.
 * Extending AbstractList, it adds validation and exporting capabilities to
 * Java's List.
 * 
 * @author Patrick Spiesberger
 * @author Raimon Gramlich
 * 
 * @version 1.0
 */
public class ByteList extends AbstractList<String> {

  /** Creates a new ByteList. */
  public ByteList() {
    super.list = new LinkedList<String>();
  }

  @Override
  public boolean isValid(Object input) throws InvalidInputException {   
    ByteList object;
    
    if (input == null) {
      throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
          I18N.get("alert.input.invalid"), 
          "Content validation input is empty");
    }
    
    try {
      object = ((ByteList) input);
      if (object.size() > MAX_LIST_LENGTH) {
        throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
            I18N.get("alert.input.invalid"), 
            "A BitList cannot be larger than" + MAX_LIST_LENGTH);
      }
    } catch (ClassCastException e) {
      throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
          I18N.get("alert.input.invalid"), 
          "Input is of wrong type");
    }
    
    for (int i = 0; i < object.size(); i++) {
      if (!object.get(i).matches("[01]+") || object.get(i).length() != 8) {
        throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
            I18N.get("alert.input.invalid"), 
            "This is not a valid byte:\n" + object.get(i));
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
      for (int y = 0; y < size(); y++) {

        String row = get(y).toString().substring(0, 8);

        if (y != size() - 1)  {
          writer.write(row + "\n");
        } else {
          writer.write(row);
        }
      }

      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }
}
