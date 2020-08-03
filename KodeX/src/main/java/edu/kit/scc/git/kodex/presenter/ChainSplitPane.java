package edu.kit.scc.git.kodex.presenter;

import javafx.scene.control.Skin;
import javafx.scene.control.SplitPane;

public class ChainSplitPane extends SplitPane {
  
  
  /** 
   * {@inheritDoc}
   */
  @Override
  protected Skin<?> createDefaultSkin() {
    return new ChainSplitPaneSkin(this);
  }
}
