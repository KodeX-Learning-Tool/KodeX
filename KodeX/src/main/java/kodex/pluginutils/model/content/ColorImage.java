package kodex.pluginutils.model.content;

import java.io.File;
import javafx.scene.image.WritableImage;

/**
 * This class holds data in Image format. An ColorImage consists of a Writable RGB Image. Extending
 * AbstractImage, it adds validation and exporting capabilities to JavaFX's WritableImage.
 *
 * @author Raimon Gramlich
 */
public class ColorImage extends AbstractImage {

  /** Creates a new ColorImage. */
  public ColorImage() {
    super.image = new WritableImage(1, 1);
  }

  /**
   * Creates a new ColorImage and sets its data to the image passed in the arguments.
   *
   * @param image The WritableImage to be used as data
   */
  public ColorImage(WritableImage image) {
    this.image = image;
  }

  @Override
  public boolean isValid(WritableImage input) {
    Boolean isValid = true;

    if (input.getWidth() > MAX_IMAGE_WIDTH && MIN_IMAGE_WIDTH > input.getWidth()) {
      isValid = false;
    }

    if (input.getHeight() > MAX_IMAGE_HEIGHT && MIN_IMAGE_HEIGHT > input.getHeight()) {
      isValid = false;
    }

    image = input;

    return isValid;
  }

  @Override
  protected File toFile() {
    // TODO Auto-generated method stub
    return null;
  }
}
