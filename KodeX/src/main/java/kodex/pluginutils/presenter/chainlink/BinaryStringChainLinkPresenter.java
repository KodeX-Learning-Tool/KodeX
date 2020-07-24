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
public class BinaryStringChainLinkPresenter extends ChainLinkPresenter {


	public BinaryStringChainLinkPresenter(ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
		super(previous, previousStep, nextStep);
		chainLinkEditPresenter = new BinaryStringEditPresenter(this);
		// chainLinkHeaderPresenter = new BinaryStringHeaderPresenter(this.getContent());
		content = new BinaryString();
	}



	@Override
	protected void mark(int id) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public AnchorPane getView() {
		AnchorPane chainLinkPane = new AnchorPane();
		
		TextArea binaryTextArea = new TextArea(((BinaryString) getContent()).getString());
		
		binaryTextArea.setEditable(false);
		binaryTextArea.setWrapText(true);

		AnchorPane.setTopAnchor(binaryTextArea, 0d);
		AnchorPane.setRightAnchor(binaryTextArea, 0d);
		AnchorPane.setBottomAnchor(binaryTextArea, 0d);
		AnchorPane.setLeftAnchor(binaryTextArea, 0d);
		
		chainLinkPane.getChildren().add(binaryTextArea);
		
		return chainLinkPane;
	}

}