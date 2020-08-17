package kodex.pluginutils.model.content;

import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import kodex.plugininterface.Content;

/**
 * This class holds data in image format. It adds validation and exporting capabilities to JavaFX's
 * WritableImage.
 *
 * @author Raimon Gramlich
 * @version 1.0
 */
public abstract class AbstractImage extends Content<WritableImage> {

  /** The Constant MAX_IMAGE_WIDTH. */
  protected static final int MAX_IMAGE_WIDTH = 500;

  /** The Constant MAX_IMAGE_HEIGHT. */
  protected static final int MAX_IMAGE_HEIGHT = 500;

  /** The Constant MIN_IMAGE_WIDTH. */
  protected static final int MIN_IMAGE_WIDTH = 0;

  /** The Constant MIN_IMAGE_HEIGHT. */
  protected static final int MIN_IMAGE_HEIGHT = 0;

  /** The WritableImage containing this Contents data. */
  protected WritableImage image;

  public Color getColor(int x, int y) {
    return image.getPixelReader().getColor(x, y);
  }

  public int getHeight() {
    return (int) image.getHeight();
  }

  /**
   * Returns the WritableImage containing this Contents data.
   *
   * @return The WritableImage containing this Contents data
   */
  public WritableImage getImage() {
    return image;
  }

  // below some shortcuts for common actions
  public int getWidth() {
    return (int) image.getWidth();
  }

  public void setColor(int x, int y, Color color) {
    image.getPixelWriter().setColor(x, y, color);
  }

  /**
   * Sets the WritableImage containing this Contents data.
   *
   * @param image The WritableImage containing this Contents data
   */
  public void setImage(WritableImage image) {
    this.image = image;
  }

  /**
   * Sets the size of this Contents WritableImage.
   *
   * @param width The width of the new Image
   * @param height The height of the new Image
   * @throws IllegalArgumentException If either argument is less than or equal 0
   */
  public void setSize(int width, int height) {
    image = new WritableImage(width, height);
  }
}
