package kodex.pluginutils.model.content;

import java.io.File;
import java.util.LinkedList;

/**
 * This class holds data in LinkedList format. An List consists of Bytes.
 * Extending AbstractList, it adds validation and exporting capabilities to
 * Javas List.
 */
public class ByteList extends AbstractList<Byte> {

  /** Creates a new RGBList. */
  public ByteList() {
    super.list = new LinkedList<Byte>();
  }

  @Override
  protected File toFile() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isValid(Byte input) {
    if (input == null) {
      return false;
    }
    return true;
  }
}
