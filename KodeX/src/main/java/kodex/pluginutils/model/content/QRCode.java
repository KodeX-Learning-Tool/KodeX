package kodex.pluginutils.model.content;

import java.io.File;

import com.google.zxing.common.BitMatrix;

import javafx.scene.image.WritableImage;
import kodex.plugininterface.Content;

/** 
 * This class holds a QRCode as BitMatrix. A QRCode can be set via a WritableImage.
 * 
 * @author Yannick Neubert
 */
public class QRCode extends Content<WritableImage> {
  
  /** This Content's data. */
  private BitMatrix matrix;
  
  public QRCode() {
    this.matrix = new BitMatrix(0);
  }
  
  public BitMatrix getData() {
    return matrix;
  }
  
  public void setData(BitMatrix data) {
    this.matrix = data;
  }
 
  public void setData(WritableImage data) {
   
  }
 
  public void setSize(int size) {
    this.matrix = new BitMatrix(size);
  }
  
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
