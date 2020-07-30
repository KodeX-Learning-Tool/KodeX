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
  protected File toFile() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isValid(String input) {
    if (input == null) {
      return false;
    }
    if (input.length() != 8) {
      return false;
    }
    if (!input.matches("[01]+")) {
      return false;
    }
    return true;
  }
}
