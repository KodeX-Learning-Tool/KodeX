package kodex.pluginutils.presenter.chainlink;


import java.util.LinkedList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.RGBByteList;
import kodex.pluginutils.presenter.edit.RGBByteListEditPresenter;
import kodex.pluginutils.presenter.header.RGBByteListHeaderPresenter;

/**
 * The Class RGBByteListChainLinkPresenter.
 *
 * @author Raimon Gramlich
 */
public class RGBByteListChainLinkPresenter extends ChainLinkPresenter {

	/** The rgb byte list view. */
	private ListView<String> rgbByteListView;

	/**
	 * Instantiates a new RGB byte list chain link presenter.
	 *
	 * @param previous the previous ChainLinkPresenter
	 * @param previousStep the previous step
	 * @param nextStep the next step
	 */
	public RGBByteListChainLinkPresenter(ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
		super(previous, previousStep, nextStep);
		chainLinkEditPresenter = new RGBByteListEditPresenter(this);
		// chainLinkHeaderPresenter = new RGBByteListHeaderPresenter(this.getContent());
		content = new RGBByteList();
	}

	@Override
	protected void mark(int id) {
		rgbByteListView.getSelectionModel().select(id);
		chainLinkEditPresenter.setMarkID(id);
	}
	
	@Override
	protected int calculateID() {
		return rgbByteListView.getSelectionModel().getSelectedIndex();
	}

	@Override
	public AnchorPane getView() {
		AnchorPane chainLinkPane = new AnchorPane();
		
		rgbByteListView = new ListView<>();
		
		LinkedList<String> list = ((RGBByteList) getContent()).getList();
		
		LinkedList<String> tripleList = new LinkedList<>();
		
		for (int j = 0; j < list.size(); j += 3) {
			tripleList.add(list.get(j) + ", " + list.get(j + 1) + ", " + list.get(j + 2));
		}
		
		rgbByteListView.setItems(FXCollections.observableArrayList(tripleList));
		
		// adds listener to list view items
		rgbByteListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        // executes handleMark on selected
		        handleMark();
		    }
		});
		
		// add list view to chain link view
		chainLinkPane.getChildren().add(rgbByteListView);
		
		return chainLinkPane;
	}
	
}