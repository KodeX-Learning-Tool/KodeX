package kodex.pluginutils.presenter.chainlink;


import javafx.scene.layout.AnchorPane;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.ColorImage;
import kodex.pluginutils.presenter.edit.ColorImageEditPresenter;
import kodex.pluginutils.presenter.header.ColorImageHeaderPresenter;

/**
 * 
 */
public class ColorImageChainLinkPresenter extends ChainLinkPresenter {



	public ColorImageChainLinkPresenter(ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
		super(previous, previousStep, nextStep);
		chainLinkEditPresenter = new ColorImageEditPresenter(this);
		chainLinkHeaderPresenter = new ColorImageHeaderPresenter(this.getContent());
		content = new ColorImage();
	}



	@Override
	protected void mark(int id) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public AnchorPane getView() {
		// TODO Auto-generated method stub
		return null;
	}

}