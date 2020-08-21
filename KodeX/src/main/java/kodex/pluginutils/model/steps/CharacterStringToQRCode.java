package kodex.pluginutils.model.steps;

import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import kodex.exceptions.LoadingException;
import kodex.model.I18N;
import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.CharacterString;
import kodex.pluginutils.model.content.QRCode;
import kodex.presenter.PresenterManager;

/** 
 * This class represents the bidirectional step between CharacterString and QRCode.
 * It contains the functionality to decode and encode the content between 
 * these explicitly defined levels.
 * 
 * @author Yannick Neubert
 * @version 1.0
 */
public class CharacterStringToQRCode implements ChainStep {

  @Override
  public void decode(Content<?> input, Content<?> output) throws LoadingException {
    CharacterString string = (CharacterString) output;
    QRCode qrcode = (QRCode) input;
    
    MultiFormatReader reader = new MultiFormatReader();
    try {
      string.setString(reader.decode(qrcode.getBinaryBitmap()).getText());
    } catch (NotFoundException e) {
      
      throw new LoadingException(
          AlertType.ERROR,
          I18N.get("alert.title.error"),
          I18N.get("alert.input.invalid"),
          "Something went wrong decoding the QR-Code");
    }
    
    if (string.getHeader() == null || string.getHeader().isEmpty()) {  
      Map<String, Object> header = new HashMap<>();
      Map<String, Object> map =  qrcode.getHeader();
      
      for (Map.Entry<String, Object> entry: map.entrySet()) {
        header.put(entry.getKey(), entry.getValue());
      }

      string.setHeader(header);
    }

  }

  @Override
  public void encode(Content<?> input, Content<?> output) throws LoadingException {
    CharacterString string = (CharacterString) input;
    QRCode qrcode = (QRCode) output;
    
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    //no additional padding
    int size = 0;
    try {
      BitMatrix bitMatrix = qrCodeWriter.encode(
          string.getString(), BarcodeFormat.QR_CODE, size, size);
      qrcode.setBitMatrix(bitMatrix);
    } catch (WriterException e) {
      
      throw new LoadingException(
          AlertType.ERROR,
          I18N.get("alert.title.error"),
          I18N.get("alert.input.invalid"),
          "Something went wrong encoding the textfile");
    }
    
    if (qrcode.getHeader() == null || qrcode.getHeader().isEmpty()) {  
      Map<String, Object> header = new HashMap<>();
      Map<String, Object> map =  string.getHeader();
      
      for (Map.Entry<String, Object> entry: map.entrySet()) {
        header.put(entry.getKey(), entry.getValue());
      }

      qrcode.setHeader(header);
    }
    
  }
}
