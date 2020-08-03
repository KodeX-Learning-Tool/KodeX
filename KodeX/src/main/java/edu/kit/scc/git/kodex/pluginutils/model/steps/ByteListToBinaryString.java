package edu.kit.scc.git.kodex.pluginutils.model.steps;

import java.util.HashMap;

import edu.kit.scc.git.kodex.plugininterface.ChainStep;
import edu.kit.scc.git.kodex.plugininterface.Content;
import edu.kit.scc.git.kodex.pluginutils.model.content.BinaryString;
import edu.kit.scc.git.kodex.pluginutils.model.content.ByteList;

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

    String input = rightstring.getString();
    for (int i = 0; i < input.length() / 8; i++) {
      sequence = input.substring(i * 8, i * 8 + 8);
      leftlist.add(sequence);
    }

    leftlist.setHeader(rightstring.getHeader());
  }

  @SuppressWarnings("unchecked")
  @Override
  public void encode(Content<?> left, Content<?> right) {
    ByteList leftlist = (ByteList) left;
    BinaryString rightstring = (BinaryString) right;
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < leftlist.getList().size(); i++) {
      result.append(leftlist.get(i));
    }

    rightstring.setString(result.toString());

    HashMap<String, Object> map = new HashMap<>();
    map.put("length", rightstring.length());
    rightstring.setHeader(map);

    rightstring.setHeader(leftlist.getHeader());
  }
}
