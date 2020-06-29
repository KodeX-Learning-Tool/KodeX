package kodex.presenter;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import kodex.plugininterface.ChainLinkPresenter;

/**
 * 
 */
public class OverviewPresenter implements IPresenter {

    /**
     * Default constructor
     */
    public OverviewPresenter() {
    }

    /**
     * 
     */
    private ChainPresenter chainPresenter;

    /**
     * 
     */
    private HBox overview;



    /**
     * 
     */
    public void OverviewPresenter() {
        // TODO implement here
    }

    /**
     * 
     */
    public void handleJumpTo() {
        // TODO implement here
    }

    /**
     * @param chainPresenter
     */
    public void setChainPresenter(ChainPresenter chainPresenter) {
        // TODO implement here
    }

    /**
     * @param firstChainLink
     */
    public void createOverviewItems(ChainLinkPresenter firstChainLink) {
        // TODO implement here
    }

    /**
     * @param chainLink
     */
    private void addOverviewItem(Button chainLink) {
        // TODO implement here
    }

    /**
     * @param id
     */
    private void emphasizeButton(int id) {
        // TODO implement here
    }

    /**
     * @return
     */
    public AnchorPane getView() {
        // TODO implement here
        return null;
    }

}