package kodex.presenter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import kodex.plugininterface.ProcedurePlugin;

/**
 * This Presenter is responsible for the Coding Chain. This Page shows the Coding Chain in its entirety
 * and is responsible for interactions regarding the display of said Coding Chain. These are creating
 * the view for a given Procedure-Plugin, hiding a Chain Link, and jumping to a Chain Link.
 * 
 * @author Yannick Neubert
 * 
 * @version 1.0
 */
public class ChainPresenter implements IPresenter {

    /**
     * The position of the Scrollbar.
     */
    private double horizontalScrollPosition;

    /**
     * The scrollable Pane in which the split pane is nested.
     */
    private ScrollPane scrollPane;

    /**
     * The split pane in which the Chain Links are displayed.
     */
    private SplitPane splitPane;

    

    /**
     * Creates a new ChainPresenter.
     */
	private AnchorPane chainRootPane;
	
	private ChainLinkPresenter firstChainLinkPresenter;
	
	private ProcedureLayoutPresenter procedureLayoutPresenter;
	
    /**
     * This method is executed when the users clicks on the button to hide a Chain Link. It minimizes 
     * the Chain Link in the Split-Pane.
     */
    public void handleHideChainLink() {
        // TODO implement here
    public ChainPresenter(ChainLinkPresenter chainLinkPresenter, ProcedureLayoutPresenter procedureLayoutPresenter) {
    	this.firstChainLinkPresenter = chainLinkPresenter;
		this.procedureLayoutPresenter = procedureLayoutPresenter;
		
		// loads the template file
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chainlayout.fxml"));
		fxmlLoader.setController(this);
		try {
			chainRootPane = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * This method creates the view for a given Procedure-Plugin.
     * @param activeProcedure the active Procedure-Plugin.
     */
    public void createChainView(ProcedurePlugin activeProcedure) {
        // TODO implement here
    }

    /**
     * This method jumps to the Chain Link with the given ID.
     * @param id : The ID of the Chain Link.
     */
    public void jumpToChainLink(int id) {
        // TODO implement here
    }

    /**
     * This method sets the position of the vertical dividers between the Chain Links.
     * @param id : The ID of the divider.
     * @param pos : The position to which the divider has to be moved.
     */
    public void setStageDivider(int id, double pos) {
        // TODO implement here
    }

    /**
     * Sets the scrollbar position in order to jump to a Chain Link.
     * @param hValue the value the Scrollbar is to be set to.
     */
    private void setHorizontalScrollPosition(double hValue) {
        // TODO implement here
    }

    @Override
    public AnchorPane getView() {
        return chainRootPane;
    }

}