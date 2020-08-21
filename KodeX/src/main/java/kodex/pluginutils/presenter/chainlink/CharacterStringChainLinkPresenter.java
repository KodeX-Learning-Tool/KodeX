package kodex.pluginutils.presenter.chainlink;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser.ExtensionFilter;
import kodex.model.I18N;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.CharacterString;
import kodex.pluginutils.presenter.edit.CharacterStringEditPresenter;

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
  
  @Override
  public List<ExtensionFilter> getExtensionsFilter() {
    List<ExtensionFilter> extensionFilters = new ArrayList<>();
    extensionFilters.add(new ExtensionFilter(I18N.get("files.text"), "*.txt"));
    return extensionFilters;
  }
}
