package kodex.pluginutils.presenter.chainlink;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser.ExtensionFilter;
import kodex.model.I18N;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.BitList;
import kodex.pluginutils.presenter.edit.BitListEditPresenter;
import kodex.pluginutils.presenter.header.RGBListHeaderPresenter;

/** 
 * The Class BitListChainLinkPresenter manages the view for the bit list.
 * 
 * @author Raimon Gramlich 
 * @author Patrick Spiesberger
 * 
 */
public class BitListChainLinkPresenter extends ChainLinkPresenter {

  /** The chain link name. */
  private static final String CHAIN_LINK_NAME = "Bit-Liste";

  /** The rgb list view. */
  private ListView<Integer> bitListView = new ListView<>();

  /** Whether to listen for changes or not. */
  private boolean listenForChanges = true;

  /**
   * Instantiates a new RGB list chain link presenter.
   *
   * @param previous the previous ChainLinkPresenter
   * @param previousStep the previous step
   * @param nextStep the next step
   */
  public BitListChainLinkPresenter(
      ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
    super(previous, previousStep, nextStep);
    chainLinkEditPresenter = new BitListEditPresenter(this);
    content = new BitList();
    chainLinkHeaderPresenter = new RGBListHeaderPresenter(this.getContent());
  }

  @Override
  protected int calculateID() {
    return bitListView.getSelectionModel().getSelectedIndex();
  }

  @Override
  public AnchorPane getView() {
    ObservableList<Integer> list = FXCollections.observableArrayList();

    for (Integer color : ((BitList) getContent()).getList()) {
      list.add(color);
    }

    bitListView.setItems(list);

    // adds listener to list view items
    bitListView
        .getSelectionModel()
        .selectedIndexProperty()
        .addListener((obs, old, newV) -> {
          if (listenForChanges && newV.intValue() != -1) {
            handleMark();
          }
        });

    AnchorPane chainLinkPane = new AnchorPane();
    
    chainLinkPane.getChildren().add(bitListView);

    return chainLinkPane;
  }

  @Override
  public String getName() {
    return CHAIN_LINK_NAME;
  }

  @Override
  protected void mark(int id) {
    listenForChanges = false;
    bitListView.getSelectionModel().select(id);
    bitListView.scrollTo(id);
    listenForChanges = true;
    chainLinkEditPresenter.setMarkID(id);
  }

  @Override
  public void updateView() {
    ObservableList<Integer> list = FXCollections.observableArrayList();

    for (Integer color : ((BitList) getContent()).getList()) {
      list.add(color);
    }

    bitListView.setItems(list); 
  }
  
  @Override
  public List<ExtensionFilter> getExtensionsFilter() {
    List<ExtensionFilter> extensionFilters = new ArrayList<>();
    extensionFilters.add(new ExtensionFilter(I18N.get("files.text"), "*.txt"));
    return extensionFilters;
  }
}
