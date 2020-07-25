package kodex.standardplugins.qrcode;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.Content;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedureInformation;
import kodex.plugininterface.ProcedurePlugin;

/** */
public class TextQRCodeProcedurePlugin extends ProcedurePlugin {

  private ChainLinkPresenter[] chainLinks; // [2..*]

  /** Default constructor. */
  public TextQRCodeProcedurePlugin() {}

  @Override
  public ImportPresenter createImportPresenter() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ProcedureInformation createProcedureInformation() {
    return new TextQRCodeProcedureInformation();
  }

  @Override
  public ChainLinkPresenter getChainHead() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void initDecodeProcedure(Content content) {
    // TODO Auto-generated method stub

  }

  @Override
  public void initEncodeProcedure(Content content) {
    // TODO Auto-generated method stub

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
