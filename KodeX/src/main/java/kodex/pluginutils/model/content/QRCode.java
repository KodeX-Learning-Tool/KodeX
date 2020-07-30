package kodex.pluginutils.model.content;

import java.io.File;
import java.io.IOException;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javafx.scene.image.WritableImage;
import kodex.plugininterface.Content;

/** 
 * This class holds a QRCode as BitMatrix. A QRCode can be set via a WritableImage.
 * 
 * @author Yannick Neubert
 * @version 1.0
 */
public class QRCode extends Content<WritableImage> {
  
  /** This Content's data. */
  private BitMatrix matrix;
  
  public QRCode() {
    this.matrix = new BitMatrix(1);
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
  public void export(File file) {
    try {
      MatrixToImageWriter.writeToPath(matrix, "PNG", file.toPath());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
