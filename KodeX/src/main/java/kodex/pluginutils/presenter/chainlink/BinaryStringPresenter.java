package kodex.pluginutils.presenter.chainlink;

import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.BinaryString;
import kodex.pluginutils.presenter.edit.BinaryStringEditPresenter;
import kodex.pluginutils.presenter.header.BinaryStringHeaderPresenter;

/**
 * The Class BinaryStringChainLinkPresenter.
 *
 * @author Raimon Gramlich
 */
public class BinaryStringPresenter extends ChainLinkPresenter {

  /** The chain link name. */
  private static final String CHAIN_LINK_NAME = "Binärkette";

  public BinaryStringPresenter(
      ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
    super(previous, previousStep, nextStep);
    chainLinkEditPresenter = new BinaryStringEditPresenter(this);
    content = new BinaryString();
    chainLinkHeaderPresenter = new BinaryStringHeaderPresenter(this.getContent());
  }

  @Override
  public AnchorPane getView() {    
    TextArea binaryTextArea = new TextArea(((BinaryString) getContent()).getString());
    
    binaryTextArea.setEditable(false);
    binaryTextArea.setWrapText(true);

    AnchorPane.setTopAnchor(binaryTextArea, 0d);
    AnchorPane.setRightAnchor(binaryTextArea, 0d);
    AnchorPane.setBottomAnchor(binaryTextArea, 0d);
    AnchorPane.setLeftAnchor(binaryTextArea, 0d);
    
    AnchorPane chainLinkPane = new AnchorPane();
    
    chainLinkPane.getChildren().add(binaryTextArea);
    
    return chainLinkPane;
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
