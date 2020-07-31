package kodex.pluginutils.model.steps;

import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.BinaryMatrix;
import kodex.pluginutils.model.content.BitList;

/** */
public class MatrixToBitList implements ChainStep {

  @SuppressWarnings("unchecked")
  @Override
  public void decode(Content<?> right, Content<?> left) {
    BinaryMatrix leftmtx = (BinaryMatrix) left;
    BitList rightlist = (BitList) right;

    int width = (int) rightlist.getHeader().get("width");
    int height = (int) rightlist.getHeader().get("height");
    leftmtx.setSize(width, height);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        leftmtx.set(x, y, rightlist.get(y * width + x));
      }
    }

    leftmtx.setHeader(rightlist.getHeader());
  }

  @SuppressWarnings("unchecked")
  @Override
  public void encode(Content<?> left, Content<?> right) {
    BinaryMatrix leftmtx = (BinaryMatrix) left;
    BitList rightlist = (BitList) right;

    for (int y = 0; y < leftmtx.getHeight(); y++) {
      for (int x = 0; x < leftmtx.getWidth(); x++) {
        rightlist.add(leftmtx.get(x, y));
      }
    }

    rightlist.setHeader(leftmtx.getHeader());
  }
}
