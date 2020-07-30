package kodex.pluginutils.model.steps;


import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.ByteList;
import kodex.pluginutils.model.content.DecMatrix;

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
        leftmtx.set(x, y, Integer.parseInt(rightlist.get(y * width + x)));
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
        String num = String.format("%8s", Integer.toBinaryString(leftmtx.get(x, y)));
        rightlist.add(num.replace(' ','0'));
      }
    }
    rightlist.setHeader(leftmtx.getHeader());
  }
}
