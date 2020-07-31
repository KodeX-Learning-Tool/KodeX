package kodex.pluginutils.presenter.chainlink;

import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.ByteList;
import kodex.pluginutils.presenter.edit.RGBByteListEditPresenter;
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
  private ListView<String> byteListView;

  /**
   * Instantiates a new RGB byte list chain link presenter.
   *
   * @param previous     the previous ChainLinkPresenter
   * @param previousStep the previous step
   * @param nextStep     the next step
   */
  public ByteListChainLinkPresenter(ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
    super(previous, previousStep, nextStep);
    chainLinkEditPresenter = new RGBByteListEditPresenter(this);
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
    byteListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
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
    byteListView.getSelectionModel().select(id);
    chainLinkEditPresenter.setMarkID(id);
  }
}
