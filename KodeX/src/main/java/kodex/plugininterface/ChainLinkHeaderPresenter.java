package kodex.plugininterface;

import javafx.scene.layout.AnchorPane;
import kodex.presenter.IPresenter;

/**
 * 
 */
public abstract class ChainLinkHeaderPresenter implements IPresenter {

    /**
     * Default constructor
     */
    public ChainLinkHeaderPresenter() {
    }

    /**
     * 
     */
    protected Content content;

    /**
     * @param content
     */
    public void ChainLinkHeaderPresenter(Content content) {
        // TODO implement here
    }

    /**
     * @param content
     */
    public void setContent(Content content) {
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