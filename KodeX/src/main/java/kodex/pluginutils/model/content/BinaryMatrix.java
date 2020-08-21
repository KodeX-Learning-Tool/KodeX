package kodex.pluginutils.model.content;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javafx.scene.control.Alert.AlertType;
import kodex.exceptions.InvalidInputException;
import kodex.model.I18N;

/**
 * This class holds data in Matrix format. An BinaryMatrix consists of a 2D array [rows][cols]
 * containing elements of the type Integer with the value 0 or 1.
 * 
 * @author Patrick Spiesberger
 * @author Raimon Gramlich
 * 
 * @version 1.0
 */
public class BinaryMatrix extends AbstractMatrix<Integer> {

  /**
   * Creates a new BinaryMatrix with the given dimensions.
   *
   * @param width The matrix's width
   * @param height The matrix's height
   * @throws IllegalArgumentException If either argument is less than or equal to 0
   */
  public BinaryMatrix(int width, int height) throws IllegalArgumentException {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException();
    }
    super.matrix = new Integer[height][width];
  }

  @Override
  public boolean isValid(Object input) throws InvalidInputException {
    BinaryMatrix object;
    
    if (input == null) {
      throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
          I18N.get("alert.input.invalid"), 
          "Content validation input is empty");
    }
    
    try {
      object = ((BinaryMatrix) input);
    } catch (ClassCastException e) {
      throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
          I18N.get("alert.input.invalid"), 
          "Input is of wrong type");
    }

    if (object.getWidth() > MAX_MATRIX_WIDTH || MIN_MATRIX_WIDTH > object.getWidth()
        || object.getHeight() > MAX_MATRIX_HEIGHT || MIN_MATRIX_HEIGHT > object.getHeight()) {
      throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
          I18N.get("alert.input.invalid"), 
          "An RGB Matrix can be no larger than " + MAX_MATRIX_HEIGHT + " by " + MAX_MATRIX_WIDTH);
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
      String row = "";
      for (int y = 0; y < getHeight(); y++) {
        row = "";
        for (int x = 0; x < getWidth(); x++) {
          row += get(x, y).toString() + " ";
        }
        row = row.substring(0, row.length() - 1);

        if (y != getHeight() - 1)  {
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
