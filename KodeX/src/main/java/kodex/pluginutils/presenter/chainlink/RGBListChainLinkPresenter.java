package kodex.pluginutils.presenter.chainlink;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser.ExtensionFilter;
import kodex.model.I18N;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.RGBList;
import kodex.pluginutils.presenter.edit.RGBListEditPresenter;
import kodex.pluginutils.presenter.header.RGBListHeaderPresenter;

/** 
 * The Class RGBListChainLinkPresenter manages the view for the RGB list.
 * 
 * @author Raimon Gramlich
 */
public class RGBListChainLinkPresenter extends ChainLinkPresenter {

  /** The chain link name. */
  private static final String CHAIN_LINK_NAME = "RGB-Liste";

  /** The rgb list view. */
  private ListView<String> rgbListView;

  /** The Constant NOT_MARKED. */
  private static final int NOT_MARKED = -1;

  /** The ID of the last element marked. */
  private int lastElementMarked = NOT_MARKED;

  /** Whether to listen for changes or not. */
  private boolean listenForChanges = true;

  /**
   * Instantiates a new RGB list chain link presenter.
   *
   * @param previous the previous ChainLinkPresenter
   * @param previousStep the previous step
   * @param nextStep the next step
   */
  public RGBListChainLinkPresenter(
      ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
    super(previous, previousStep, nextStep);
    content = new RGBList();
    chainLinkEditPresenter = new RGBListEditPresenter(this);
    chainLinkHeaderPresenter = new RGBListHeaderPresenter(this.getContent());
    
    rgbListView = new ListView<>();
    
    // adds listener to list view items
    rgbListView
        .getSelectionModel()
        .selectedIndexProperty()
        .addListener((obs, old, newV) -> {
          if (listenForChanges && newV.intValue() != -1) {
            handleMark();
          }
        });
    
  }

  @Override
  protected int calculateID() {
    return rgbListView.getSelectionModel().getSelectedIndex();
  }

  /**
   * Gets the RGB string of the given color.
   *
   * @param color the color
   * @return the rgb string
   */
  private String colorToRGBString(Color color) {
    return "(" + ((int) Math.round(color.getRed() * 255)) + ", "
        + (int) Math.round(color.getGreen() * 255) + ", "
        + (int) Math.round(color.getBlue() * 255)  + ")";
  }

  @Override
  public AnchorPane getView() {
    updateView();

    return new AnchorPane(rgbListView);
  }

  @Override
  public String getName() {
    return CHAIN_LINK_NAME;
  }

  @Override
  protected void mark(int id) {
    lastElementMarked = id;
    listenForChanges = false;
    rgbListView.getSelectionModel().select(id);
    rgbListView.scrollTo(id);
    listenForChanges = true;
  }

  @Override
  public void updateView() {
    ObservableList<String> list = FXCollections.observableArrayList();
    
    for (Color color : ((RGBList) getContent()).getList()) {
      list.add(colorToRGBString(color));
    }
    
    listenForChanges = false;
    rgbListView.setItems(list);
    listenForChanges = true;
    
    // remarks the view
    if (lastElementMarked !=  NOT_MARKED) {
      mark(lastElementMarked);
    }
  }
  
  @Override
  public List<ExtensionFilter> getExtensionsFilter() {
    List<ExtensionFilter> extensionFilters = new ArrayList<>();
    extensionFilters.add(new ExtensionFilter(I18N.get("files.text"), "*.txt"));
    return extensionFilters;
  }
}
