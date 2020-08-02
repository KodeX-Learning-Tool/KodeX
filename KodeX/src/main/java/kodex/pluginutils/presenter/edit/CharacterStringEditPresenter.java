package kodex.pluginutils.presenter.edit;

import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import kodex.plugininterface.ChainLinkEditPresenter;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.pluginutils.model.content.CharacterString;

/**
 * This class manages the edit view and is responsible for editing a character string.
 * 
 * @author Yannick Neubert
 * @author Raimon Gramlich
 */
public class CharacterStringEditPresenter extends ChainLinkEditPresenter {
  
  /** The content. */
  private CharacterString content;
  
  /** The view managed by the edit view. */
  private AnchorPane view;
  
  /** The TextArea used to input a character string. */
  private TextArea characterStringArea;
  
  /**
   * Instantiates a new CharacterStringEditPresenter.
   *
   * @param chainLinkPresenter the chain link presenter
   */
  public CharacterStringEditPresenter(ChainLinkPresenter chainLinkPresenter) {
    super(chainLinkPresenter);
    
    characterStringArea = new TextArea();
    characterStringArea.setWrapText(true);
    view = new AnchorPane(characterStringArea);
  }

  @Override
  public AnchorPane getView() {
    updateMarkedElement();
    
    return view;
  }

  @Override
  public void handleSubmit() {
    content.setString(characterStringArea.getText());
    chainLinkPresenter.updateChain();
  }

  @Override
  protected void updateMarkedElement() {
    content = (CharacterString) chainLinkPresenter.getContent();
    characterStringArea.setText(content.getString());
  }
}
