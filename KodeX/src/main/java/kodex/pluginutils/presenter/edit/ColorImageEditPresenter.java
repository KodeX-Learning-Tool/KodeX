package kodex.pluginutils.presenter.edit;

import javafx.scene.control.ColorPicker;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import kodex.plugininterface.ChainLinkEditPresenter;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.pluginutils.model.content.ColorImage;

/**
 * This class manages the edit view and is responsible for editing a color image.
 * 
 *  @author Raimon Gramlich
 */
public class ColorImageEditPresenter extends ChainLinkEditPresenter {
  
  private ColorImage content;
  private int selectedX;
  private int selectedY;
  private ColorPicker colorPicker;
  
  private AnchorPane view;
  
  /**
   * Instantiates a new color image edit presenter.
   *
   * @param chainLinkPresenter the chain link presenter
   */
  public ColorImageEditPresenter(ChainLinkPresenter chainLinkPresenter) {
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
    content.setColor(selectedX, selectedY, colorPicker.getValue());
    chainLinkPresenter.updateChain();
  }
  
  protected void updateMarkedElement() {
    content = (ColorImage) chainLinkPresenter.getContent();
    WritableImage img = content.getImage();
    selectedX = markID % (int) Math.round(img.getWidth());
    selectedY = (markID / (int) Math.round(img.getWidth()));

    Color markedPixel = content.getColor(selectedX, selectedY);

    colorPicker = new ColorPicker(markedPixel);
    
    view.getChildren().set(0, colorPicker);  
  }
}
