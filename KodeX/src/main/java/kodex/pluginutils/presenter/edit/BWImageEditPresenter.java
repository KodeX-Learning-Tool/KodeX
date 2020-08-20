package kodex.pluginutils.presenter.edit;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import kodex.model.I18N;
import kodex.plugininterface.ChainLinkEditPresenter;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.pluginutils.model.content.BlackWhiteImage;
import kodex.presenter.PresenterManager;

/**
 * This class manages the edit view and is responsible for editing a black & white image.
 * 
 *  @author Raimon Gramlich
 *  @author Patrick Spiesberger
 *  
 *  @version 1.0
 */
public class BWImageEditPresenter extends ChainLinkEditPresenter {
  
  /** The content which this class edits. */
  private BlackWhiteImage content;
  
  /** The x coordinate of the selected pixel. */
  private int selectedX;
  
  /** The y coordinate of the selected pixel. */
  private int selectedY;
  
  /** The color picker used for user input. */
  private ColorPicker colorPicker;
  
  /** The edit view. */
  private AnchorPane view;
  
  /**
   * Instantiates a new color image edit presenter.
   *
   * @param chainLinkPresenter the chain link presenter
   */
  public BWImageEditPresenter(ChainLinkPresenter chainLinkPresenter) {
    super(chainLinkPresenter);
    
    view = new AnchorPane(new ColorPicker());
  }

  @Override
  public AnchorPane getView() {
    updateMarkedElement();
    
    return view;
  }

  @Override
  public void handleSubmit() {
    Color newColor = colorPicker.getValue();
    
    if (!newColor.toString().equals(Color.BLACK.toString()) 
        && !newColor.toString().equals(Color.WHITE.toString()))  {
      Alert alert = new Alert(AlertType.WARNING);
      alert.titleProperty().bind(I18N.createStringBinding("alert.title.warning"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));
      alert.setContentText("This isn't a valid black or white value");
      PresenterManager.showAlertDialog(alert);
      return;
    }
    
    content.setColor(selectedX, selectedY, newColor);
    chainLinkPresenter.updateChain();
  }
  
  protected void updateMarkedElement() {
    content = (BlackWhiteImage) chainLinkPresenter.getContent();
    WritableImage img = content.getImage();
    selectedX = markID % (int) Math.round(img.getWidth());
    selectedY = (markID / (int) Math.round(img.getWidth()));

    Color markedPixel = content.getColor(selectedX, selectedY);

    colorPicker = new ColorPicker(markedPixel);
    
    view.getChildren().set(0, colorPicker);  
  }
}
