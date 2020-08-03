package edu.kit.scc.git.kodex.pluginutils.model.steps;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import edu.kit.scc.git.kodex.plugininterface.ChainStep;
import edu.kit.scc.git.kodex.plugininterface.Content;
import edu.kit.scc.git.kodex.pluginutils.model.content.RGBByteList;
import edu.kit.scc.git.kodex.pluginutils.model.content.RGBList;
import javafx.scene.paint.Color;

/**
 * This class represents the bidirectional step between RGBList and RGBByteList.
 * It contains the functionality to decode and encode the content between
 * these explicitly defined levels.
 * 
 * @author Yannick Neubert
 * @author Raimon Gramlich
 * 
 * @version 1.0
 */

public class RGBListToRGBByteList implements ChainStep {
  
  /** The Constant defines how many elements belong together in a RGB byte list. */
  private static final int RGB_BYTE_LIST_UNIT_LENGTH = 3;

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
    
    outputlist.getList().clear();

    for (int i = 0; i < inputlist.size() / RGB_BYTE_LIST_UNIT_LENGTH; i++) {
      int red = Integer.parseInt(inputlist.get(i * RGB_BYTE_LIST_UNIT_LENGTH), 2);
      int green = Integer.parseInt(inputlist.get(i * RGB_BYTE_LIST_UNIT_LENGTH + 1), 2);
      int blue = Integer.parseInt(inputlist.get(i * RGB_BYTE_LIST_UNIT_LENGTH + 2), 2);
      Color color = Color.rgb(red, green, blue);
      outputlist.add(color);
    }
    
    
    if (outputlist.getHeader() == null || outputlist.getHeader().isEmpty()) {  
      Map<String, Object> header = new HashMap<>();
      Map<String, Object> map =  inputlist.getHeader();
      
      for (Map.Entry<String, Object> entry: map.entrySet()) {
        header.put(entry.getKey(), entry.getValue());
      }
      
      // not needed for RGBList
      header.remove("unit-length");
      
      outputlist.setHeader(header);
    }
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
      rgblist.add(percentToByteString(color.getGreen()));
      rgblist.add(percentToByteString(color.getBlue()));
    }
    outputlist.setList(rgblist);
    
    if (outputlist.getHeader() == null || outputlist.getHeader().isEmpty()) {
      Map<String, Object> header = new HashMap<>();
      Map<String, Object> map = inputlist.getHeader();

      for (Map.Entry<String, Object> entry : map.entrySet()) {
        header.put(entry.getKey(), entry.getValue());
      }

      // RGB byte list needs this key since it's value is not 1
      header.put("unit-length", RGB_BYTE_LIST_UNIT_LENGTH);
      
      outputlist.setHeader(header);
    }
  }
}
