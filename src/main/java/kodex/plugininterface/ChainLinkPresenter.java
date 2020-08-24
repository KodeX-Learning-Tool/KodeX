package kodex.plugininterface;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser.ExtensionFilter;
import kodex.exceptions.AlertWindowException;
import kodex.model.I18N;
import kodex.presenter.IPresenter;

/**
 * This class represents an abstract link. A link can export a content, mark an item in this
 * content, and request the presenter for the view of a content. Every link must expand this class.
 *
 * @author Patrick Spiesberger
 * @author Raimon Gramlich
 * @version 1.0
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
   * ChainLinkPresenter class constructors. Sets the previous level, as well as the next and
   * previous step. The next level is not set because it is not known when a chain is initialized.
   *
   * @param previous : previous level
   * @param previousStep : previous step
   * @param nextStep : next step
   */
  public ChainLinkPresenter(
      ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
    this.previous = previous;
    this.previousStep = previousStep;
    this.nextStep = nextStep;
  }

  /**
   * Calculates the ID that was clicked on the content.
   *
   * @return : ID representing the element to be marked
   */
  protected int calculateID() {
    return -1;
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
   * Returns the ChainLinkEditPresenter of this link.
   *
   * @return ChainLinkEditPresenter of this link
   */
  public ChainLinkEditPresenter getChainLinkEditPresenter() {
    return chainLinkEditPresenter;
  }

  /**
   * Gets the ChainLinkPresenter of this link.
   *
   * @return The ChainLinkHeaderPresenter
   */
  public ChainLinkHeaderPresenter getChainLinkHeader() {
    return chainLinkHeaderPresenter;
  }

  /**
   * Returns the content of this link.
   *
   * @return content of this link
   */
  public Content<?> getContent() {
    return this.content;
  }

  /**
   * Returns the next link.
   *
   * @return next link
   */
  public ChainLinkPresenter getNext() {
    return this.next;
  }

  /**
   * Returns the previous link.
   *
   * @return previous link
   */
  public ChainLinkPresenter getPrev() {
    return this.previous;
  }

  /**
   * Returns the symbol that is used in the overview to represent this link.
   *
   * @return Symbol representing this link
   */
  public Image getSymbol() {
    // TODO return Standard Bild
    return null;
  }

  /**
   * Returns the view of a content.
   *
   * @return View in the form of an AnchorPane in which the content is displayed
   */
  @Override
  public abstract AnchorPane getView();
  
  /**
   * Updates the view.
   */
  protected abstract void updateView();
  
  /**
   * Gets the name of the chain link.
   *
   * @return the name of the chain link
   */
  public abstract String getName();

  /**
   * Called when something is clicked in the view of this link and thus marked. Marks the element
   * belonging to the id in the view and ensures that the corresponding element is recursively
   * marked in all other links of the procedure.
   */
  protected void handleMark() {
    int id = calculateID();
    if (id != -1) {
      mark(id);
      
      // set marked element id for editing
      if (this.chainLinkEditPresenter != null) {
        this.chainLinkEditPresenter.setMarkID(id);
      }
      
      if (previous != null) {
        previous.markPrev(id);
      }

      if (next != null) {
        next.markNext(id);
      }
    }
  }

  /**
   * Marks the corresponding element in the view of this link with the help of the transferred ID.
   *
   * @param id : The ID to identify the element to be marked
   */
  protected abstract void mark(int id);

  /**
   * Marks the element corresponding to the ID in the next link and therefore recursively in all
   * next links.
   *
   * @param id : representative ID
   */
  public void markNext(int id) {
    mark(id);
    if (next != null) {
      next.markNext(id);
    }
  }

  /**
   * Marks the element corresponding to the ID in the previous link and therefore recursively in all
   * previous links.
   *
   * @param id : representative ID
   */
  public void markPrev(int id) {
    mark(id);
    if (previous != null) {
      previous.markPrev(id);
    }
  }

  /**
   * Sets the content for this link.
   *
   * @param content : content to be set
   * @throws AlertWindowException if an error happens during updating the chain
   */
  public void setContent(Content<?> content) throws AlertWindowException {
    this.content = content;
    updateChain();
  }

  /**
   * Sets the next link to the desired next link in the chain.
   *
   * @param next : next link
   */
  public void setNext(ChainLinkPresenter next) {
    this.next = next;
  }

  /**
   * Sets the previous link to the desired previous link in the chain.
   *
   * @param prev : previous link
   */
  public void setPrev(ChainLinkPresenter prev) {
    this.previous = prev;
  }

  /**
   * Recursively updates the content of all links of the process chain from this link. Must always
   * be called when the content of the link has been edited.
   * @throws AlertWindowException if an error happens during encoding or decoding 
   */
  public void updateChain() throws AlertWindowException {
    updateView();
    updateNextChainLink();
    updatePrevChainLink();
  }

  /** Updates the next link. 
   * @throws AlertWindowException if an error happens during encoding
   */
  public void updateNextChainLink() throws AlertWindowException {
    if (next != null) {
      nextStep.encode(content, next.getContent());
      next.updateView();
      next.updateNextChainLink();
    }
  }

  /** Updates the previous link. 
   * @throws AlertWindowException if an error happens during decoding
   */
  public void updatePrevChainLink() throws AlertWindowException {
    if (previous != null) {
      previousStep.decode(content, previous.getContent());
      previous.updateView();
      previous.updatePrevChainLink();
    }
  }

  /**
   * Gets the extensions filter for exporting.
   *
   * @return the extensions filter
   */
  public List<ExtensionFilter> getExtensionsFilter() {
    List<ExtensionFilter> extensionFilters = new ArrayList<>();
    extensionFilters.add(new ExtensionFilter(I18N.get("files.all"), "*.*"));
    return extensionFilters;
  }
}
