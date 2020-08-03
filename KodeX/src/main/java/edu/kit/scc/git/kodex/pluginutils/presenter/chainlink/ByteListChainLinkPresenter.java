package edu.kit.scc.git.kodex.pluginutils.presenter.chainlink;

import java.util.List;

import edu.kit.scc.git.kodex.plugininterface.ChainLinkPresenter;
import edu.kit.scc.git.kodex.plugininterface.ChainStep;
import edu.kit.scc.git.kodex.pluginutils.model.content.ByteList;
import edu.kit.scc.git.kodex.pluginutils.presenter.edit.ByteListEditPresenter;
import edu.kit.scc.git.kodex.pluginutils.presenter.header.RGBByteListHeaderPresenter;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * The Class RGBByteListChainLinkPresenter.
 *
 * @author Raimon Gramlich
 */
public class ByteListChainLinkPresenter extends ChainLinkPresenter {

  /** The chain link name. */
  private static final String CHAIN_LINK_NAME = "Byte-Liste";

  /** The rgb byte list view. */
  private ListView<String> byteListView;

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
    byteListView = new ListView<>();

    List<String> list = ((ByteList) getContent()).getList();

    byteListView.setItems(FXCollections.observableArrayList(list));

    // adds listener to list view items
    byteListView.getSelectionModel()
          .selectedIndexProperty().addListener((obs, old, newV) -> handleMark());

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
    byteListView.getSelectionModel().select(id);
    chainLinkEditPresenter.setMarkID(id);
  }

  @Override
  public void updateView() {
    // TODO Auto-generated method stub
    
  }
}
