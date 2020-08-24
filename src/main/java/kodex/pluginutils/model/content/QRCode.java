package kodex.pluginutils.model.content;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.NotFoundException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import kodex.plugininterface.Content;

/** 
 * This class holds a QRCode. A QRCode can be set via a File.
 * This class holds to types of data. A BitMatrix is generated when encoding a String.
 * A BinaryBitmap is generated when decoding a QRCode.
 * There appears to be no practicable way of converting between these to since one would need to
 * encode and then decode (or vice versa) the types first.
 * 
 * @author Yannick Neubert
 * @version 1.0
 */
public class QRCode extends Content<File> {
  
  /** This Content's data. Use this when encoding a String*/
  private BitMatrix matrix;
  
  /** This Content's data. Use this when decoding an image*/
  private BinaryBitmap bitmap;
  
  /** Instantiates a new QRCode. */
  public QRCode() {
    this.matrix = new BitMatrix(1);
  }
  
  /** 
   * Returns this Content's data. Use this for encoding.
   * 
   * @return BitMatrix this Content's data 
   */
  public BitMatrix getBitMatrix() {
    return matrix;
  }
  
  /** 
   * Returns this Content's data. Use this for decoding.
   * 
   * @return BinaryBitmap this Content's data 
   */
  public BinaryBitmap getBinaryBitmap() {
    return bitmap;
  }
  
  /**
   * Sets this Content's data. Use this for encoding.
   * 
   * @param data The data to be used
   */
  public void setBitMatrix(BitMatrix data) {
    this.matrix = data;
  }
  
  /**
   * Sets this Content's data. Use this for decoding.
   * 
   * @param data The data to be used
   */
  public void setBinaryBitmap(BinaryBitmap data) {
    this.bitmap = data;
  }
 
  /**
   * Sets this Content's data. Use this for decoding.
   * 
   * @param data The file containing the QRCode
   */
  public void setBinaryBitmap(File data) {
    decodeFile(data);
  }
  
  @Override
  public boolean isValid(File input) {
    return decodeFile(input);
  }

  private boolean decodeFile(File input) {
    BufferedImageLuminanceSource source = null;
    try {
      source = new BufferedImageLuminanceSource(ImageIO.read(input));
    } catch (IOException | NullPointerException e1) {
      return false;
    } 
    bitmap = new BinaryBitmap(new HybridBinarizer(source));
    
    try {
      matrix = bitmap.getBlackMatrix();
    } catch (NotFoundException e) {
      return false;
    }
    
    return true;
  }
  
  @Override
  public void export(File file) {
    try {
      MatrixToImageWriter.writeToPath(matrix, "PNG", file.toPath());
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }

}
