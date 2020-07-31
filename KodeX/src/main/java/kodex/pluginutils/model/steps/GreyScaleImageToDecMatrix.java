package kodex.pluginutils.model.steps;

import java.util.HashMap;

import javafx.scene.paint.Color;
import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.ColorImage;
import kodex.pluginutils.model.content.DecMatrix;

/**
 * Step from Image (GreyScale) to Matrix (decimal) bidirectional
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 */
public class GreyScaleImageToDecMatrix implements ChainStep {

  @SuppressWarnings("unchecked")
  @Override
  public void decode(Content<?> right, Content<?> left) {
    ColorImage leftimg = (ColorImage) left;
    DecMatrix rightmtx = (DecMatrix) right;

    int width = (int) rightmtx.getHeader().get("width");
    int height = (int) rightmtx.getHeader().get("height");
    leftimg.setSize(width, height);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        leftimg.setColor(x, y, new Color(rightmtx.get(x, y), rightmtx.get(x, y), rightmtx.get(x, y), 1));
      }
    }

    leftimg.setHeader(rightmtx.getHeader());
  }

  @SuppressWarnings("unchecked")
  @Override
  public void encode(Content<?> left, Content<?> right) {
    ColorImage leftimg = (ColorImage) left;
    DecMatrix rightmtx = (DecMatrix) right;

    rightmtx.setSize(leftimg.getWidth(), leftimg.getHeight());
    for (int y = 0; y < leftimg.getHeight(); y++) {
      for (int x = 0; x < leftimg.getWidth(); x++) {
        double color = (leftimg.getColor(x, y).getGreen()) * 255;
        rightmtx.set(x, y, (int) color);
      }
    }
    HashMap<String, Object> map = new HashMap<>();
    map.put("width", Integer.valueOf(leftimg.getWidth()));
    map.put("height", Integer.valueOf(leftimg.getHeight()));
    rightmtx.setHeader(map);
  }
}
