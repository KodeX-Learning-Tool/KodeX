package kodex.pluginutils.model.content;

import java.io.File;
import java.util.LinkedList;

/**
 * This class holds data in LinkedList format. An List consists of Strings.
 * Extending AbstractList, it adds validation and exporting capabilities to
 * Javas List.
 */
public class ByteList extends AbstractList<String> {

  /** Creates a new RGBList. */
  public ByteList() {
    super.list = new LinkedList<String>();
  }

  @Override
  public boolean isValid(Object input) {    
    if (input == null) {
      return false;
    }
    String byteValue = (String) input;
    
    if (byteValue.length() != 8) {
      return false;
    }
    if (!byteValue.matches("[01]+")) {
      return false;
    }
    return true;
  }

  @Override
  public void export(File file) {
    // TODO Auto-generated method stub
    
  }
}
