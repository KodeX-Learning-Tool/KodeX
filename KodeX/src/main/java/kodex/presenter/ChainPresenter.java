package kodex.presenter;

import java.io.File;
import java.io.IOException;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.SplitPane.Divider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import kodex.model.I18N;
import kodex.plugininterface.ChainLinkHeaderPresenter;
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
	
	/** The dividers of the SplitPane chainSplitPane. */
	private ObservableList<Divider> dividers;
	
	/** This Boolean stores whether the dividers are programmatically moved at the moment. */
	private boolean isMoving = false;
	
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
     * Initializes the view-object created by the FXMLLoader.
     */
    @FXML
    private void initialize() {

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
		
		/** The VBox containing information like the header and the export button. */
		@FXML
		private VBox informationBox;
		
		/** The Icon displaying whether or not the chain item is expanded. */
		@FXML
		private FontIcon hideButtonIcon;
		
		/** The actual content of the chain item. */
		@FXML
		private AnchorPane chainItemContent;
		
		/** The AnchorPane which is displayed when the content is hidden. */
		@FXML
		private AnchorPane hiddenPane;
		
		/** The Label displaying the text when the content is hidden. */
		@FXML
		private Label hiddenLabel;
		
		/** This Boolean represents the state of the chain item. */
		private Boolean isHidden;
		
		/** The IconLiteral of the hidden-icon. */
		private String hiddenIcon = "mdi-chevron-down";
		
		/** The IconLiteral of the expanded-icon. */
		private String shownIcon = "mdi-chevron-right";
		
		/** The reference to the ChainLinkPresenter. */
		private ChainLinkPresenter chainLinkPresenter;
		
	    /**
	     * Creates a new ChainItem with a reference to its ChainLinkPresenter.
	     * 
	     * @param chainLinkPresenter : The reference to the ChainLinkPresenter.
	     */
		ChainItem(ChainLinkPresenter chainLinkPresenter) {
			this.chainLinkPresenter = chainLinkPresenter;
			
			isHidden = false; 
			
			// loads template file
			try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("chainlinktemplate.fxml"));
	            loader.setController(this);
	            loader.setRoot(this);
	            loader.load();
	        } catch (IOException exc) {
	            exc.printStackTrace();
	        	System.err.println("The file chainlinktemplate.fxml was not found!");
	        }
		}
		
	    /**
	     * Initializes the view-object created by the FXMLLoader.
	     * Sets the chain item name and adds the header view object to the displayed view.
	     */
		@FXML
		private void initialize() {
			// titleLabel.setText("Kodierungsstufe: " + chainLinkPresenter.getName());
		    
		    ChainLinkHeaderPresenter header = chainLinkPresenter.getChainLinkHeader();
		    
		    // enable language support
		    hiddenLabel.textProperty().bind(I18N.createStringBinding("chainlinktemplate.hiddenlabel").concat(""));
		    
		    // bind the visibility to the managed property and hide the hiddenPane
		    chainItemContent.visibleProperty().bind(chainItemContent.managedProperty());
		    hiddenPane.visibleProperty().bind(hiddenPane.managedProperty());
		    hiddenPane.setManaged(false);
		    
		    // display the header if available
		    if (header != null) {
		        informationBox.getChildren().set(0, header.getView());
		    }
			
		    // display the chain link content
			chainLinkPane.setCenter(chainLinkPresenter.getView());
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
			// opens save dialog to choose where you want to save the file
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Choose export location");
			File exportLocation = chooser.showSaveDialog(null);
			
			// initiates exporting the chain link content
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
			if (Boolean.TRUE.equals(isHidden)) {
				// change the icon
				hideButtonIcon.setIconLiteral(shownIcon);
				
				// reverse max width
				this.setMaxWidth(chainItemContent.getMaxWidth());
				
				// show chain item content and hide the hidden pane
				hiddenPane.setManaged(false);
				chainItemContent.setManaged(true);
				isHidden = false;
			} else {
				// change the icon
				hideButtonIcon.setIconLiteral(hiddenIcon);
				
				// force the chain item to be thinner
				this.setMaxWidth(hiddenPane.getPrefWidth());
				
				// hide chain item content and show the hidden pane
				hiddenPane.setManaged(true);
				chainItemContent.setManaged(false);
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
		while (chainLinkPresenter != null) {
			chainSplitPane.getItems().add(new ChainItem(chainLinkPresenter));
			chainLinkPresenter = chainLinkPresenter.getNext();
		}
    	
    	// add change listener to the dividers of the SplitPane
    	dividers = chainSplitPane.getDividers();
    	
    	for (Divider divider: dividers) {
    		divider.positionProperty().addListener((obs, oldValue, newValue) -> {
    			if (Boolean.FALSE.equals(isMoving)) {
        			double delta = newValue.doubleValue() - oldValue.doubleValue();
        			moveDividers(divider, delta);
    			}
    		});
    	} 
    }

    /**
     * This method jumps to the Chain Link with the given ID.
     * 
     * @param id : The ID of the Chain Link.
     */
    public void jumpToChainLink(int id) {    	
    	// This sets the position of the vertical dividers between the Chain Links.
    	// TODO: expand the chain item
		
    	Node chainItem = chainSplitPane.getChildrenUnmodifiable().get(id);
    	
    	// calculate the hValue for the view port of the scroll pane
        double scrollPanewidth = viewScrollPane.getContent().getBoundsInLocal().getWidth();
        double x = (chainItem.getParent().getParent().getBoundsInParent().getMaxX() + chainItem.getParent().getParent().getBoundsInParent().getMinX()) / 2.0;
        double viewPortWdith = viewScrollPane.getViewportBounds().getWidth();
        viewScrollPane.setHvalue(viewScrollPane.getHmax() * ((x - 0.5 * viewPortWdith) / (scrollPanewidth - viewPortWdith)));
    }
    
    /**
     * This method moves the other dividers, if possible, by the same amount as the given divider.
     * 
     * @param divider : The divider which was moved.
     * @param delta : The signed amount which the divider was moved.
     */
    private void moveDividers(Divider divider, double delta) {
    	isMoving = true;
    	
    	int id = dividers.indexOf(divider);
    	
    	// move left or right hand dividers depending on the direction of movement
    	if (delta > 0) {
        	for (int i = id + 1; i < dividers.size(); i++) {
        		double newPosition = dividers.get(i).getPosition() + delta/100;

        		dividers.get(i).setPosition(newPosition);
        	}	
    	} else if (delta < 0) {
        	for (int i = id - 1; i >= 0; i--) {
        		double newPosition = dividers.get(i).getPosition() + delta/100;
				
        		dividers.get(i).setPosition(newPosition);
        	}
    	} 
    	isMoving = false;
    }

    @Override
    public AnchorPane getView() {
        return chainRootPane;
    }

}