package kodex.pluginutils.model.content;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import kodex.exceptions.InvalidInputException;
import kodex.model.I18N;

/**
 * This class holds data in Matrix format. An RGBMatrix consists of a 2D array [rows][cols]
 * containing elements of the type Color.
 * 
 * @author Yannick Neubert
 * @version 1.0
 */
public class RGBMatrix extends AbstractMatrix<Color> {

  /**
   * Creates a new RGBMatrix with the given dimensions.
   *
   * @param width The matrix's width
   * @param height The matrix's height
   * @throws IllegalArgumentException If either argument is less than or equal to 0
   */
  public RGBMatrix(int width, int height) throws IllegalArgumentException {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException();
    }
    super.matrix = new Color[height][width];
  }

  @Override
  public boolean isValid(Object input) throws InvalidInputException {
    RGBMatrix object;

    if (input == null) {
      throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
          I18N.get("alert.input.invalid"), 
          "Content validation input is empty");
    }

    try {
      object = ((RGBMatrix) input);
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

    for (int y = 0; y < object.getHeight(); y++) {
      for (int x = 0; x < object.getWidth(); x++) {
        if (object.get(x, y) == null) {
          throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
              I18N.get("alert.input.invalid"), 
              "Input contains uninitialized values");
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
      String row = "";
      for (int y = 0; y < getHeight(); y++) {
        row = "";
        for (int x = 0; x < getWidth(); x++) {
          row += get(x, y).toString().substring(0, 8) + " ";
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
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
