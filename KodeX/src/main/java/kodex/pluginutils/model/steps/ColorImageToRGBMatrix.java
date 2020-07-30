package kodex.pluginutils.model.steps;

import java.util.HashMap;

import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.ColorImage;
import kodex.pluginutils.model.content.RGBMatrix;

/**
 * This class represents the bidirectional step between ColorImage and RGBMatrix.
 * It contains the functionality to decode and encode the content between 
 * these explicitly defined levels.
 * 
 * @author Yannick Neubert
 * @version 1.0
 */

public class ColorImageToRGBMatrix implements ChainStep {

  @Override
  public void decode(Content<?> input, Content<?> output) {
    ColorImage img = (ColorImage) output;
    RGBMatrix mtx = (RGBMatrix) input;

    int width = (int) mtx.getHeader().get("width");
    int height = (int) mtx.getHeader().get("height");
    img.setSize(width, height);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        img.setColor(x, y, mtx.get(x, y));
      }
    }

    img.setHeader(mtx.getHeader());
  }

  @Override
  public void encode(Content<?> input, Content<?> output) {
    ColorImage img = (ColorImage) input;
    RGBMatrix mtx = (RGBMatrix) output;

    mtx.setSize(img.getWidth(), img.getHeight());
    for (int y = 0; y < img.getHeight(); y++) {
      for (int x = 0; x < img.getWidth(); x++) {
        mtx.set(x, y, img.getColor(x, y));
      }
    }

    HashMap<String, Object> map = new HashMap<>();
    map.put("width", Integer.valueOf(img.getWidth()));
    map.put("height", Integer.valueOf(img.getHeight()));
    mtx.setHeader(map);
  }
}
