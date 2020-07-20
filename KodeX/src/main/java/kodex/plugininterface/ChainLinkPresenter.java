package kodex.plugininterface;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import kodex.presenter.IPresenter;

/**
 * This class represents an abstract link. A link can export a content, mark
 * an item in this content, and request the presenter for the view of a content.
 * Every link must expand this class.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0 
 *
 */
public abstract class ChainLinkPresenter implements IPresenter {

	
	/* The link that comes after this link in the process chain */
    protected ChainLinkPresenter next;

    /* The level that comes before this level in the process chain */
    protected ChainLinkPresenter previous;

    /* The content of this link */
    protected Content<?> content;

    /* 
 	 * The step in the process chain with which you can convert the content
	 * of this link into the content for the previous link
     */
    protected ChainStep previousStep;

    /* 
 	 * The step in the process chain with which you can convert the content
	 * of this link into the content for the next link
     */
    protected ChainStep nextStep;
    
    /** The corresponding chain link edit presenter. */
    protected ChainLinkEditPresenter chainLinkEditPresenter;
    
    /** The corresponding chain link header presenter. */
    protected ChainLinkHeaderPresenter chainLinkHeaderPresenter;

    /**
     * ChainLinkPresenter class constructors.
     * Sets the previous level, as well as the next and previous step.
     * The next level is not set because it is not known when a chain is initialized.
     * @param previous : previous level
     * @param previousStep : vprevious step
     * @param nextStep : next step
     */
    public ChainLinkPresenter(ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
        this.previous = previous;
        this.previousStep = previousStep;
        this.nextStep = nextStep;
    }

    
    /**
     * Returns the ChainLinkEditPresenter of this link
     * @return ChainLinkEditPresenter of this link
     */
    public ChainLinkEditPresenter getChainLinkEditPresenter() {
        return chainLinkEditPresenter;
    }

    /**
     * Returns the view of the header for this level, only calls getView () 
     * on the ChainLinkHeaderPresenter of this level and returns the result.
     * @return View of the ChainLinkHeaderPresenter
     */
    public Pane getChainLinkHeaderView() {
        return chainLinkHeaderPresenter.getView();
    }

    /**
     * Sets the content for this link
     * @param content : content to be set
     */
    public void setContent(Content<?> content) {
        this.content = content;
        updateChain();
    }

    /**
     * Returns the content of this link
     * @return content of this link
     */
    public Content<?> getContent() {
        return this.content;
    }

    /**
     * Recursively updates the content of all links of the process
     * chain from this link. Must always be called when the
     * content of the link has been edited
     */
    public void updateChain() {
    	updateNextChainLink();
    	updatePrevChainLink();
    }

    /**
     * Updates the next link
     */
    public void updateNextChainLink() {
    	if (next != null) {
        	nextStep.encode(content, next.getContent());
        	next.updateNextChainLink();
    	}
    }

    /**
     * Updates the previous link
     */
    public void updatePrevChainLink() {
    	if (previous != null) {
    		previousStep.decode(previous.getContent(), content);
        	previous.updatePrevChainLink();
    	}
    }

    /**
     * Marks the corresponding element in the view of this link
     * with the help of the transferred ID
     * @param id : The ID to identify the element to be marked
     */
    protected abstract void mark(int id);

    /**
     * Called when something is clicked in the view of this link and thus marked.
     * Marks the element belonging to the id in the view and ensures that the
     * corresponding element is recursively marked in all other links of the procedure.
     */
    public void handleMark() {
        int id = calculateID();
        if (id != -1) {
        	mark(id);
        	if (previous != null) {
            	previous.markPrev(id);
        	}
        	
        	if (next != null) {
        		next.markNext(id);
        	}
        }
    }
    
    
    /**
     * Calculates the ID that was clicked on the content
     * @return : ID representing the element to be marked
     */
    protected int calculateID() {
		return -1;
    }

    /**
     * Marks the element corresponding to the ID in the previous link 
     * and therefore recursively in all previous links
     * @param id : representative ID
     */
    public void markPrev(int id) {
    	mark(id);
        if (previous != null) {
        	previous.markPrev(id);
        }
    }

    /**
     * Marks the element corresponding to the ID in the next link 
     * and therefore recursively in all next links
     * @param id : representative ID
     */
    public void markNext(int id) {
        mark(id);
        if (next != null) {
        	next.markNext(id);
        }
    }

    /**
     * Sets the next link to the desired next link in the chain
     * @param next : next link
     */
    public void setNext(ChainLinkPresenter next) {
        this.next = next;
    }

    /**
     * Returns the next link
     * @return next link
     */
    public ChainLinkPresenter getNext() {
        return this.next;
    }

    /**
     * Sets the previous link to the desired previous link in the chain
     * @param prev : previous link
     */
    public void setPrev(ChainLinkPresenter prev) {
        this.previous = prev;
    }

    /**
     * Returns the previous link
     * @return previous link
     */
    public ChainLinkPresenter getPrev() {
        return this.previous;
    }

    /**
     * Returns the symbol that is used in the overview to represent this link.
     * @return Symbol representing this link
     */
    public Image getSymbol() {
        // TODO return Standard Bild
        return null;
    }
    
    /**
     * Passes on the export location to the content for exporting.
     * 
     * @param exportLocation : The location which the content is exported to
     */
    public void export(File exportLocation) {
    	content.export(exportLocation);
    }

    /**
     * Returns the view of a content
     * @return View in the form of an AnchorPane in which the content is displayed
     */
    public abstract AnchorPane getView();

}