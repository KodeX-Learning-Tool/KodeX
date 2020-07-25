package kodex.presenter;

import java.io.IOException;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import kodex.plugininterface.ChainLinkEditPresenter;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedurePlugin;

/**
 * This Presenter is responsible for the Procedure Plugin page.
 * It manages a number of Presenters, each responsible for a unique part in creating a Coding Procedure.
 * 
 * @author Yannick Neubert
 * @author RaimoN Gramlich
 * 
 * @version 1.0
 */
public class ProcedureLayoutPresenter extends Presenter {
	
	/** The HBox displaying the overview items. */
	@FXML
	private HBox overviewBox;
	
	/** The BorderPane which displays the overview bar at the top and the view of the active presenter in the center. */
	@FXML
	private BorderPane procedurePane;
	
	/** The Pane which makes sliding in the Editor possible. */
	@FXML
	private StackPane overlayPane;
		
    /**
     * The active Coding Procedure. It contains the data for the Coding Chain and 
     * the Presenters for editing the data as well as an Import Presenter.
     */
    private ProcedurePlugin activeProcedure;

    /** The active Presenter. Is either an Import Presenter or a ChainPresenter. */
    private IPresenter activePresenter;

    /**
     * Creates a new Procedure-Layout-Presenter with a reference to a Presenter-Manager
     * and a Procedure Plugin.
     * 
     * @param pm : The reference to the Presenter-Manager.
     * @param activePlugin : The active Procedure-Plugin.
     */
    public ProcedureLayoutPresenter(PresenterManager pm, ProcedurePlugin activePlugin) {
        super(pm, "procedurelayout");
        this.activeProcedure = activePlugin;
        
        // creates and displays a new import presenter based on the selected procedure
        this.activePresenter = activeProcedure.createImportPresenter();
        ((ImportPresenter) activePresenter).setLayoutPresenter(this);
	    procedurePane.setCenter(activePresenter.getView());	
    }
	
	/**
	 * This class represents a single overview item. It loads a template via fxml
	 * file and fills it with the information it has gotten from an instance of the 
	 * ChainLinkPresenter class.
	 * 
	 * @author Raimon Gramlich
	 * 
	 * @version 1.0
	 */
	private class OverviewItem extends Button {
		
		/** The ImageView which displays the symbol. */
		@FXML
		private ImageView overviewThumbNail;
		
		/** An icon or a thumb nail representing a chain link. */
		private Image thumbNail;
		
		/** The abbreviation of the chain link name. It is displayed if there is no symbol. */
		private String chainLinkNameAbbreviation;
		
		/** The id of the chain link it represents in the chain. */
		private int id;
		
	    /**
	     * Creates a new Procedure-Layout-Presenter with a reference to a Presenter-Manager
	     * and a Procedure Plugin.
	     * 
	     * @param chainLinkPresenter : The reference to the ChainLinkPresenter.
	     * @param id : The id of the ChainLink the item is representing.
	     */
		OverviewItem(ChainLinkPresenter chainLinkPresenter, int id) {
			this.id = id;
			
			// gets the symbol and string representing the chain link
			thumbNail = chainLinkPresenter.getSymbol();
			// procedureNameInitial = chainLinkPresenter.getName().substring(0, 1);
			
			
			// loads the template file
			try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("overviewbuttontemplate.fxml"));
	            loader.setController(this);
	            loader.setRoot(this);
	            loader.load();
	        } catch (IOException exc) {
	        	System.err.println("The file overviewbutton.fxml was not found!");
	        }
			
