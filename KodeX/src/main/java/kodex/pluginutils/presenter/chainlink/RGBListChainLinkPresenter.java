package kodex.pluginutils.presenter.chainlink;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
    chainLinkEditPresenter = new RGBListEditPresenter(this);
    content = new RGBList();
    chainLinkHeaderPresenter = new RGBListHeaderPresenter(this.getContent());
    
    rgbListView = new ListView<>();
    
    // adds listener to list view items
    rgbListView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener((obs, old, newV) -> handleMark());
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
    return "("
        + String.valueOf((int) Math.round(color.getRed() * 255))
        + ", "
        + String.valueOf((int) Math.round(color.getGreen() * 255))
        + ", "
        + String.valueOf((int) Math.round(color.getBlue() * 255))
        + ")";
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
    rgbListView.getSelectionModel().select(id);
    chainLinkEditPresenter.setMarkID(id);
  }

  @Override
  public void updateView() {
    ObservableList<String> list = FXCollections.observableArrayList();
    
    list.clear();
    
    System.out.println("Size: " + list.size());
    
    for (Color color : ((RGBList) getContent()).getList()) {
      list.add(colorToRGBString(color));
    }
    System.out.println("Size: " + list.size());
    
    rgbListView.setItems(list);
    
    // remarks the view
    if (lastElementMarked !=  NOT_MARKED) {
      mark(lastElementMarked);
    }
  }
}
