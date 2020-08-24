package kodex.pluginutils.model.steps;


import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.ByteList;
import kodex.pluginutils.model.content.ByteMatrix;

/**
 * Step from Matrix (decimal) to List (byte) bidirectional.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 */
public class ByteMatrixToByteList implements ChainStep {

  @SuppressWarnings("unchecked")
  @Override
  public void decode(Content<?> right, Content<?> left) {
    ByteMatrix leftmtx = (ByteMatrix) left;
    ByteList rightlist = (ByteList) right;

    int width = (int) rightlist.getHeader().get("width");
    int height = (int) rightlist.getHeader().get("height");
    leftmtx.setSize(width, height);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        leftmtx.set(x, y, Integer.parseInt(rightlist.get(y * width + x), 2));
      }
    }

    leftmtx.setHeader(rightlist.getHeader());
  }

  @SuppressWarnings("unchecked")
  @Override
  public void encode(Content<?> left, Content<?> right) {
    ByteMatrix leftmtx = (ByteMatrix) left;
    ByteList rightlist = (ByteList) right;
    
    rightlist.getList().clear();

    for (int y = 0; y < leftmtx.getHeight(); y++) {
      for (int x = 0; x < leftmtx.getWidth(); x++) {
        String num = String.format("%8s", Integer.toBinaryString(leftmtx.get(x, y)));
        rightlist.add(num.replace(' ', '0'));
      }
    }
    rightlist.setHeader(leftmtx.getHeader());
  }
}
