package kodex.presenter;

import java.io.File;
import java.io.IOException;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ProcedurePlugin;

/**
 * This Presenter is responsible for the Coding Chain. This Page shows the Coding Chain in its entirety
 * and is responsible for interactions regarding the display of said Coding Chain. These are creating
 * the view for a given Procedure-Plugin, hiding a Chain Link, and jumping to a Chain Link.
 * 
 * @author Yannick Neubert
 * @author Raimon Gramlich
 * 
 * @version 1.0
 */
public class ChainPresenter implements IPresenter {
	
    /** The scrollable Pane in which the split pane is nested. */
	@FXML
	private ScrollPane viewScrollPane;
	
    /** The split pane in which the Chain Links are displayed. */
	@FXML
	private SplitPane chainSplitPane;
	
	/** The view object containing everything which is displayed in the chain. */
	private AnchorPane chainRootPane;
	
	/** The reference to the first ChainLinkPresenter. */
	private ChainLinkPresenter firstChainLinkPresenter;
	
	/** The reference to the ProcedureLayoutPresenter. */
	private ProcedureLayoutPresenter procedureLayoutPresenter;
	
    /**
     * Creates a new ChainPresenter with a reference to the first ChainLinkPresenter
     * and a ProcedureLayoutPresenter.
     * 
     * @param chainLinkPresenter : The reference to the first ChainLinkPresenter.
     * @param procedureLayoutPresenter : The reference to a ProcedureLayoutPresenter.
     */
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
	 * This class represents a single chain link. It loads a template via fxml
	 * file and fills it with the content it has gotten from an instance of the 
	 * ChainLinkPresenter class.
	 * 
	 * @author Raimon Gramlich
	 * 
	 * @version 1.0
	 */
    private class ChainItem extends AnchorPane {
		
    	/** The Label for displaying the name of the chain link. */
		@FXML
		private Label titleLabel;
		
		/** The BorderPane to the ProcedureLayoutPresenter. */
		@FXML
		private BorderPane chainLinkPane;
		
		/** The reference to the ProcedureLayoutPresenter. */
		@FXML
		private VBox informationBox;
		
		/** The reference to the ProcedureLayoutPresenter. */
		@FXML
		private FontIcon hideButtonIcon;
		
		/** The reference to the ProcedureLayoutPresenter. */
		private Boolean isHidden;
		
		/** The reference to the ProcedureLayoutPresenter. */
		private String hiddenIcon = "mdi-chevron-down";
		
		/** The reference to the ProcedureLayoutPresenter. */
		private String shownIcon = "mdi-chevron-right";
		
		/** The reference to the ProcedureLayoutPresenter. */
		private ChainLinkPresenter chainLinkPresenter;
		
	    /**
	     * Creates a new ChainItem with a reference to its ChainLinkPresenter.
	     * 
	     * @param chainLinkPresenter : The reference to the ChainLinkPresenter.
	     */
		ChainItem(ChainLinkPresenter chainLinkPresenter) {
			this.chainLinkPresenter = chainLinkPresenter;
			
			isHidden = false; 
			
			try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("chianlinktemplate.fxml"));
	            loader.setController(this);
	            loader.setRoot(this);
	            loader.load();
	        } catch (IOException exc) {
	        	System.err.println("The file chianlinktemplate.fxml was not found!");
	        }
			
		}
		
	    /**
	     * Initializes the view-object created by the FXMLLoader.
	     * Sets the chain item name and adds the header view object to the displayed view.
	     */
		@FXML
		private void initialize() {
			// titleLabel.setText("Kodierungsstufe: " + chainLinkPresenter.getName());
			informationBox.getChildren().set(0, chainLinkPresenter.getChainLinkHeaderView());
		}
		
	    /**
	     * This method is executed when the users clicks on the button to edit a Chain Link.
	     * It opens the editor window.
	     */
		@FXML
		private void handleEdit() {
			procedureLayoutPresenter.setEditPresenter(chainLinkPresenter.getChainLinkEditPresenter());
		}
		
	    /**
	     * This method is executed when the users clicks on the button to export the content of a chain link.
	     * It writes the content to the disk in a file specified by the user.
	     */
		@FXML
		private void handleExport() {
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Choose export location");
			File exportLocation = chooser.showSaveDialog(null);
			
			chainLinkPresenter.export(exportLocation);
		}
		
	    /**
	     * This method is executed when the users clicks on the button to hide a Chain Link. It minimizes 
	     * the Chain Link in the Split-Pane.
	     */
		@FXML
		private void handleHideChainLink() {
			toggleHide();
		}
		
	    /** Toggles between the flag isHidden between true and false, while changing the icon accordingly.  */
		private void toggleHide() {
			if (isHidden) {
				hideButtonIcon.setIconLiteral(shownIcon);
				isHidden = false;
			} else {
				hideButtonIcon.setIconLiteral(hiddenIcon);
				isHidden = true;
			}
		}
	}
    
    /**
     * This method creates the view for a given Procedure-Plugin.
     * 
     * @param activeProcedure the active Procedure-Plugin.
     */
    public void createChainView(ProcedurePlugin activeProcedure) {		
    	ChainLinkPresenter chainLinkPresenter = firstChainLinkPresenter;
	
    	// add a newly created chain item for each ChainLinkPresenter
		int i = 0;
		while (chainLinkPresenter != null) {
			chainSplitPane.getItems().set(i, new ChainItem(chainLinkPresenter));
			chainLinkPresenter = chainLinkPresenter.getNext();
			i++;
		}	
    }

    /**
     * This method jumps to the Chain Link with the given ID.
     * 
     * @param id : The ID of the Chain Link.
     */
    public void jumpToChainLink(int id) {
    	double hValue;
    	double position;
    	
    	// TODO: calculate jump position
    	
    	// This sets the horizontal scroll position.
    	// viewScrollPane.setHvalue(hValue);
    	
    	// This sets the position of the vertical dividers between the Chain Links.
    	// chainSplitPane.setDividerPosition(id, position);
    }

    @Override
    public AnchorPane getView() {
        return chainRootPane;
    }

}