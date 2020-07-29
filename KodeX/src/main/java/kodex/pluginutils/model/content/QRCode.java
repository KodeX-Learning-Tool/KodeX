package kodex.pluginutils.model.content;

import java.io.File;

import javafx.scene.image.WritableImage;
import kodex.plugininterface.Content;

/** */
public class QRCode extends Content<WritableImage> {

  @Override
  public boolean isValid(WritableImage input) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  protected File toFile() {
    // TODO Auto-generated method stub
    return null;
  }

}
