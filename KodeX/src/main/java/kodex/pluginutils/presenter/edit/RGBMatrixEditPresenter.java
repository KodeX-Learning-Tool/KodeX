package kodex.pluginutils.presenter.edit;

import java.util.function.UnaryOperator;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import kodex.exceptions.AlertWindowException;
import kodex.exceptions.InvalidInputException;
import kodex.model.I18N;
import kodex.plugininterface.ChainLinkEditPresenter;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.pluginutils.model.content.RGBMatrix;

/**
 * This class manages the edit view and is responsible for editing a RGB matrix.
 * 
 *  @author Raimon Gramlich
 */
public class RGBMatrixEditPresenter extends ChainLinkEditPresenter {
  
  private RGBMatrix content;
  
  private int selectedX;
  private int selectedY;
  
  private TextField redField;
  private TextField greenField;
  private TextField blueField;
  
  /** The text formatter which only allows binary input. */
  private TextFormatter<String> redFormatter;
  
  /** The text formatter which only allows binary input. */
  private TextFormatter<String> greenFormatter;
  
  /** The text formatter which only allows binary input. */
  private TextFormatter<String> blueFormatter;
  
  private AnchorPane view;
  
  /**
   * Instantiates a new RGB matrix edit presenter.
   *
   * @param chainLinkPresenter the corresponding chain link presenter
   */
  public RGBMatrixEditPresenter(ChainLinkPresenter chainLinkPresenter) {
    super(chainLinkPresenter);
    
    // only allows numbers as input
    UnaryOperator<TextFormatter.Change> filter = change -> {
      if (change.getControlNewText().matches("\\d*")) {
        return change;
      } else {
        return null;
      }
    };
    
    redFormatter = new TextFormatter<>(filter);
    greenFormatter = new TextFormatter<>(filter);
    blueFormatter = new TextFormatter<>(filter);
    
    
    redField = new TextField();
    redField.setTextFormatter(redFormatter);
    
    greenField = new TextField();
    greenField.setTextFormatter(greenFormatter);
    
    blueField = new TextField();
    blueField.setTextFormatter(blueFormatter);
    
    Label redLabel = new Label("R: ");
    Label greenLabel = new Label("G: ");
    Label blueLabel = new Label("B: ");
    
    HBox redBox = new HBox(redLabel, redField);
    HBox greenBox = new HBox(greenLabel, greenField);
    HBox blueBox = new HBox(blueLabel, blueField);
   
    view = new AnchorPane((new VBox(redBox, greenBox, blueBox)));
  }

  @Override
  public AnchorPane getView() {
    updateMarkedElement();    
    
    return view;
  }

  @Override
  public void handleSubmit() throws AlertWindowException {
    
    if (validateColorValue(redField) && validateColorValue(greenField) 
        && validateColorValue(blueField)) {
      content.set(selectedX, selectedY, new Color(
          Double.valueOf(redField.getText()) / 255d,
          Double.valueOf(greenField.getText()) / 255d,
          Double.valueOf(blueField.getText()) / 255d, 1d));
    } else {    
      throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"),
          I18N.get("alert.input.invalid"), "RGB values have to be between 0 and 255.");
    }
        
    chainLinkPresenter.updateChain();
  }

  @Override
  protected void updateMarkedElement() {   
    content = (RGBMatrix) chainLinkPresenter.getContent();
    
    selectedX = markID % content.getWidth();
    selectedY = (markID / content.getWidth());

    Color markedPixel = content.get(selectedX, selectedY);
    redField.setText(String.valueOf(Math.round(markedPixel.getRed() * 255)));
    greenField.setText(String.valueOf(Math.round(markedPixel.getGreen() * 255)));
    blueField.setText(String.valueOf(Math.round(markedPixel.getBlue() * 255)));
  }
  
  /**
   * Verifies whether the value is a RGB color value.
   *
   * @param textField the text field with the input
   * @return true, if successful
   */
  private boolean validateColorValue(TextField textField) {
    String input = textField.getText();
    
    if (input.isEmpty()) {
      textField.setText("0"); 
    } else if (!input.matches("\\b(1?[0-9]{1,2}|2[0-4][0-9]|25[0-5])\\b")) {
      // input is not between 0 and 255
      return false;
    }
    
    return true;
  }
}
