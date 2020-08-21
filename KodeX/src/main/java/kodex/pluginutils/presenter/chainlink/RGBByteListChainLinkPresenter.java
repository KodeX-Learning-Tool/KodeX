package kodex.pluginutils.presenter.chainlink;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser.ExtensionFilter;
import kodex.model.I18N;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.RGBByteList;
import kodex.pluginutils.presenter.edit.RGBByteListEditPresenter;
import kodex.pluginutils.presenter.header.RGBByteListHeaderPresenter;

/**
 * The Class RGBByteListChainLinkPresenter manages the view for the RGB byte list.
 *
 * @author Raimon Gramlich
 */
public class RGBByteListChainLinkPresenter extends ChainLinkPresenter {

  /** The chain link name. */
  private static final String CHAIN_LINK_NAME = "RGB-Byte-Liste";

  /** The rgb byte list view. */
  private ListView<String> rgbByteListView;
  
  /** The Constant NOT_MARKED. */
  private static final int NOT_MARKED = -1;

  /** The ID of the last element marked. */
  private int lastElementMarked = NOT_MARKED;

  /** Whether to listen for changes or not. */
  private boolean listenForChanges = true;

  /**
   * Instantiates a new RGB byte list chain link presenter.
   *
   * @param previous the previous ChainLinkPresenter
   * @param previousStep the previous step
   * @param nextStep the next step
   */
  public RGBByteListChainLinkPresenter(
      ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
    super(previous, previousStep, nextStep);
    content = new RGBByteList();
    chainLinkEditPresenter = new RGBByteListEditPresenter(this);
    chainLinkHeaderPresenter = new RGBByteListHeaderPresenter(this.getContent());
    
    rgbByteListView = new ListView<>();
    
    // adds listener to list view items
    rgbByteListView
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
    return rgbByteListView.getSelectionModel().getSelectedIndex();
  }

  @Override
  public AnchorPane getView() {
    updateView();

    return new AnchorPane(rgbByteListView);
  }

  @Override
  public String getName() {
    return CHAIN_LINK_NAME;
  }

  @Override
  protected void mark(int id) {
    lastElementMarked = id;
    listenForChanges = false;
    rgbByteListView.getSelectionModel().select(id);
    rgbByteListView.scrollTo(id);
    listenForChanges = true;
  }

  @Override
  public void updateView() {
    List<String> list = ((RGBByteList) getContent()).getList();
    
    List<String> tripleList = new LinkedList<>();
    
    for (int j = 0; j < list.size(); j += 3) {
      tripleList.add(list.get(j) + ", " + list.get(j + 1) + ", " + list.get(j + 2));
    }
    
    listenForChanges = false;
    rgbByteListView.setItems(FXCollections.observableArrayList(tripleList));
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
