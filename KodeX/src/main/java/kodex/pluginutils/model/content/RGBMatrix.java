package kodex.pluginutils.model.content;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javafx.scene.paint.Color;

/**
 * This class holds data in Matrix format. An RGBMatrix consists of a 2D array [rows][cols]
 * containing elements of the type Color.
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
  public boolean isValid(Object input) {
    RGBMatrix object;

    if (input == null) {
      System.out.println("Invalid import, no import to validate");
      return false;
    }

    try {
      object = ((RGBMatrix) input);
    } catch (ClassCastException e) {
      System.out.println("Invalid import, import is of wrong type");
      return false;
    }

    if (object.getWidth() > MAX_MATRIX_WIDTH || MIN_MATRIX_WIDTH > object.getWidth()) {
      System.out.println("Ivalid import, wrong dimensions");
      return false;
    }

    if (object.getHeight() > MAX_MATRIX_HEIGHT || MIN_MATRIX_HEIGHT > object.getHeight()) {
      System.out.println("Ivalid import, wrong dimensions");
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
      @SuppressWarnings("unchecked")
      //the unchecked warning is suppressed since it has no negative effects
      //it's caused by the lambda expression used in writing the header in combination with
      //this class extending a generic superclass. There appears to be no way of avoiding this.
      HashMap<String, Object> map = (HashMap<String, Object>) header;
      map.forEach((key, value) -> { 
        try {
          writer.write(key + " " + value + "\n");
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      });

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
