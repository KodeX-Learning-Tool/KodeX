package edu.kit.scc.git.kodex.pluginutils.presenter.chainlink;

import edu.kit.scc.git.kodex.plugininterface.ChainLinkPresenter;
import edu.kit.scc.git.kodex.plugininterface.ChainStep;
import edu.kit.scc.git.kodex.pluginutils.model.content.CharacterString;
import edu.kit.scc.git.kodex.pluginutils.presenter.edit.CharacterStringEditPresenter;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * This class is the ChainLinkPresenter for a CharacterString. It's main purpose is to 
 * generate the view for a CharacterString.
 * 
 * @author Yannick Neubert 
 */
public class CharacterStringChainLinkPresenter extends ChainLinkPresenter {

  /** The chain link name. */
  private static final String CHAIN_LINK_NAME = "Zeichenkette";
  
  private Label displayText = new Label();

  /**
   * Instantiates a new CharacterStringChainLink presenter.
   *
   * @param previous the previous ChainLinkPresenter
   * @param previousStep the previous step
   * @param nextStep the next step
   */
  public CharacterStringChainLinkPresenter(
      ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
    super(previous, previousStep, nextStep);
    this.content = new CharacterString();
    this.chainLinkEditPresenter = new CharacterStringEditPresenter(this);
  }

  @Override
  public AnchorPane getView() {
    updateView();
    
    AnchorPane ap = new AnchorPane();
    ap.getChildren().add(displayText);
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
    displayText.setText(((CharacterString) content).getString());
  }
}
