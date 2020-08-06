package kodex.pluginutils.model.steps;

import java.util.HashMap;
import java.util.Map;

import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.BinaryString;
import kodex.pluginutils.model.content.ByteList;

/**
 * Step from List (byte) to String (binary) bidirectional.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 */
public class ByteListToBinaryString implements ChainStep {

  @SuppressWarnings("unchecked")
  @Override
  public void decode(Content<?> right, Content<?> left) {
    ByteList leftlist = (ByteList) left;
    BinaryString rightstring = (BinaryString) right;
    String sequence = "";
    
    leftlist.getList().clear();

    String input = rightstring.getString();
    for (int i = 0; i < input.length() / 8; i++) {
      sequence = input.substring(i * 8, i * 8 + 8);
      leftlist.add(sequence);
    }
    
    rightstring.getHeader().put("unit-length", 8); //Length of Byte
    leftlist.setHeader(rightstring.getHeader());
  }

  @Override
  public void encode(Content<?> left, Content<?> right) {
    ByteList leftlist = (ByteList) left;
    BinaryString rightstring = (BinaryString) right;
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < leftlist.getList().size(); i++) {
      result.append(leftlist.get(i));
    }

    rightstring.setString(result.toString());
    
    Map<String, Object> header = new HashMap<>();
    header.put("unit-length", 8); //Length of Byte
    
    rightstring.setHeader(header);

  }
}
