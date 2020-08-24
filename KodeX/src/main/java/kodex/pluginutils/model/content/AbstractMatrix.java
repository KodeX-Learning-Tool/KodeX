package kodex.pluginutils.model.content;

import kodex.plugininterface.Content;

/**
 * This class holds data in matrix format. The Matrix is stored in a 2D Array with the format
 * [rows][cols].
 * 
 * @author Yannick Neubert
 * @version 1.0
 */

//This is a workaround for the isValid method since generics could not be
//used in this context.
//This does however create the warning
//"Content is a raw type. References to generic type Content<T> should be
//parameterized"
//and requires class casting in the subclasses so that the isValid method
//receives the correct generic type of input.
@SuppressWarnings("rawtypes")
public abstract class AbstractMatrix<E> extends Content {
  /** The Constant MAX_IMAGE_WIDTH. */
  protected static final int MAX_MATRIX_WIDTH = 500;
  
  /** The Constant MAX_IMAGE_HEIGHT. */
  protected static final int MAX_MATRIX_HEIGHT = 500;
  
  /** The Constant MIN_IMAGE_WIDTH. */
  protected static final int MIN_MATRIX_WIDTH = 0;
  
  /** The Constant MIN_IMAGE_HEIGHT. */
  protected static final int MIN_MATRIX_HEIGHT = 0;
  
  /** The data for this content. */
  protected E[][] matrix;

  // below some shortcuts for common actions
  public E get(int x, int y) {
    return matrix[y][x];
  }

  /**
   * Returns the height of the Matrix containing this Contents data.
   *
   * @return The height of the Matrix containing this Contents data
   */
  public int getHeight() {
    return matrix.length;
  }

  /**
   * Returns the Matrix containing this Contents data. Note that this matrix has the Format
   * [rows][cols}
   *
   * @return The Matrix containing this Contents data
   */
  public E[][] getMatrix() {
    return matrix;
  }

  /**
   * Returns the width of the Matrix containing this Contents data.
   *
   * @return The width of the Matrix containing this Contents data
   */
  public int getWidth() {
    return matrix[0].length;
  }

  public void set(int x, int y, E element) {
    matrix[y][x] = element;
  }

  /**
   * Sets the Matrix containing this Contents data. Note that this matrix has the Format
   * [rows][cols}
   *
   * @param matrix The Matrix containing this Contents data
   */
  public void setMatrix(E[][] matrix) {
    if (matrix == null) {
      throw new IllegalArgumentException();
    }
    
    int len = matrix[0].length;
    for (int i = 0; i < matrix.length; i++) {
      if (matrix[i].length != len) {
        throw new IllegalArgumentException();
      }
    }
    this.matrix = matrix;
  }

  // Java doesn't allow the creation of an array of a generic type directly, so a cast is needed
  // This will, however, create the unavoidable warning "Unchecked cast"
  // which we can safely ignore in the context of our usage due to the abstract nature of this class
 
  /**
   * Creates a new matrix with the given width height.
   * 
   * @param width The width of the matrix.
   * @param height The height of the matrix.
   */
  @SuppressWarnings("unchecked")
  public void setSize(int width, int height) throws IllegalArgumentException {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException();
    }
    matrix = (E[][]) new Object[height][width];
  }
}
