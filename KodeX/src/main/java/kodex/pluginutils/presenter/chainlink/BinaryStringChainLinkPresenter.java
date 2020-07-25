package kodex.pluginutils.presenter.chainlink;

import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
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
public class BinaryStringChainLinkPresenter extends ChainLinkPresenter {

	/** The chain link name. */
	private static final String CHAIN_LINK_NAME = "Binärkette";

  public BinaryStringChainLinkPresenter(
      ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
    super(previous, previousStep, nextStep);
    chainLinkEditPresenter = new BinaryStringEditPresenter(this);
    chainLinkHeaderPresenter = new BinaryStringHeaderPresenter(this.getContent());
  }

  @Override
  public AnchorPane getView() {
    AnchorPane chainLinkPane = new AnchorPane();

    Text binaryText = new Text(((BinaryString) getContent()).getString());

    binaryText.wrappingWidthProperty().bind(chainLinkPane.widthProperty());

    chainLinkPane.getChildren().add(binaryText);

    AnchorPane.setTopAnchor(binaryText, 0d);
    AnchorPane.setRightAnchor(binaryText, 0d);
    AnchorPane.setBottomAnchor(binaryText, 0d);
    AnchorPane.setLeftAnchor(binaryText, 0d);

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
