package kodex.pluginutils.model.steps;

import java.util.HashMap;
import javafx.scene.paint.Color;
import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.GreyScaleImage;
import kodex.pluginutils.model.content.ByteMatrix;

/*
 * Step from Image (GreyScale) to Matrix (decimal) bidirectional
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 */
public class GreyScaleImageToByteMatrix implements ChainStep {

  @SuppressWarnings("unchecked")
  @Override
  public void decode(Content<?> right, Content<?> left) {
    GreyScaleImage leftimg = (GreyScaleImage) left;
    ByteMatrix rightmtx = (ByteMatrix) right;

    int width = (int) rightmtx.getHeader().get("width");
    int height = (int) rightmtx.getHeader().get("height");
    double color;
    
    leftimg.setSize(width, height);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        color = ((double) rightmtx.get(x, y) / 255);
        leftimg.setColor(x, y, new Color(color, color, color, 1));
      }
    }

    leftimg.setHeader(rightmtx.getHeader());
  }

  @SuppressWarnings("unchecked")
  @Override
  public void encode(Content<?> left, Content<?> right) {
    GreyScaleImage leftimg = (GreyScaleImage) left;
    ByteMatrix rightmtx = (ByteMatrix) right;

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
