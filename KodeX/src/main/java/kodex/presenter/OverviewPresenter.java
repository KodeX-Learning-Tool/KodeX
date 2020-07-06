package kodex.presenter;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import kodex.plugininterface.ChainLinkPresenter;

/**
 * This presenter is responsible for the overview over the Coding Chain.
 * Clicking an element in the overview will cause the ChainPresenter to jump to the corresponding Chain Link. 
 * 
 * @author Yannick Neubert
 * 
 * @version 1.0
 */
public class OverviewPresenter implements IPresenter {
	
    /**
     * The Chain-Presenter containig the view of the Coding Chain.
     */
    private ChainPresenter chainPresenter;

    /**
     * The HBox containing the view of the Overview.
     */
    private HBox overview;



    /**
     * Creates a new Overview-Presenter.
     */
    public OverviewPresenter() {
        // TODO implement here
    }

    /**
     * This method is executed if the user clicks on an element in the Overview. 
     * It Jumps to the corresponding Chain Link.
     */
    public void handleJumpTo() {
        // TODO implement here
    }

    /**
     * This method sets the Chain-Presenter containing the view for this Coding Chain.
     * @param chainPresenter : The Chain-Presenter for this Coding Chain.
     */
    public void setChainPresenter(ChainPresenter chainPresenter) {
        // TODO implement here
    }

    /**
     * This method creates the Overview Items. Starting by the first Chain Link of the Coding Chain,
     * this method iterates over the Chain Links and creates the Overview Items.
     * @param firstChainLink : The Presenter to the first Chain Link.
     */
    public void createOverviewItems(ChainLinkPresenter firstChainLink) {
        // TODO implement here
    }

    /**
     * This method adds an Overview Item to the Overview.
     * @param chainLink : The item that is to be added.
     */
    private void addOverviewItem(Button chainLink) {
        // TODO implement here
    }

    /**
     * This method highlights an item in the Overview.
     * @param id : The ID of the item.
     */
    private void emphasizeButton(int id) {
        // TODO implement here
    }

    @Override
    public AnchorPane getView() {
        // TODO implement here
        return null;
    }

}