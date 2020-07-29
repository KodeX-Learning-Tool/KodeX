package kodex.pluginutils.model.steps;

import javafx.scene.paint.Color;
import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.ByteList;
import kodex.pluginutils.model.content.DecMatrix;
import kodex.pluginutils.model.content.RGBList;
import kodex.pluginutils.model.content.RGBMatrix;

/** */
public class DecMatrixToByteList implements ChainStep {

  @Override
  public void decode(Content<?> right, Content<?> left) {
    DecMatrix leftmtx = (DecMatrix) left;
    ByteList rightlist = (ByteList) right;

    int width = (int) rightlist.getHeader().get("width");
    int height = (int) rightlist.getHeader().get("height");
    leftmtx.setSize(width, height);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        double colorValue = rightlist.get(y * width + x);
        leftmtx.set(x, y, new Color(colorValue, colorValue, colorValue, 1));
      }
    }

    leftmtx.setHeader(rightlist.getHeader());
  }

  @Override
  public void encode(Content<?> left, Content<?> right) {
    DecMatrix leftmtx = (DecMatrix) left;
    ByteList rightlist = (ByteList) right;

    for (int y = 0; y < leftmtx.getHeight(); y++) {
      for (int x = 0; x < leftmtx.getWidth(); x++) {
        rightlist.add((byte) leftmtx.get(x, y).getGreen());
      }
    }

    rightlist.setHeader(leftmtx.getHeader());
  }
}
