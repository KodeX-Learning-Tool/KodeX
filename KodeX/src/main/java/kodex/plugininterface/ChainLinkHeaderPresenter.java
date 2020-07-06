package kodex.plugininterface;

import javafx.scene.layout.AnchorPane;
import kodex.presenter.IPresenter;

/**
 * This class represents an abstract HeaderPresenter for a specific link.
 * It ensures that the header belonging to the content of the level can be displayed.

 * @author Patrick Spiesberger
 * 
 * @version 1.0
 *
 */
public abstract class ChainLinkHeaderPresenter implements IPresenter {

    /*
     * The content of the link belonging to the HeaderPresenter, which
     * contains the header
     */
    protected Content content;

    /**
     * The constructor of class ChainLinkHeaderPresenter
     * @param content : the content of the link to which this header presenter belongs
     */
    public ChainLinkHeaderPresenter(Content content) {
        this.content = content;
    }

    /**
     * Sets the content for this HeaderPresenter
     * @param content : Content to be set for the header presenter
     */
    public void setContent(Content content) {
    	this.content = content;
    }

    /**
     * Returns the view for displaying the header of the content
     * @return View in the form of an AnchorPane, in which the header is displayed
     */
    public abstract AnchorPane getView();

}