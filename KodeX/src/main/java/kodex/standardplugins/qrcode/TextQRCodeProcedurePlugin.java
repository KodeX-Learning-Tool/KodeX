package kodex.standardplugins.qrcode;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedureInformation;
import kodex.plugininterface.ProcedurePlugin;
import kodex.pluginutils.model.steps.CharacterStringToQRCode;
import kodex.pluginutils.presenter.chainlink.CharacterStringChainLinkPresenter;
import kodex.pluginutils.presenter.chainlink.QRCodeChainLinkPresenter;
import kodex.standardplugins.qrcode.presenter.TextQRCodeImportPresenter;

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

  /** Creates a new instance of the QRCodeProcedurePlugin. */
  public TextQRCodeProcedurePlugin() {

  }
  
  /**
   * Initialize the procedure plugin.
   */
  public void initializeProcedure() {
    this.chainLinks = new ChainLinkPresenter[2];
    
    CharacterStringToQRCode charStringToQRCode = new CharacterStringToQRCode();
    
    chainLinks[0] = new CharacterStringChainLinkPresenter(null, null, charStringToQRCode);
    chainLinks[1] = new QRCodeChainLinkPresenter(chainLinks[0], charStringToQRCode, null);
    
    chainLinks[0].setNext(chainLinks[1]);
  }
  
  @Override
  public ImportPresenter createImportPresenter() {
    return new TextQRCodeImportPresenter(this);
  }

  @Override
  public ProcedureInformation createProcedureInformation() {
    return new TextQRCodeProcedureInformation();
  }

  @Override
  public StringProperty pluginDescriptionProperty() {
    return new SimpleStringProperty("Das Kodierungs-Verfahren zum Generieren eines QRCodes.");
  }

  @Override
  public StringProperty pluginNameProperty() {
    return new SimpleStringProperty("QR-Code");
  }
}
