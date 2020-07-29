package kodex.pluginutils.model.steps;

import java.util.HashMap;
import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.BinaryString;
import kodex.pluginutils.model.content.ByteList;

/** */
public class ByteListToBinaryString implements ChainStep {

  @Override
  public void decode(Content<?> right, Content<?> left) {
    ByteList leftlist = (ByteList) left;
    BinaryString rightstring = (BinaryString) right;

    String input = rightstring.getString();
    for (int i = 0; i < input.length() / 8; i++) {
      byte[] value = input.substring(i * 8, i * 8 + 8).getBytes();
      leftlist.add(value[0]);
    }

    leftlist.setHeader(rightstring.getHeader());
  }

  @Override
  public void encode(Content<?> left, Content<?> right) {
    ByteList leftlist = (ByteList) left;
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
