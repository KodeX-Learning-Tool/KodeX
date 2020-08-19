package kodex.pluginutils.model.content;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javafx.scene.control.Alert.AlertType;
import kodex.InvalidInputException;
import kodex.model.I18N;

/**
 * This class holds data in LinkedList format. An BitList consists of Intergers.
 * Extending AbstractList, it adds validation and exporting capabilities to
 * Javas List.
 * 
 * @author Patrick Spiesberger
 * @author Raimon Gramlich
 * 
 * @version 1.0
 */
public class BitList extends AbstractList<Integer> {

  /** Creates a new RGBList. */
  public BitList() {
    super.list = new LinkedList<Integer>();
  }


  @Override
  public boolean isValid(Object input) throws InvalidInputException {
    if (input == null) {
      throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
          I18N.get("alert.input.invalid"), 
          "Content validation input is empty");
    }
    int bit = (Integer) input;
    return (bit == 0 || bit == 1);
  }


  @Override
  public void export(File file) {
    try {
      FileWriter writer = new FileWriter(file);

      //header
      writer.write("HEADER\n");
      @SuppressWarnings("unchecked")
      HashMap<String, Object> map = (HashMap<String, Object>) header;
      map.forEach((key, value) -> { 
        try {
          writer.write(key + " " + value + "\n");
        } catch (IOException e) {
          e.printStackTrace();
        }
      });

      //content
      writer.write("CONTENT\n");
      for (int y = 0; y < size(); y++) {

        String row = get(y).toString().substring(0, 1);

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
