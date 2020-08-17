package kodex.pluginutils.model.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

class AbstractImageTest {
  private static int width = 5;
  private static int height = 10;
  private static ColorImage cimg;
  private static WritableImage img;
  
  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    cimg = new ColorImage();
    
    img = new WritableImage(width, height);
    PixelWriter writer = img.getPixelWriter();
    PixelReader reader = img.getPixelReader();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Color c = new Color(Math.random(), Math.random(), Math.random(), 0);
        System.out.println(c);
        writer.setColor(x, y, c);
        System.out.println(reader.getColor(x, y));
      }
    }
    
  }

  @Test
  void testSetAndGetDimesions() {
    cimg.setImage(img);
    assertEquals(cimg.getHeight(), height);
    assertEquals(cimg.getWidth(), width);
  }
  
  @Test
  void testSetandGet() {
    assertThrows(IllegalArgumentException.class, () -> cimg.setImage(null));
    cimg.setImage(img);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        assertEquals(img.getPixelReader().getColor(x, y), cimg.getColor(x, y));
        assertEquals(img.getPixelReader().getColor(x, y),
            cimg.getImage().getPixelReader().getColor(x, y));
//        System.out.print(cimg.getColor(x, y) + " ");
      }
      System.out.println();
    }
    Color c = new Color(0.5, 0.1, 0.3, 0);
    cimg.setColor(width -  1, height - 1, c);
    assertEquals(c, cimg.getColor(width - 1, height - 1));
//    assertThrows(ArrayIndexOutOfBoundsException.class, () -> bmtx.set(width, height, 0));
//    bmtx.setSize(height, width);
//    assertEquals(bmtx.getHeight(), width);
//    assertEquals(bmtx.getWidth(), height);
//    assertNull(bmtx.get(height - 1, width - 1));
//    assertThrows(IllegalArgumentException.class, () -> bmtx.setSize(0, 0));
//    assertThrows(IllegalArgumentException.class, () -> bmtx.setMatrix(failmtx));
  }  
}