			this.getStyleClass().add("overview__item");
		}
		
	    /**
	     * Initializes the view-object created by the FXMLLoader. If there is no image to display,
	     * it sets a String as the text of the overview item instead.
	     */
		@FXML
		private void initialize() {
			if (thumbNail != null) {
				// displays the icon / thumb nail
				overviewThumbNail.setImage(thumbNail);
				this.setText("");
			} else {
				// displays a text instead
				this.setText(chainLinkNameAbbreviation);
			}	
		}
		
	    /**
	     * This method is executed if the user clicks on a overview item.
	     * It jumps to the chosen chain link in the chain view.
	     */
		@FXML
		private void handleJumpTo() {
			((ChainPresenter) activePresenter).jumpToChainLink(id);
		}
	}
	
    /** This method displays the chain view with the fitting items in the overview bar. */
    public void switchToChainPresenter() {
    	// switches the import presenter with a new chain presenter 
		activePresenter = new ChainPresenter(activeProcedure.getChainHead(), this);
		
		// fills the overview bar with items
		addOverviewItems();
		
		// fills the chain view with chain links, then display it
		((ChainPresenter) activePresenter).createChainView(activeProcedure);
		procedurePane.setCenter(activePresenter.getView());
    }	

    /** This creates and adds the items to the overview bar. */
	private void addOverviewItems() {
		ChainLinkPresenter chainLinkPresenter = activeProcedure.getChainHead();
		
		// loops over the chain link presenter and keeps adding newly created overview items
		int i = 0;
		while (chainLinkPresenter != null) {
			overviewBox.getChildren().add(new OverviewItem(chainLinkPresenter, i));
			
			// adds a buffer used for a responsive page design
			Region region = new Region();
			region.setPrefWidth(100);
			overviewBox.getChildren().add(region);
			
			chainLinkPresenter = chainLinkPresenter.getNext();
			i++;
		}
	}

	/**
	 * This class represents the editor-window which slides in when the respective button is clicked.
	 * It loads a template via fxml file and fills it with the view-object it has gotten from an
	 * instance of the ChainLinkEditPresenter class.
	 * 
	 * @author Raimon Gramlich
	 * 
	 * @version 1.0
	 */
    private class Editor extends AnchorPane {
		
    	/** The VBox which displays the concrete view for a chain link.. */
		@FXML
		private VBox editItemsBox;
		
		/** The ChainLinkEditPresenter from which the editor gets the concrete view for a chain link. */
		private ChainLinkEditPresenter editPresenter;
		
		/** The TranslateTransition for sliding the editor window in and out. */
		private TranslateTransition editorTranslation;
		
	    /**
	     * Creates a new Editor with a reference to a ChainLinkEditPresenter.
	     * 
	     * @param editPresenter : The reference to the ChainLinkEditPresenter.
	     */
		Editor(ChainLinkEditPresenter editPresenter) {
			this.editPresenter = editPresenter;
			
			// loads the template file
			try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("editlayout.fxml"));
	            loader.setController(this);
	            loader.setRoot(this);
	            loader.load();
	        } catch (IOException exc) {
	        	System.err.println("The file editlayout.fxml was not found!");
	        }
		}
		
	    /** Initializes the view-object created by the FXMLLoader. Sets up a TranslateTransition.  */
		@FXML
		private void initialize() {
		    this.setPrefWidth(200);
		    this.setPrefHeight(overlayPane.getHeight());
		    this.setTranslateX(overlayPane.getWidth());
		    
		    // creates a new TranslateTransition to move the window on the x-axis
		    editorTranslation = new TranslateTransition(Duration.millis(500), this);
		    editorTranslation.setFromX(overlayPane.getWidth() + 150);
		    editorTranslation.setToX(overlayPane.getWidth() - 150);
		    
		    // gets and adds the concrete editor items for the chain link
		    editItemsBox.getChildren().set(3, editPresenter.getView());
		}
		
	    /**
	     * This method is executed if the user clicks on the button to close the Edit-Window.
	     * It closes the Edit-Window.
	     */
		@FXML
		private void handleCloseEditor() {
			hideEditor();
		}
		
		/** Plays the slide in animation at normal rate. */
		public void showEditor() {
		    editorTranslation.setRate(1);
		    editorTranslation.play();
		}
		
		/** Plays the slide in animation at a normal rate in reverse. */
		public void hideEditor() {
			editorTranslation.setRate(-1);
			editorTranslation.play();
		}
	}

    /**
     * This method sets the Edit-Presenter in order to show an Edit-Window.
     * 
     * @param editPresenter : The Edit-Presenter.
     */
    public void setEditPresenter(ChainLinkEditPresenter editPresenter) {
		Editor editor = new Editor(editPresenter);
		overlayPane.getChildren().add(editor);
		editor.showEditor();
    }
}