package kodex.pluginutils.model.content;

import java.io.File;
import java.util.LinkedList;

/**
 * This class holds data in LinkedList format. An BitList consists of Intergers.
 * Extending AbstractList, it adds validation and exporting capabilities to
 * Javas List.
 */
public class BitList extends AbstractList<Integer> {

  /** Creates a new RGBList. */
  public BitList() {
    super.list = new LinkedList<Integer>();
  }

  @Override
  protected File toFile() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isValid(Integer input) {
    return (input == 0 || input == 1);
  }
}
