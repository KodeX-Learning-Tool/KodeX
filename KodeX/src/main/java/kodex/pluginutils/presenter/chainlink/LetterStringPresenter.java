package kodex.pluginutils.presenter.chainlink;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.LetterString;

public class LetterStringPresenter extends ChainLinkPresenter {
	
	/** The chain link name. */
	private static final String CHAIN_LINK_NAME = "Buchstabenkette";
	
    public LetterStringPresenter(ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
        super(previous, previousStep, nextStep);
        this.content = new LetterString();
    }

    @Override
    protected void mark(int id) {
        // TODO Auto-generated method stub

    }

    @Override
    public AnchorPane getView() {
        
        AnchorPane ap = new AnchorPane();
        Label displaytext = new Label();
        
        displaytext.setText(((LetterString)content).getLetterString());
        ap.getChildren().add(displaytext);
        return ap;
    }

	@Override
	public String getName() {
		return CHAIN_LINK_NAME;
	}
}
