package kodex.pluginutils.model.steps;

import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.BinaryString;
import kodex.pluginutils.model.content.RGBByteList;

/** */
public class RGBByteListToBinaryString implements ChainStep {

  @Override
  public void decode(Content<?> right, Content<?> left) {
    RGBByteList leftlist = (RGBByteList) left;
    BinaryString rightstring = (BinaryString) right;

    String input = rightstring.getString();
    for (int i = 0; i < input.length() / 8; i++) {
      String rgb = input.substring(i * 8, i * 8 + 8);
      leftlist.add(rgb);
    }

    leftlist.setHeader(rightstring.getHeader());
  }

  @Override
  public void encode(Content<?> left, Content<?> right) {
    RGBByteList leftlist = (RGBByteList) left;
    BinaryString rightstring = (BinaryString) right;

    String result = "";
    for (int i = 0; i < leftlist.getList().size(); i++) {
      result += leftlist.get(i);
    }
    rightstring.setString(result);

    rightstring.setHeader(leftlist.getHeader());
  }
}
