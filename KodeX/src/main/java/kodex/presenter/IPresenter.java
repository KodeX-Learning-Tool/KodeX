package kodex.presenter;

import javafx.scene.Parent;

/**
 * This is the interface, which has to be implemented by all presenters.
 * A presenter creates a view object that can be displayed in the GUI.
 * This object is also responsible for all interactions with the user and has to convey them to its presenter.
 * 
 * @author Leonhard Kraft
 * @author Yannick Neubert
 * 
 * @version 1.0
 */
public interface IPresenter {

    /**
     * This method returns a view object, that should be used for displaying in the application window.
     * 
     * @return Parent The view object to be displayed.
     */
    public Parent getView();

}