package kodex.pluginutils.model.steps;

import java.util.LinkedList;
import javafx.scene.paint.Color;
import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.RGBByteList;
import kodex.pluginutils.model.content.RGBList;

/** */
public class RGBListToRGBByteList implements ChainStep {

  private static String percentToByteString(double input) {
    int value = ((int) (input * 255));
    String bytes = Integer.toBinaryString(value);
    String zeros = "0".repeat(8 - bytes.length());
    bytes = zeros.concat(bytes);
    return bytes;
  }

  @Override
  public void decode(Content<?> right, Content<?> left) {
    RGBList leftlist = (RGBList) left;
    RGBByteList rightlist = (RGBByteList) right;

    for (int i = 0; i < rightlist.size() / 3; i++) {
      int red = Integer.parseInt(rightlist.get(i * 3), 2);
      int green = Integer.parseInt(rightlist.get(i * 3 + 1), 2);
      int blue = Integer.parseInt(rightlist.get(i * 3 + 2), 2);
      Color color = Color.rgb(red, green, blue);
      leftlist.add(color);
    }

    leftlist.setHeader(rightlist.getHeader());
  }

  @Override
  public void encode(Content<?> left, Content<?> right) {
    RGBList leftlist = (RGBList) left;
    RGBByteList rightlist = (RGBByteList) right;

    LinkedList<String> rgblist = new LinkedList<>();
    for (int i = 0; i < leftlist.size(); i++) {
      Color color = leftlist.get(i);
      rgblist.add(percentToByteString(color.getRed()));
      rgblist.add(percentToByteString(color.getBlue()));
      rgblist.add(percentToByteString(color.getGreen()));
    }
    rightlist.setList(rgblist);

    rightlist.setHeader(leftlist.getHeader());
  }
}
