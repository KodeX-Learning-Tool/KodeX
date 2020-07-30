package kodex.pluginutils.presenter.chainlink;

import javafx.scene.layout.AnchorPane;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.QRCode;

public class QRCodeChainLinkPresenter extends ChainLinkPresenter {
  /** The chain link name. */
  private static final String CHAIN_LINK_NAME = "QRCode";

  public QRCodeChainLinkPresenter(
      ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
    super(previous, previousStep, nextStep);
    this.content = new QRCode();
  }

  @Override
  public AnchorPane getView() {
    //TODO
    return null;
  }

  @Override
  public String getName() {
    return CHAIN_LINK_NAME;
  }

  @Override
  protected void mark(int id) {
    // TODO Auto-generated method stub

  }
}
