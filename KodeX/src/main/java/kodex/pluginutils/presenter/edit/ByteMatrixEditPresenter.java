package kodex.pluginutils.presenter.edit;

import java.util.function.UnaryOperator;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import kodex.exceptions.AlertWindowException;
import kodex.exceptions.InvalidInputException;
import kodex.model.I18N;
import kodex.plugininterface.ChainLinkEditPresenter;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.pluginutils.model.content.ByteMatrix;

/**
 * This class manages the edit view and is responsible for editing a RGB matrix.
 * 
 *  @author Raimon Gramlich
 *  @author Patrick Spiesberger
 *  
 *  @version 1.0
 */
public class ByteMatrixEditPresenter extends ChainLinkEditPresenter {
  
  private ByteMatrix content;
  
  private int selectedX;
  private int selectedY;
  
  private TextField valueField = new TextField();
  
  /** The text formatter which only allows binary input. */
  private TextFormatter<String> valueFormatter;
  
  private AnchorPane view;
  
  /**
   * Instantiates a new RGB matrix edit presenter.
   *
   * @param chainLinkPresenter the corresponding chain link presenter
   */
  public ByteMatrixEditPresenter(ChainLinkPresenter chainLinkPresenter) {
    super(chainLinkPresenter);
    
    // only allows 0 to 255 as input
    UnaryOperator<TextFormatter.Change> filter = change -> {
      if (change.getControlNewText().matches("\\b(1?[0-9]{1,2}|2[0-4][0-9]|25[0-5])\\b")) {
        return change;
      } else if (change.getControlNewText().isEmpty()) {
        change.setText("");
        return change;
      } else {
        return null;
      }
    };
    
    valueFormatter = new TextFormatter<>(filter);
    
    valueField.setTextFormatter(valueFormatter);
    
    Label valueLabel = new Label("Value: ");
    
    HBox valueBox = new HBox(valueLabel, valueField);
   
    view = new AnchorPane((new VBox(valueBox)));
  }

  @Override
  public AnchorPane getView() {
    updateMarkedElement();    
    
    return view;
  }

  @Override
  public void handleSubmit() throws AlertWindowException {
    String input = valueField.getText();
    if (input == null || input.equals("")) {

      throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"),
          I18N.get("alert.input.invalid"), "This string is empty");
    }
    content.set(selectedX, selectedY, Integer.parseInt(valueField.getText()));
    
    chainLinkPresenter.updateChain();
  }

  @Override
  protected void updateMarkedElement() {   
    content = (ByteMatrix) chainLinkPresenter.getContent();
    
    selectedX = markID % content.getWidth();
    selectedY = (markID / content.getWidth());

    int markedPixel = content.get(selectedX, selectedY);
    valueField.setText(String.valueOf(Math.round(markedPixel) * 255));
  }
}
