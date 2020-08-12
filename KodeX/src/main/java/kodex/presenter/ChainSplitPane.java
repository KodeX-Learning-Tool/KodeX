package kodex.presenter;

import javafx.scene.control.Skin;
import javafx.scene.control.SplitPane;

public class ChainSplitPane extends SplitPane {
  
  private double initialWidth;
  
  private ChainSplitPaneSkin skin;

  /** 
   * {@inheritDoc}
   */
  @Override
  protected Skin<?> createDefaultSkin() {
    this.skin = new ChainSplitPaneSkin(this);
    return skin;
  }

  public void setInitialWidth(double width) {
    this.initialWidth = width;
  }

  public double getInitialWidth() {
    return initialWidth;
  }
  
  public void disableDivider(int index) {
    skin.disableDivider(index);
  }
  
  public void enableDivider(int index) {
    skin.enableDivider(index);
  }
}
