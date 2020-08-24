package kodex.pluginutils.presenter.edit;

import java.util.function.UnaryOperator;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import kodex.exceptions.AlertWindowException;
import kodex.plugininterface.ChainLinkEditPresenter;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.pluginutils.model.content.LetterString;

public class LetterStringEditPresenter extends ChainLinkEditPresenter {

  /** The content. */
  private LetterString content;
  
  /** The text formatter which only allows letter input. */
  private TextFormatter<String> letterFormatter;
  
  /** The TextArea used to input a letter string. */
  private TextArea letterStringArea;
  
  /** The view managed by the edit view. */
  private AnchorPane view;

  /**
   * Instantiates a new letter string edit presenter.
   *
   * @param chainLinkPresenter the chain link presenter
   */
  public LetterStringEditPresenter(ChainLinkPresenter chainLinkPresenter) {
    super(chainLinkPresenter);

    // only allows letters
    UnaryOperator<TextFormatter.Change> filter =
        change -> {
          if (change.getControlNewText().chars().allMatch(Character::isLetter)) {
            return change;
          } else {
            return null;
          }
        };

    letterStringArea = new TextArea();
    letterStringArea.setWrapText(true);
    letterFormatter = new TextFormatter<>(filter);
    letterStringArea.setTextFormatter(letterFormatter);

    view = new AnchorPane(letterStringArea);
    
    content = (LetterString) chainLinkPresenter.getContent();
  }

  @Override
  public AnchorPane getView() {

    updateMarkedElement();

    return view;
  }

  @Override
  public void handleSubmit() throws AlertWindowException {
    content.setString(letterStringArea.getText());
    
    chainLinkPresenter.updateChain();
  }

  @Override
  protected void updateMarkedElement() {
    content = (LetterString) chainLinkPresenter.getContent();
    
    letterStringArea.setText(content.getString());
  }
}
