package kodex.pluginutils.model.steps;

import javafx.scene.paint.Color;
import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.RGBList;
import kodex.pluginutils.model.content.RGBMatrix;

/**
 * This class represents the bidirectional step between RGBMatrix and RGBList.
 * It contains the functionality to decode and encode the content between
 * these explicitly defined levels.
 * 
 * @author Yannick Neubert
 * @version 1.0
 */

public class RGBMatrixToRGBList implements ChainStep {

  @SuppressWarnings("unchecked")
  @Override
  public void decode(Content<?> input, Content<?> output) {
    RGBMatrix mtx = (RGBMatrix) output;
    RGBList list = (RGBList) input;

    int width = (int) list.getHeader().get("width");
    int height = (int) list.getHeader().get("height");
    mtx.setSize(width, height);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Color color = list.get(y * width + x);
        mtx.set(x, y, color);
      }
    }

    mtx.setHeader(list.getHeader());
  }

  @SuppressWarnings("unchecked")
  @Override
  public void encode(Content<?> input, Content<?> output) {
    RGBMatrix mtx = (RGBMatrix) input;
    RGBList list = (RGBList) output;

    for (int y = 0; y < mtx.getHeight(); y++) {
      for (int x = 0; x < mtx.getWidth(); x++) {
        list.add(mtx.get(x, y));
      }
    }

    list.setHeader(mtx.getHeader());
  }
}
