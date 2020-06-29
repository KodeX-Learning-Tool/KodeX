package kodex.presenter;

import java.util.*;

/**
 * 
 */
public abstract class Presenter implements IPresenter {

    /**
     * Default constructor
     */
    public Presenter() {
    }

    /**
     * 
     */
    protected PresenterManager presenterManager;



    /**
     * @param pm
     */
    public void Presenter(PresenterManager pm) {
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