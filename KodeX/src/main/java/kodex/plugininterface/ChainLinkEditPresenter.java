package kodex.plugininterface;

import javafx.scene.layout.AnchorPane;
import kodex.exceptions.InvalidInputException;
import kodex.presenter.IPresenter;

/**
 * This class represents an abstract EditPresenter for a specific link. It manages the edit view and
 * is responsible for all of the users implemented and feasible changes in content.
 *
 * @author Patrick Spiesberger
 * @version 1.0
 */
public abstract class ChainLinkEditPresenter implements IPresenter {

  /*
   * The link to which this edit belongs
   */
  protected ChainLinkPresenter chainLinkPresenter;

  /*
   * The ID of the element that is currently selected
   */
  protected int markID;

  /**
   * The constructor of class ChainLinkEditPresenter.
   *
   * @param chainLinkPresenter : the link associated with this edit
   */
  public ChainLinkEditPresenter(ChainLinkPresenter chainLinkPresenter) {
    this.chainLinkPresenter = chainLinkPresenter;
  }

  /**
   * Returns the view for editing the content.
   *
   * @return View in the form of an AnchorPane in which the content can be edited
   */
  @Override
  public abstract AnchorPane getView();

  /** This method is called when the user changes the Contents of a link and handles this change. 
   * @throws InvalidInputException thrown if the user input is invalid
   */
  public abstract void handleSubmit() throws InvalidInputException;
  
  /** Update the marked element in the edit-view. */
  protected abstract void updateMarkedElement();
  
  /**
   * Sets the markID of this edit. This should always happen when something new in the level was
   * marked to keep the edit always up to date. Also updates the view accordingly.
   *
   * @param id : the representative ID
   */
  public void setMarkID(int id) {
    this.markID = id;
    updateMarkedElement();
  }
}
