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
import kodex.pluginutils.model.content.RGBList;
import kodex.pluginutils.presenter.edit.RGBListEditPresenter;
import kodex.pluginutils.presenter.header.RGBListHeaderPresenter;

/** @author Raimon Gramlich */
public class RGBListChainLinkPresenter extends ChainLinkPresenter {

	/** The chain link name. */
	private static final String CHAIN_LINK_NAME = "RGB-Liste";

  /** The rgb list view. */
  private ListView<String> rgbListView;

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
    // chainLinkHeaderPresenter = new RGBListHeaderPresenter(this.getContent());
    content = new RGBList();
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
    AnchorPane chainLinkPane = new AnchorPane();

    rgbListView = new ListView<>();

    ObservableList<String> list = FXCollections.observableArrayList();

    for (Color color : ((RGBList) getContent()).getList()) {
      list.add(colorToRGBString(color));
    }

    rgbListView.setItems(list);

    // adds listener to list view items
    rgbListView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            new ChangeListener<String>() {

              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // executes handleMark on selected
                handleMark();
              }
            });

    chainLinkPane.getChildren().add(rgbListView);

    return chainLinkPane;
  }

	@Override
	public String getName() {
		return CHAIN_LINK_NAME;
	}

  @Override
  protected void mark(int id) {
    rgbListView.getSelectionModel().select(id);
    chainLinkEditPresenter.setMarkID(id);
  }
}
