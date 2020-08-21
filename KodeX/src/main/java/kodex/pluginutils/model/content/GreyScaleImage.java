package kodex.pluginutils.model.content;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.WritableImage;
import kodex.exceptions.InvalidInputException;
import kodex.model.I18N;

/**
 * This class holds data in Image format. An GrayScaleImage consists of a
 * WritableImage where each pixels red, green and blue value are all equal.
 * Extending AbstractImage, it adds validation and exporting capabilities to
 * JavaFX's WritableImage.
 * 
 * @author Patrick Spiesberger
 * @author Raimon Gramlich
 * 
 * @version 1.0
 */
public class GreyScaleImage extends AbstractImage {

  /** Creates a new GrayScaleImage. */
  public GreyScaleImage() {
    super.image = new WritableImage(1, 1);
    header = new HashMap<>();
  }

  /**
   * Creates a new GrayScaleImage and sets its data to the image passed in the
   * arguments.
   *
   * @param image The WritableImage to be used as data
   */
  public GreyScaleImage(WritableImage image) {
    this.image = image;
  }

  @Override
  public boolean isValid(WritableImage input) throws InvalidInputException {
    if (input == null) {
      throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
          I18N.get("alert.input.invalid"), 
          "Content validation input is empty");
    }

    if (input.getWidth() > MAX_IMAGE_WIDTH || MIN_IMAGE_WIDTH > input.getWidth() 
          || input.getHeight() > MAX_IMAGE_HEIGHT || MIN_IMAGE_HEIGHT > input.getHeight()) {
      throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
          I18N.get("alert.input.invalid"), 
          "An image can be no larger than " 
          + MAX_IMAGE_HEIGHT + " by " + MAX_IMAGE_WIDTH + "pixels");
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
          throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
              I18N.get("alert.input.invalid"), 
              "Pixel [" + x + "," + y + "] isn't valid");
        }
      }
    }

    image = input;

    return true;
  }

  @Override
  public void export(File file) {
    try {
      FileWriter writer = new FileWriter(file);

      // header
      writer.write("HEADER\n");
      HashMap<String, Object> map = (HashMap<String, Object>) header;
      map.forEach((key, value) -> {
        try {
          writer.write(key + " " + value + "\n");
        } catch (IOException e) {
          e.printStackTrace();
        }
      });

      // content
      writer.write("CONTENT\n");
      String row = "";
      for (int y = 0; y < getHeight(); y++) {
        row = "";
        for (int x = 0; x < getWidth(); x++) {
          row += getColor(x, y).toString().substring(0, 8) + " ";
        }
        row = row.substring(0, row.length() - 1);

        if (y != getHeight() - 1) {
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
