package kodex.pluginutils.presenter.chainlink;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.CharacterString;

/**
 *  
 */
public class CharacterStringChainLinkPresenter extends ChainLinkPresenter {

  /** The chain link name. */
  private static final String CHAIN_LINK_NAME = "Zeichenkette";

  public CharacterStringChainLinkPresenter(
      ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
    super(previous, previousStep, nextStep);
    this.content = new CharacterString();
  }

  @Override
  public AnchorPane getView() {

    AnchorPane ap = new AnchorPane();
    Label displaytext = new Label();

    displaytext.setText(((CharacterString) content).getString());
    ap.getChildren().add(displaytext);
    return ap;
  }

  @Override
  public String getName() {
    return CHAIN_LINK_NAME;
  }

  @Override
  protected void mark(int id) {
    // TODO Auto-generated method stub

  }

  @Override
  public void updateView() {
    // TODO Auto-generated method stub
    
  }
}
