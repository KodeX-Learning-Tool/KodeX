package kodex.pluginutils.presenter.chainlink;

import javafx.scene.layout.AnchorPane;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;

/** */
public class CharacterStringPresenter extends ChainLinkPresenter {

  /** The chain link name. */
  private static final String CHAIN_LINK_NAME = "Zeichenkette";
	
  public CharacterStringPresenter(
      ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
    super(previous, previousStep, nextStep);
    // TODO Auto-generated constructor stub
  }

  @Override
  public AnchorPane getView() {
    // TODO Auto-generated method stub
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
