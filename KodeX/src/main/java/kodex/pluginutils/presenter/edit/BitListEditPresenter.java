package kodex.pluginutils.presenter.edit;

import java.util.function.UnaryOperator;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import kodex.plugininterface.ChainLinkEditPresenter;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.pluginutils.model.content.BitList;

/**
 * This class manages the edit view and is responsible for editing a RGB matrix.
 * 
 * @author Raimon Gramlich
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 */
public class BitListEditPresenter extends ChainLinkEditPresenter {
  
  private BitList content;

  private TextField bitField;
  
  /** The text formatter which only allows binary input. */
  private TextFormatter<String> bitFormatter;
  
  private AnchorPane view;
  
  /**
   * Instantiates a new RGB matrix edit presenter.
   *
   * @param chainLinkPresenter the corresponding chain link presenter
   */
  public BitListEditPresenter(ChainLinkPresenter chainLinkPresenter) {
    super(chainLinkPresenter);
    
    // only allows 0 to 255 as input
    UnaryOperator<TextFormatter.Change> filter = change -> {
      if (change.getControlNewText().matches("[0-1]")) {
        return change;
      } else if (change.getControlNewText().isEmpty()) {
        change.setText("0");
        return change;
      } else {
        return null;
      }
    };
    
    bitFormatter = new TextFormatter<>(filter);
    
    
    bitField = new TextField();
    bitField.setTextFormatter(bitFormatter);
    
    Label bitLabel = new Label("Bit: ");

    
    HBox bitBox = new HBox(bitLabel, bitField);
   
    view = new AnchorPane((new VBox(bitBox)));
  }

  @Override
  public AnchorPane getView() {
    updateMarkedElement();    
    
    return view;
  }

  @Override
  public void handleSubmit() {
    content.getList().set(markID, Integer.parseInt(bitField.getText()));
    
    chainLinkPresenter.updateChain();
  }

  @Override
  protected void updateMarkedElement() {   
    content = (BitList) chainLinkPresenter.getContent();
    
    bitField.setText(String.valueOf(content.get(markID)));
  }
}
