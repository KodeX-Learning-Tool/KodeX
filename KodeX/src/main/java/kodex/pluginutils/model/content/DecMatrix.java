package kodex.pluginutils.model.content;

import java.io.File;
import javafx.scene.paint.Color;

/**
 * This class holds data in Matrix format. An DecMatrix consists of a 2D array [rows][cols]
 * containing elements of the type Integer.
 */
public class DecMatrix extends AbstractMatrix<Integer> {

  /**
   * Creates a new DecMatrix with the given dimensions.
   *
   * @param width The matrix's width
   * @param height The matrix's height
   * @throws IllegalArgumentException If either argument is less than or equal to 0
   */
  public DecMatrix(int width, int height) throws IllegalArgumentException {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException();
    }
    super.matrix = new Integer[height][width];
  }

  @Override
  protected File toFile() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isValid(Integer input) {
    if (input == null) {
      return false;
    }
    
    if (input >= 0 && input <= 256) {
      return true;
    }
    
    return false;
  }
}
