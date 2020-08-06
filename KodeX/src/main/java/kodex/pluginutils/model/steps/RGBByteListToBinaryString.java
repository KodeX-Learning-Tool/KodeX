package kodex.pluginutils.model.steps;

import java.util.HashMap;
import java.util.Map;
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
 * @author Raimon Gramlich
 * 
 * @version 1.0
 */

public class RGBByteListToBinaryString implements ChainStep {
  
  /** The Constant defines how many elements belong together in a binary string. */
  private static final int BINARY_STRING_UNIT_LENGTH = 24;
  
  /** The Constant defines how many elements belong together in a RGB byte list. */
  private static final int RGB_BYTE_LIST_UNIT_LENGTH = 3;

  @SuppressWarnings("unchecked")
  @Override
  public void decode(Content<?> input, Content<?> output) {
    RGBByteList list = (RGBByteList) output;
    BinaryString string = (BinaryString) input;
    
    int ratio = BINARY_STRING_UNIT_LENGTH / RGB_BYTE_LIST_UNIT_LENGTH;
    
    list.getList().clear();
    
    String data = string.getString();
    for (int i = 0; i < data.length() / ratio; i++) {
      String rgb = data.substring(i * ratio, (i + 1) * ratio);
      list.add(rgb);
    }
    
    if (list.getHeader() == null || list.getHeader().isEmpty()) {
        
      Map<String, Object> header = new HashMap<>();
      Map<String, Object> map =  string.getHeader();
      
      for (Map.Entry<String, Object> entry: map.entrySet()) {
        header.put(entry.getKey(), entry.getValue());
      }
      
      // RGB byte list needs this key since it's value is not 1
      header.put("unit-length", RGB_BYTE_LIST_UNIT_LENGTH);
      
      list.setHeader(header);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void encode(Content<?> input, Content<?> output) {
    RGBByteList list = (RGBByteList) input;
    BinaryString string = (BinaryString) output;

    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < list.getList().size(); i++) {
      builder.append(list.get(i));
    }
    string.setString(builder.toString());
    
    
    if (string.getHeader() == null || string.getHeader().isEmpty()) {
      Map<String, Object> header = new HashMap<>();
      Map<String, Object> map = list.getHeader();

      for (Map.Entry<String, Object> entry : map.entrySet()) {
        header.put(entry.getKey(), entry.getValue());
      }

      // Binary string needs this key since it's value is not 1
      header.put("unit-length", BINARY_STRING_UNIT_LENGTH);
      string.setHeader(header);
    }
  }
}
