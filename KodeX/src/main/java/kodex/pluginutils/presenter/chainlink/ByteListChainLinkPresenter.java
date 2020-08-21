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
import kodex.pluginutils.model.content.ByteList;
import kodex.pluginutils.presenter.edit.ByteListEditPresenter;
import kodex.pluginutils.presenter.header.RGBByteListHeaderPresenter;

/**
 * The Class RGBByteListChainLinkPresenter.
 *
 * @author Raimon Gramlich
 */
public class ByteListChainLinkPresenter extends ChainLinkPresenter {

  /** The chain link name. */
  private static final String CHAIN_LINK_NAME = "Byte-Liste";

  /** The rgb byte list view. */
  private ListView<String> byteListView = new ListView<>();
  
  /** Whether to listen for changes or not. */
  private boolean listenForChanges = true;

  /**
   * Instantiates a new RGB byte list chain link presenter.
   *
   * @param previous     the previous ChainLinkPresenter
   * @param previousStep the previous step
   * @param nextStep     the next step
   */
  public ByteListChainLinkPresenter(
      ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
    super(previous, previousStep, nextStep);
    chainLinkEditPresenter = new ByteListEditPresenter(this);
    content = new ByteList();
    chainLinkHeaderPresenter = new RGBByteListHeaderPresenter(this.getContent());
  }

  @Override
  protected int calculateID() {
    return byteListView.getSelectionModel().getSelectedIndex();
  }

  @Override
  public AnchorPane getView() {
    List<String> list = ((ByteList) getContent()).getList();

    byteListView.setItems(FXCollections.observableArrayList(list));

    // adds listener to list view items
    byteListView.getSelectionModel()
          .selectedIndexProperty().addListener((obs, old, newV) -> {
            if (listenForChanges && newV.intValue() != -1) {
              handleMark();
            }
          });

    AnchorPane chainLinkPane = new AnchorPane();

    // add list view to chain link view
    chainLinkPane.getChildren().add(byteListView);

    return chainLinkPane;
  }

  @Override
  public String getName() {
    return CHAIN_LINK_NAME;
  }

  @Override
  protected void mark(int id) {
    listenForChanges = false;
    byteListView.getSelectionModel().select(id);
    byteListView.scrollTo(id);
    listenForChanges = true;
    chainLinkEditPresenter.setMarkID(id / 8);
  }

  @Override
  public void updateView() {
    ObservableList<String> list = FXCollections.observableArrayList();

    for (String color : ((ByteList) getContent()).getList()) {
      list.add(color);
    }

    byteListView.setItems(list);
  }
  
  @Override
  public List<ExtensionFilter> getExtensionsFilter() {
    List<ExtensionFilter> extensionFilters = new ArrayList<>();
    extensionFilters.add(new ExtensionFilter(I18N.get("files.text"), "*.txt"));
    return extensionFilters;
  }
}
