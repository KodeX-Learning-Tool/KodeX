package kodex.pluginutils.presenter.chainlink;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.BitList;
import kodex.pluginutils.model.content.RGBList;
import kodex.pluginutils.presenter.edit.RGBListEditPresenter;

/** 
 * @author Raimon Gramlich 
 * @author Patrick Spiesberger
 * 
 */
public class BitListChainLinkPresenter extends ChainLinkPresenter {

  /** The chain link name. */
  private static final String CHAIN_LINK_NAME = "Bit-Liste";

  /** The rgb list view. */
  private ListView<Integer> bitListView;

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
    chainLinkEditPresenter = new RGBListEditPresenter(this); //TODO: anpassen
    // chainLinkHeaderPresenter = new RGBListHeaderPresenter(this.getContent());
    content = new RGBList();
  }

  @Override
  protected int calculateID() {
    return bitListView.getSelectionModel().getSelectedIndex();
  }

  @Override
  public AnchorPane getView() {
    bitListView = new ListView<>();

    ObservableList<Integer> list = FXCollections.observableArrayList();

    for (Integer color : ((BitList) getContent()).getList()) {
      list.add(color);
    }

    bitListView.setItems(list);

    // adds listener to list view items
    bitListView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            new ChangeListener<Integer>() {
              @Override
              public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                // executes handleMark on selected
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
    bitListView.getSelectionModel().select(id);
    chainLinkEditPresenter.setMarkID(id);
  }
}
