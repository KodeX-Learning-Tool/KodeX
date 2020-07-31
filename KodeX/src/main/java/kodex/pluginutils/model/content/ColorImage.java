package kodex.pluginutils.model.content;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

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
    header = new HashMap<>();
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
    if (input == null) {
      System.out.println("Invalid import, no import to validate");
      return false;
    }

    if (input.getWidth() > MAX_IMAGE_WIDTH || MIN_IMAGE_WIDTH > input.getWidth()) {
      return false;
    }

    if (input.getHeight() > MAX_IMAGE_HEIGHT || MIN_IMAGE_HEIGHT > input.getHeight()) {
      return false;
    }

    image = input;

    return true;

  }
  
  @Override
  public void export(File file) {
    try {
      FileWriter writer = new FileWriter(file);

      //header
      writer.write("HEADER\n");
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
          row += getColor(x, y).toString().substring(0, 8) + " ";
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
