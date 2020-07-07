package kodex.presenter;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import kodex.plugininterface.ChainLinkEditPresenter;
import kodex.plugininterface.ProcedurePlugin;

/**
 * This Presenter is responsible for the Procedure Plugin page.
 * It manages a number of Presenters, each responsible for a unique part in creating a Coding Procedure.
 * 
 * @author Yannick Neubert
 * 
 * @version 1.0
 */
public class ProcedureLayoutPresenter extends Presenter {
	
	@FXML
	private HBox overviewBox;
	@FXML
	private BorderPane procedurePane;
	@FXML
	private Pane overlayPane;
    /**
     * The active Coding Procedure. It contains the data for the Coding Chain and 
     * the Presenters for editing the data as well as an Import Presenter.
     */
    private ProcedurePlugin activeProcedure;

    /**
     * The Overview Presenter to the Procedure Plugin.
     */
    private OverviewPresenter overviewPresenter;

    /**
     * The active Presenter. Is either an Import Presenter or a ChainPresenter.
     */
    private IPresenter activePresenter;

    /**
     * The active Edit Presenter.
     */
    private ChainLinkEditPresenter editPresenter;



    /**
     * Creates a new Procedure-Layout-Presenter with a reference to a Presenter-Manager
     * and a Procedure Plugin.
     * @param pm : The reference to the Presenter-Manager.
     * @param activePlugin : The active Procedure-Plugin.
     */
    public ProcedureLayoutPresenter(PresenterManager pm, ProcedurePlugin activePlugin) {
        // TODO implement here
        super(pm, "procedurelayout");
    }

    /**
     * This method is executed if the user clicks on the button to close the Edit-Window.
     * It closes the Edit-Window.
     */
    public void handleCloseEditWindow() {
        // TODO implement here
    }

    /**
     * This method creates a new Chain-Presenter and sets it as the active Presenter.
     */
    public void switchToChainPresenter() {
        // TODO implement here
    }

    /**
     * This method sets the Edit-Presenter in order to show an Edit-Window.
     * @param editPresenter : The Edit-Presenter.
     */
    public void setEditPresenter(ChainLinkEditPresenter editPresenter) {
        // TODO implement here
    }

    /**
     * This method gets rid of the Edit-Window.
     */
    public void clearEditView() {
        // TODO implement here
    }
}