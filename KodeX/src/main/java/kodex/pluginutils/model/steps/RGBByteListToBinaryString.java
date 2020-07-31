package kodex.pluginutils.model.steps;

import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.BinaryString;
import kodex.pluginutils.model.content.RGBByteList;

/**
 * This class represents the bidirectional step between RGBByteList and BinaryString.
 * It contains the functionality to decode and encode the content between
 * these explicitly defined levels.
 * 
 * @author Yannick Neubert
 * @version 1.0
 */

public class RGBByteListToBinaryString implements ChainStep {

  @Override
  public void decode(Content<?> input, Content<?> output) {
    RGBByteList list = (RGBByteList) output;
    BinaryString string = (BinaryString) input;

    String data = string.getString();
    for (int i = 0; i < data.length() / 8; i++) {
      String rgb = data.substring(i * 8, i * 8 + 8);
      list.add(rgb);
    }

    list.setHeader(string.getHeader());
  }

  @Override
  public void encode(Content<?> input, Content<?> output) {
    RGBByteList list = (RGBByteList) input;
    BinaryString string = (BinaryString) output;

    String result = "";
    for (int i = 0; i < list.getList().size(); i++) {
      result += list.get(i);
    }
    string.setString(result);

    string.setHeader(list.getHeader());
  }
}
