package kodex.pluginutils.presenter.edit;

import java.util.function.UnaryOperator;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import kodex.model.I18N;
import kodex.plugininterface.ChainLinkEditPresenter;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.pluginutils.model.content.RGBByteList;
import kodex.presenter.PresenterManager;

/**
 * This class manages the edit view and is responsible for editing a RGB byte list.
 * 
 *  @author Raimon Gramlich
 *  @author Patrick Spiesberger
 *  
 *  @version 1.0
 */
public class RGBByteListEditPresenter extends ChainLinkEditPresenter {
  
  private static final int RGB_BINARY_LENGTH = 8;

  private RGBByteList content;
  
  private TextField redField;
  private TextField greenField;
  private TextField blueField;
  
  /** The text formatter which only allows binary input. */
  private TextFormatter<String> redFormatter;
  
  /** The text formatter which only allows binary input. */
  private TextFormatter<String> greenFormatter;
  
  /** The text formatter which only allows binary input. */
  private TextFormatter<String> blueFormatter;
  
  /** The length of one unit used to determine how many list elements belong together. */
  private int unitLength;
  
  private AnchorPane view;

  /**
   * Instantiates a new RGB byte list edit presenter.
   *
   * @param chainLinkPresenter the corresponding chain link presenter
   */
  public RGBByteListEditPresenter(ChainLinkPresenter chainLinkPresenter) {
    super(chainLinkPresenter);
    
    // only allows 0 or 1 as input
    UnaryOperator<TextFormatter.Change> filter = change -> {
      if (change.getControlNewText().matches("[0-1]+")) {
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
    
    content = (RGBByteList) chainLinkPresenter.getContent();
  }

  @Override
  public AnchorPane getView() {
    unitLength = (int) content.getHeader().get("unit-length");
    
    updateMarkedElement();
    
    return view;
  }

  @Override
  public void handleSubmit() {
    setBinaryColorString(markID * unitLength, redField.getText());
    setBinaryColorString(markID * unitLength + 1, greenField.getText());
    setBinaryColorString(markID * unitLength + 2, blueField.getText());

    chainLinkPresenter.updateChain();
  }

  @Override
  protected void updateMarkedElement() {
    content = (RGBByteList) chainLinkPresenter.getContent();
    
    redField.setText(String.valueOf(content.get(markID * unitLength)));
    greenField.setText(String.valueOf(content.get(markID * unitLength + 1)));
    blueField.setText(String.valueOf(content.get(markID * unitLength + 2)));
  }
  
  /**
   * Validates and sets the binary color string.
   *
   * @param input the input string
   * @return the string with leading zeros if necessary
   */
  private void setBinaryColorString(int index, String input) {
    // strip leading zeros to check if the string is equal to or less than max length
    input = input.replaceFirst("^0+(?!$)", "");
    if (input.length() > RGB_BINARY_LENGTH) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.error"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
      alert.setContentText(
          "RGB values range from 0 to 255." 
              + " In Binary this means there can only be 8 digits without counting leading zeros.");
      PresenterManager.showAlertDialog(alert);
      
      return;
    } else if (input.length() < RGB_BINARY_LENGTH) {
      while (input.length() < RGB_BINARY_LENGTH) {
        input = "0".concat(input);
      }
    }
    
    content.getList().set(index, input);
  }
}
