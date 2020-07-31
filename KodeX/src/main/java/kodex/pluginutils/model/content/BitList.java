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
  public boolean isValid(Object input) {
    if (input == null) {
      return false;
    }
    int bit = (Integer) input;
    return (bit == 0 || bit == 1);
  }


  @Override
  public void export(File file) {
    // TODO Auto-generated method stub
    
  }
}
