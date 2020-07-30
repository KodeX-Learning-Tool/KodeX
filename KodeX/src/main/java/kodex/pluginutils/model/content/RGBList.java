package kodex.pluginutils.model.content;

import java.io.File;
import java.util.LinkedList;
import javafx.scene.paint.Color;

/**
 * This class holds data in LinkedList format. An RGBList consists of colors. Extending
 * AbstractList, it adds validation and exporting capabilities to Javas List.
 */
public class RGBList extends AbstractList<Color> {

  /** Creates a new RGBList. */
  public RGBList() {
    super.list = new LinkedList<Color>();
  }

  @Override
  protected File toFile() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isValid(Object input) {
    // TODO Auto-generated method stub
    return false;
  }
}
