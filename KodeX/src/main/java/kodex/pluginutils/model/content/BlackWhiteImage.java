package kodex.pluginutils.model.content;

import java.io.File;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * This class holds data in Image format. An BlackWhitImage consists of a WritableImage using only
 * the RGB values 0x000000 and 0xFFFFFF. Extending AbstractImage, it adds validation and exporting
 * capabilities to JavaFX's WritableImage.
 */
public class BlackWhiteImage extends AbstractImage {

  /** Creates a new BlackWhiteImage. */
  public BlackWhiteImage() {
    super.image = new WritableImage(1, 1);
  }

  /**
   * Creates a new BlackWhiteImage and sets its data to the image passed in the arguments.
   *
   * @param image The WritableImage to be used as data
   */
  public BlackWhiteImage(WritableImage image) {
    this.image = image;
  }

  @Override
  public boolean isValid(WritableImage input) {
    if (input == null) {
      return false;
    }   
    
    if (input.getWidth() > MAX_IMAGE_WIDTH && MIN_IMAGE_WIDTH > input.getWidth()) {
      return false;
    }

    if (input.getHeight() > MAX_IMAGE_HEIGHT && MIN_IMAGE_HEIGHT > input.getHeight()) {
      return false;
    }

    // Check if every pixel is black or white
    for (int x = 0; x < input.getWidth(); x++) {
      for (int y = 0; y < input.getHeight(); y++) {
        if (input.getPixelReader().getColor(x, y) != Color.BLACK
            && input.getPixelReader().getColor(x, y) != Color.WHITE) {
          System.out.println(x + " : " + y);
          return false;
        }
      }
    }
    
    image = input;
    
    return true;
  }

  @Override
  protected File toFile() {
    // TODO Auto-generated method stub
    return null;
  }
}
