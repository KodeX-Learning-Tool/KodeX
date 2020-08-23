package kodex.pluginutils.presenter.edit;

import java.util.function.UnaryOperator;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import kodex.exceptions.AlertWindowException;
import kodex.plugininterface.ChainLinkEditPresenter;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.pluginutils.model.content.BinaryMatrix;

/**
 * This class manages the edit view and is responsible for editing a RGB matrix.
 * 
 * @author Raimon Gramlich
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 */
public class BWMatrixEditPresenter extends ChainLinkEditPresenter {
  
  private BinaryMatrix content;
  
  private int selectedX;
  private int selectedY;
  
  private TextField blackField;
  
  /** The text formatter which only allows binary input. */
  private TextFormatter<String> blackFormatter;
  
  private AnchorPane view;
  
  /**
   * Instantiates a new RGB matrix edit presenter.
   *
   * @param chainLinkPresenter the corresponding chain link presenter
   */
  public BWMatrixEditPresenter(ChainLinkPresenter chainLinkPresenter) {
    super(chainLinkPresenter);
    
    // only allows 0 to 255 as input
    UnaryOperator<TextFormatter.Change> filter = change -> {
      if (change.getControlNewText().toString().equals("0")
          || change.getControlNewText().toString().equals("1")) {
        return change;
      } else if (change.getControlNewText().isEmpty()) {
        change.setText("");
        return change;
      } else {
        return null;
      }
    };
    
    blackFormatter = new TextFormatter<>(filter);
    
    
    blackField = new TextField();
    blackField.setTextFormatter(blackFormatter);
    
    Label blackLabel = new Label("Weiß: ");

    
    HBox blackBox = new HBox(blackLabel, blackField);
   
    view = new AnchorPane((new VBox(blackBox)));
  }

  @Override
  public AnchorPane getView() {
    updateMarkedElement();    
    
    return view;
  }

  @Override
  public void handleSubmit() throws AlertWindowException {
    content.set(selectedX, selectedY, Integer.parseInt(blackField.getText()));
    chainLinkPresenter.updateChain();
  }

  @Override
  protected void updateMarkedElement() {   
    content = (BinaryMatrix) chainLinkPresenter.getContent();
    
    selectedX = markID % content.getWidth();
    selectedY = (markID / content.getWidth());

    int markedPixel = content.get(selectedX, selectedY);
    blackField.setText(String.valueOf(markedPixel));
  }
}
