package kodex.standardplugins.qrcode;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.Content;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedureInformation;
import kodex.plugininterface.ProcedurePlugin;
import kodex.standardplugins.qrcode.presenter.QRCodeImportPresenter;

/**
 * This class is the entry point to the QRCode procedure plugin.
 * This class holds the ChainLinkPresenters that make up the QRCode coding chain
 * and controls the access to procedure information, the import presenter 
 * and the encode/decode methods.
 *
 * @author Yannick Neubert
 * @version 1.0
 */
public class TextQRCodeProcedurePlugin extends ProcedurePlugin {

  private ChainLinkPresenter[] chainLinks; // [2..*]

  /** Creates a new instance of the QRCodeProcedurePlugin. */
  public TextQRCodeProcedurePlugin() {
    QRCodeWriter writer = new QRCodeWriter();
    try {
      BitMatrix mtx = writer.encode("yo waddup dog", BarcodeFormat.QR_CODE, 100, 100);
      MatrixToImageWriter;
    } catch (WriterException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public ImportPresenter createImportPresenter() {
    return new QRCodeImportPresenter(this);
  }

  @Override
  public ProcedureInformation createProcedureInformation() {
    return new TextQRCodeProcedureInformation();
  }

  @Override
  public ChainLinkPresenter getChainHead() {
    return chainLinks[0];
  }
  
  @Override
  public ChainLinkPresenter getChainTail() {
    return chainLinks[chainLinks.length - 1];
  }

  @Override
  public void initDecodeProcedure(Content<?> content) {
    chainLinks[chainLinks.length - 1].setContent(content);
  }

  @Override
  public void initEncodeProcedure(Content<?> content) {
    chainLinks[0].setContent(content);
  }

  @Override
  public StringProperty pluginDescriptionProperty() {
    return new SimpleStringProperty("Verfahren");
  }

  @Override
  public StringProperty pluginNameProperty() {
    return new SimpleStringProperty("QR-Code");
  }
}
