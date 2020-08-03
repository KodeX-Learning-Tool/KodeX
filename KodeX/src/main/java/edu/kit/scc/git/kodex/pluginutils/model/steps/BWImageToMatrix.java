package edu.kit.scc.git.kodex.pluginutils.model.steps;

import java.util.HashMap;

import edu.kit.scc.git.kodex.plugininterface.ChainStep;
import edu.kit.scc.git.kodex.plugininterface.Content;
import edu.kit.scc.git.kodex.pluginutils.model.content.BinaryMatrix;
import edu.kit.scc.git.kodex.pluginutils.model.content.ColorImage;
import javafx.scene.paint.Color;

/**
 * Step from Image (black & white) to Matrix (bit) bidirectional.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 */
public class BWImageToMatrix implements ChainStep {

  @SuppressWarnings("unchecked")
  @Override
  public void decode(Content<?> right, Content<?> left) {
    ColorImage leftimg = (ColorImage) left;
    BinaryMatrix rightmtx = (BinaryMatrix) right;

    int width = (int) rightmtx.getHeader().get("width");
    int height = (int) rightmtx.getHeader().get("height");
    Color col;
    leftimg.setSize(width, height);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (rightmtx.get(x, y) == 0) {
          col = Color.BLACK;
        } else {
          col = Color.WHITE;
        }
        leftimg.setColor(x, y, col);
      }
    }
    leftimg.setHeader(rightmtx.getHeader());
  }

  @SuppressWarnings("unchecked")
  @Override
  public void encode(Content<?> left, Content<?> right) {
    ColorImage leftimg = (ColorImage) left;
    BinaryMatrix rightmtx = (BinaryMatrix) right;

    rightmtx.setSize(leftimg.getWidth(), leftimg.getHeight());
    for (int y = 0; y < leftimg.getHeight(); y++) {
      for (int x = 0; x < leftimg.getWidth(); x++) {
        if (leftimg.getColor(x, y).toString().equals(Color.BLACK.toString())) {
          rightmtx.set(x, y, 0);
        } else {
          rightmtx.set(x, y, 1);
        }
        
      }
    }

    HashMap<String, Object> map = new HashMap<>();
    map.put("width", Integer.valueOf(leftimg.getWidth()));
    map.put("height", Integer.valueOf(leftimg.getHeight()));
    rightmtx.setHeader(map);
  }
}
