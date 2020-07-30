package kodex.pluginutils.model.content;

import java.io.File;
import javafx.scene.image.WritableImage;

/**
 * This class holds data in Image format. An GrayScaleImage consists of a WritableImage where each
 * pixels red, green and blue value are all equal. Extending AbstractImage, it adds validation and
 * exporting capabilities to JavaFX's WritableImage.
 */
public class GreyScaleImage extends AbstractImage {

  /** Creates a new GrayScaleImage. */
  public GreyScaleImage() {
    super.image = new WritableImage(1, 1);
  }

  /**
   * Creates a new GrayScaleImage and sets its data to the image passed in the arguments.
   *
   * @param image The WritableImage to be used as data
   */
  public GreyScaleImage(WritableImage image) {
    this.image = image;
  }

  @Override
  public boolean isValid(WritableImage input) {
    if (input == null) {
      System.err.println("Invalid import, no file selected");
      return false;
    } else if (input.getWidth() > 500 || input.getHeight() > 500) {
      System.err.println("Invalid import, file too large");
      return false;
    }

    // Check if image is a greyscale image
    // Source of Code: https://stackoverflow.com/a/36157968
    int pixel;
    int red;
    int green;
    int blue;
    for (int x = 0; x < input.getWidth(); x++) {
      for (int y = 0; y < input.getHeight(); y++) {
        pixel = input.getPixelReader().getArgb(x, y);
        red = (pixel >> 16) & 0xff;
        green = (pixel >> 8) & 0xff;
        blue = (pixel) & 0xff;
        if (red != green || green != blue) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public void export(File file) {
    // TODO Auto-generated method stub
  }
  
}
