package kodex.pluginutils.model.steps;

import java.util.HashMap;
import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.BinaryString;
import kodex.pluginutils.model.content.BitList;

/** */
public class BitListToBinaryString implements ChainStep {

  @SuppressWarnings("unchecked")
  @Override
  public void decode(Content<?> right, Content<?> left) {
    BitList leftlist = (BitList) left;
    BinaryString rightstring = (BinaryString) right;

    String input = rightstring.getString();
    for (int i = 0; i < input.length(); i++) {
      String rgb = input.substring(i, i);
      leftlist.add(Integer.valueOf(rgb));
    }

    leftlist.setHeader(rightstring.getHeader());
  }

  @SuppressWarnings("unchecked")
  @Override
  public void encode(Content<?> left, Content<?> right) {
    BitList leftlist = (BitList) left;
    BinaryString rightstring = (BinaryString) right;

    String result = "";
    for (int i = 0; i < leftlist.getList().size(); i++) {
      result += leftlist.get(i);
    }
    rightstring.setString(result);

    HashMap<String, Object> map = new HashMap<>();
    map.put("length", rightstring.length());
    rightstring.setHeader(map);

    rightstring.setHeader(leftlist.getHeader());
  }
}
