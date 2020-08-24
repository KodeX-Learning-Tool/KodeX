package kodex.pluginutils.model.steps;

import java.util.HashMap;
import java.util.Map;

import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.BinaryString;
import kodex.pluginutils.model.content.BitList;

/**
 * Step from List (bit) to String (binary) bidirectional.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 */
public class BitListToBinaryString implements ChainStep {

  @SuppressWarnings("unchecked")
  @Override
  public void decode(Content<?> right, Content<?> left) {
    BitList leftlist = (BitList) left;
    BinaryString rightstring = (BinaryString) right;
    
    leftlist.getList().clear();

    String input = rightstring.getString();
    
    for (int i = 0; i < input.length(); i++) {
      String value = input.substring(i, i + 1);
      leftlist.add(Integer.valueOf(value));
    }

    if (leftlist.getHeader() == null || leftlist.getHeader().isEmpty()) {
      
      Map<String, Object> header = new HashMap<>();
      Map<String, Object> map =  rightstring.getHeader();
      
      for (Map.Entry<String, Object> entry: map.entrySet()) {
        header.put(entry.getKey(), entry.getValue());
      }
      
      header.put("unit-length", 1); //Length of a Bit
      
      leftlist.setHeader(header);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void encode(Content<?> left, Content<?> right) {
    BitList leftlist = (BitList) left;
    BinaryString rightstring = (BinaryString) right;

    StringBuilder builder = new StringBuilder();
    
    for (int i = 0; i < leftlist.getList().size(); i++) {
      builder.append(leftlist.get(i));
    }
    rightstring.setString(builder.toString());
    
    if (rightstring.getHeader() == null || rightstring.getHeader().isEmpty()) {
      Map<String, Object> header = new HashMap<>();
      Map<String, Object> map = leftlist.getHeader();

      for (Map.Entry<String, Object> entry : map.entrySet()) {
        header.put(entry.getKey(), entry.getValue());
      }

      header.put("unit-length", 1); //Length of a Bit
      rightstring.setHeader(header);
    }
  }
}
