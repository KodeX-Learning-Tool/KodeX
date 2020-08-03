package edu.kit.scc.git.kodex.pluginutils.presenter.edit;

import edu.kit.scc.git.kodex.model.I18N;
import edu.kit.scc.git.kodex.plugininterface.ChainLinkEditPresenter;
import edu.kit.scc.git.kodex.plugininterface.ChainLinkPresenter;
import edu.kit.scc.git.kodex.pluginutils.model.content.CharacterString;
import edu.kit.scc.git.kodex.presenter.PresenterManager;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

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
    String string = characterStringArea.getText();
    if (!string.isEmpty()) {
      content.setString(string);
      chainLinkPresenter.updateChain();
    } else {
      Alert alert = new Alert(AlertType.ERROR);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
      alert.setContentText("The string cannot be empty");
      PresenterManager.showAlertDialog(alert);
    }
  }

  @Override
  protected void updateMarkedElement() {
    content = (CharacterString) chainLinkPresenter.getContent();
    characterStringArea.setText(content.getString());
  }
}
