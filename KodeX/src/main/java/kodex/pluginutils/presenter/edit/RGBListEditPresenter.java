package kodex.pluginutils.presenter.edit;

import java.util.function.UnaryOperator;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import kodex.plugininterface.ChainLinkEditPresenter;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.pluginutils.model.content.RGBList;

/**
 * This class manages the edit view and is responsible for editing a RGB list.
 * 
 *  @author Raimon Gramlich
 */
public class RGBListEditPresenter extends ChainLinkEditPresenter {
  
  /** The content. */
  private RGBList content;
  
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
   * Instantiates a new RGB list edit presenter.
   *
   * @param chainLinkPresenter the corresponding chain link presenter
   */
  public RGBListEditPresenter(ChainLinkPresenter chainLinkPresenter) {
    super(chainLinkPresenter);
    
    // only allows 0 to 255 as input
    UnaryOperator<TextFormatter.Change> filter = change -> {
      if (change.getControlNewText().matches("\\b(1?[0-9]{1,2}|2[0-4][0-9]|25[0-5])\\b")) {
        return change;
      } else if (change.getControlNewText().isEmpty()) {
        change.setText("0");
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
  public void handleSubmit() {
    content.getList().set(markID, new Color(
        Double.valueOf(redField.getText()) / 255d,
        Double.valueOf(greenField.getText()) / 255d,
        Double.valueOf(blueField.getText()) / 255d, 1d));
    
    chainLinkPresenter.updateChain();
  }

  @Override
  protected void updateMarkedElement() {
    content = (RGBList) chainLinkPresenter.getContent();
    
    Color markedPixel = content.get(markID);
    
    redField.setText(String.valueOf(Math.round(markedPixel.getRed() * 255)));
    greenField.setText(String.valueOf(Math.round(markedPixel.getGreen() * 255)));
    blueField.setText(String.valueOf(Math.round(markedPixel.getBlue() * 255)));
  }
}
