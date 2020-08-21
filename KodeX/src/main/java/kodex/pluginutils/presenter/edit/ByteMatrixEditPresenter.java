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
  
  private TextField valueField;
  
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
      if (change.getControlNewText().matches("[0-255]")) {
        return change;
      } else if (change.getControlNewText().isEmpty()) {
        change.setText("0");
        return change;
      } else {
        return null;
      }
    };
    
    valueFormatter = new TextFormatter<>(filter);
    
    
    valueField = new TextField();
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
  public void handleSubmit() {
    content.set(selectedX, selectedY, Integer.parseInt(valueField.getText()) / 255);
    
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
