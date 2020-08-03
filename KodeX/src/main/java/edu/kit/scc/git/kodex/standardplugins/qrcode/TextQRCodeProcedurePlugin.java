package edu.kit.scc.git.kodex.standardplugins.qrcode;

import edu.kit.scc.git.kodex.plugininterface.ChainLinkPresenter;
import edu.kit.scc.git.kodex.plugininterface.Content;
import edu.kit.scc.git.kodex.plugininterface.ImportPresenter;
import edu.kit.scc.git.kodex.plugininterface.ProcedureInformation;
import edu.kit.scc.git.kodex.plugininterface.ProcedurePlugin;
import edu.kit.scc.git.kodex.pluginutils.model.steps.CharacterStringToQRCode;
import edu.kit.scc.git.kodex.pluginutils.presenter.chainlink.CharacterStringChainLinkPresenter;
import edu.kit.scc.git.kodex.pluginutils.presenter.chainlink.QRCodeChainLinkPresenter;
import edu.kit.scc.git.kodex.standardplugins.qrcode.presenter.TextQRCodeImportPresenter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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

  }
  
  /**
   * Initialize the procedure plugin.
   */
  private void initialize() {
    this.chainLinks = new ChainLinkPresenter[2];
    
    CharacterStringToQRCode charStringToQRCode = new CharacterStringToQRCode();
    
    chainLinks[0] = new CharacterStringChainLinkPresenter(null, null, charStringToQRCode);
    chainLinks[1] = new QRCodeChainLinkPresenter(chainLinks[0], charStringToQRCode, null);
    
    chainLinks[0].setNext(chainLinks[1]);
  }
  
  @Override
  public ImportPresenter createImportPresenter() {
    initialize();
    return new TextQRCodeImportPresenter(this);
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
    return new SimpleStringProperty("Das Kodierungs-Verfahren zum Generieren eines QRCodes");
  }

  @Override
  public StringProperty pluginNameProperty() {
    return new SimpleStringProperty("QR-Code");
  }
}
