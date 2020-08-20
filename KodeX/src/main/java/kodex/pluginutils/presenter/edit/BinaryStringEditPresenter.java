package kodex.pluginutils.presenter.edit;

import java.util.function.UnaryOperator;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import kodex.exceptions.InvalidInputException;
import kodex.model.I18N;
import kodex.plugininterface.ChainLinkEditPresenter;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.pluginutils.model.content.BinaryString;

/**
 * This class manages the edit view and is responsible for editing a binary string.
 * 
 *  @author Raimon Gramlich
 */
public class BinaryStringEditPresenter extends ChainLinkEditPresenter {
  
  /** The content. */
  private BinaryString content;
  
  /** The view managed by the edit view. */
  private AnchorPane view;
  
  /** The text formatter which only allows binary input. */
  private TextFormatter<String> binaryFormatter;
  
  /** The TextArea used to input a binary string. */
  private TextArea binaryStringArea;
  
  /** The length of one unit used to determine where to split the binary string. */
  private int unitLength;
  
  /**
   * Instantiates a new binary string edit presenter.
   *
   * @param chainLinkPresenter the chain link presenter
   */
  public BinaryStringEditPresenter(ChainLinkPresenter chainLinkPresenter) {
    super(chainLinkPresenter);
    
    // only allows 0 or 1 as input
    UnaryOperator<TextFormatter.Change> filter = change -> {
      if (change.getControlNewText().matches("[0-1]*")) {
        return change;
      } else {
        return null;
      }
    };
    
    binaryStringArea = new TextArea();
    binaryStringArea.setWrapText(true);
    binaryFormatter = new TextFormatter<>(filter);
    binaryStringArea.setTextFormatter(binaryFormatter);
    
    view = new AnchorPane(binaryStringArea);

    content = (BinaryString) chainLinkPresenter.getContent();
  }

  @Override
  public AnchorPane getView() {
    unitLength = (int) content.getHeader().get("unit-length");
    
    updateMarkedElement();
    
    return view;
  }

  @Override
  public void handleSubmit() throws InvalidInputException {
    String input = binaryStringArea.getText();
    // strip leading zeros to verify whether the number is in range
    input = input.replaceFirst("^0+(?!$)", "");
    
    if (input.length() < unitLength) {
      // add leading zeros
      while (input.length() < unitLength) {
        input = "0".concat(input);
      }
    } else {
      throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"),
          I18N.get("alert.input.invalid"), "This binary string has a max length of " + unitLength);
    }
    
    // replace and concatenate each part 

    String binaryString = content.getString();
    String prefix = binaryString.substring(0, unitLength * markID);
    String suffix = binaryString.substring((markID + 1) * 24);
    
    binaryString = prefix.concat(input.concat(suffix));
    
    content.setString(binaryString);
    
    chainLinkPresenter.updateChain();
  }

  @Override
  protected void updateMarkedElement() {
    content = (BinaryString) chainLinkPresenter.getContent();
    binaryStringArea.setText(content.getString().substring(unitLength * markID,
        (markID + 1) * unitLength));
  }
}
