package kodex.plugininterface;

import javafx.scene.layout.AnchorPane;
import kodex.presenter.IPresenter;

/**
 * 
 */
public abstract class ChainLinkEditPresenter implements IPresenter {

    /**
     * Default constructor
     */
    public ChainLinkEditPresenter() {
    }

    /**
     * 
     */
    protected ChainLinkPresenter chainLinkPresenter;

    /**
     * 
     */
    protected int markID;


    /**
     * @param chainLinkPresenter
     */
    public void ChainLinkEditPresenter(ChainLinkPresenter chainLinkPresenter) {
        // TODO implement here
    }

    /**
     * 
     */
    public abstract void handleSubmit();

    /**
     * @param id
     */
    public void setMarkID(int id) {
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