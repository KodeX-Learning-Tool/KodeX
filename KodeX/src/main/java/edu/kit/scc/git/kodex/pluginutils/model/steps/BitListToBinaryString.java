package edu.kit.scc.git.kodex.pluginutils.model.steps;

import java.util.HashMap;

import edu.kit.scc.git.kodex.plugininterface.ChainStep;
import edu.kit.scc.git.kodex.plugininterface.Content;
import edu.kit.scc.git.kodex.pluginutils.model.content.BinaryString;
import edu.kit.scc.git.kodex.pluginutils.model.content.BitList;

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

    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < leftlist.getList().size(); i++) {
      builder.append(leftlist.get(i));
    }
    rightstring.setString(builder.toString());

    HashMap<String, Object> map = new HashMap<>();
    map.put("length", rightstring.length());
    rightstring.setHeader(map);

    rightstring.setHeader(leftlist.getHeader());
  }
}
