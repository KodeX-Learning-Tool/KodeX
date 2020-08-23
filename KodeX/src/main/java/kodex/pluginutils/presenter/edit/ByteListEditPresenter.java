package kodex.pluginutils.presenter.edit;

import java.util.function.UnaryOperator;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import kodex.exceptions.AlertWindowException;
import kodex.exceptions.InvalidInputException;
import kodex.model.I18N;
import kodex.plugininterface.ChainLinkEditPresenter;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.pluginutils.model.content.ByteList;

/**
 * This class manages the edit view and is responsible for editing a RGB byte list.
 * 
 * @author Raimon Gramlich
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 */
public class ByteListEditPresenter extends ChainLinkEditPresenter {
  
  private ByteList content;
  
  private TextField byteField = new TextField();
  
  /** The text formatter which only allows binary input. */
  private TextFormatter<String> byteFormatter;
  
  /** The length of one unit used to determine how many list elements belong together. */
  private int unitLength = 8;
  
  private AnchorPane view;

  /**
   * Instantiates a new RGB byte list edit presenter.
   *
   * @param chainLinkPresenter the corresponding chain link presenter
   */
  public ByteListEditPresenter(ChainLinkPresenter chainLinkPresenter) {
    super(chainLinkPresenter);
    
    // only allows 0 or 1 as input
    UnaryOperator<TextFormatter.Change> filter = change -> {
      if (isValid(change.getControlNewText())) {
        return change;
      } else {
        return null;
      }
    };
    
    
    byteFormatter = new TextFormatter<>(filter);
    byteField.setTextFormatter(byteFormatter);
    
    Label byteLabel = new Label("Byte: ");
    
    HBox byteBox = new HBox(byteLabel, byteField);
   
    view = new AnchorPane((new VBox(byteBox)));
  }

  private boolean isValid(String binarySeq) {
    for (int i = 0; i < binarySeq.length(); i++) {
      if (binarySeq.charAt(i) != '0' && binarySeq.charAt(i) != '1') {
        return false;
      }
    }
    return true;
  }

  @Override
  public AnchorPane getView() {
    updateMarkedElement();
    
    return view;
  }

  @Override
  public void handleSubmit() throws AlertWindowException {
    String input = byteField.getText();
    // strip leading zeros to verify whether the number is in range
    if (input == null || input.equals("") || input.length() != unitLength) {

      throw new InvalidInputException(
          AlertType.ERROR,
          I18N.get("alert.title.error"),
          I18N.get("alert.input.invalid"),
          "A byte is a sequence of 8 numbers in the dual system (0 or 1)");
    }

    content.getList().set(markID * unitLength, byteField.getText());
    
    chainLinkPresenter.updateChain();
  }

  @Override
  protected void updateMarkedElement() {
    
    content = (ByteList) chainLinkPresenter.getContent();

    byteField.setText(String.valueOf(content.get(markID)));
  }
}
