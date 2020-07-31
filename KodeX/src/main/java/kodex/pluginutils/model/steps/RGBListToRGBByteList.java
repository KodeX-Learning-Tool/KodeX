package kodex.pluginutils.model.steps;

import java.util.LinkedList;
import javafx.scene.paint.Color;
import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.RGBByteList;
import kodex.pluginutils.model.content.RGBList;

/**
 * This class represents the bidirectional step between RGBList and RGBByteList.
 * It contains the functionality to decode and encode the content between
 * these explicitly defined levels.
 * 
 * @author Yannick Neubert
 * @version 1.0
 */

public class RGBListToRGBByteList implements ChainStep {

  private static String percentToByteString(double input) {
    int value = ((int) (input * 255));
    String bytes = Integer.toBinaryString(value);
    String zeros = "0".repeat(8 - bytes.length());
    bytes = zeros.concat(bytes);
    return bytes;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void decode(Content<?> input, Content<?> output) {
    RGBList outputlist = (RGBList) output;
    RGBByteList inputlist = (RGBByteList) input;

    for (int i = 0; i < inputlist.size() / 3; i++) {
      int red = Integer.parseInt(inputlist.get(i * 3), 2);
      int green = Integer.parseInt(inputlist.get(i * 3 + 1), 2);
      int blue = Integer.parseInt(inputlist.get(i * 3 + 2), 2);
      Color color = Color.rgb(red, green, blue);
      outputlist.add(color);
    }

    outputlist.setHeader(inputlist.getHeader());
  }

  @SuppressWarnings("unchecked")
  @Override
  public void encode(Content<?> input, Content<?> output) {
    RGBList inputlist = (RGBList) input;
    RGBByteList outputlist = (RGBByteList) output;

    LinkedList<String> rgblist = new LinkedList<>();
    for (int i = 0; i < inputlist.size(); i++) {
      Color color = inputlist.get(i);
      rgblist.add(percentToByteString(color.getRed()));
      rgblist.add(percentToByteString(color.getBlue()));
      rgblist.add(percentToByteString(color.getGreen()));
    }
    outputlist.setList(rgblist);

    outputlist.setHeader(inputlist.getHeader());
  }
}
