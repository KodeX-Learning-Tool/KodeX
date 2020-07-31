package kodex.pluginutils.model.content;

import java.io.File;

/**
 * This class holds data in Matrix format. An BinaryMatrix consists of a 2D array [rows][cols]
 * containing elements of the type Integer.
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
  public boolean isValid(Object input) {
    if (input == null) {
      return false;
    }
    int bit = (Integer) input;
    return (bit == 0 || bit == 1);
  }

  @Override
  public void export(File file) {
    // TODO Auto-generated method stub
    
  }
}
