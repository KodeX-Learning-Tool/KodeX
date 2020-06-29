package kodex.presenter;

import java.util.*;

/**
 * 
 */
public class ProcedureLayoutPresenter extends Presenter {

    /**
     * Default constructor
     */
    public ProcedureLayoutPresenter() {
    }

    /**
     * 
     */
    private ProcedurePlugin activeProcedure;

    /**
     * 
     */
    private OverviewPresenter overviewPresenter;

    /**
     * 
     */
    private IPresenter activePresenter;

    /**
     * 
     */
    private ChainLinkEditPresenter editPresenter;






    /**
     * @param pm 
     * @param activePlugin
     */
    public void ProcedureLayoutPresenter(PresenterManager pm, ProcedurePlugin activePlugin) {
        // TODO implement here
    }

    /**
     * 
     */
    public void handleCloseEditWindow() {
        // TODO implement here
    }

    /**
     * 
     */
    public void switchToChainPresenter() {
        // TODO implement here
    }

    /**
     * @param editPresenter
     */
    public void setEditPresenter(ChainLinkEditPresenter editPresenter) {
        // TODO implement here
    }

    /**
     * 
     */
    public void clearEditView() {
        // TODO implement here
    }

}