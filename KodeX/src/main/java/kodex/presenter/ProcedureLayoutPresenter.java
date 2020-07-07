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
import javafx.scene.layout.Pane;
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
     * The active Presenter. Is either an Import Presenter or a ChainPresenter.
     */
    private IPresenter activePresenter;

    /**
     * Creates a new Procedure-Layout-Presenter with a reference to a Presenter-Manager
     * and a Procedure Plugin.
     * @param pm : The reference to the Presenter-Manager.
     * @param activePlugin : The active Procedure-Plugin.
     */
    public ProcedureLayoutPresenter(PresenterManager pm, ProcedurePlugin activePlugin) {
        super(pm, "procedurelayout");
        this.activeProcedure = activePlugin;
        this.activePresenter = activeProcedure.createImportPresenter();
        ((ImportPresenter) activePresenter).setLayoutPresenter(this);
	    procedurePane.setCenter(activePresenter.getView());	
    }
	private class OverviewItem extends Button {
		
		@FXML
		private ImageView overviewThumbNail;
		
		private Image thumbNail;
		
		private String chainLinkNameAbbreviation;
		
		private int id;
		
		OverviewItem(ChainLinkPresenter chainLinkPresenter, int id) {
			this.id = id;
			
			thumbNail = chainLinkPresenter.getSymbol();
			
			// procedureNameInitial = chainLinkPresenter.getName().substring(0, 1);
			
			try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("overviewbutton.fxml"));
	            loader.setController(this);
	            loader.setRoot(this);
	            loader.load();
	        } catch (IOException exc) {
	        	System.err.println("The file overviewbutton.fxml was not found!");
	        }
			
			this.getStyleClass().add("overview__item");
		}
		
		@FXML
		private void initialize() {
			if (thumbNail != null) {
				overviewThumbNail.setImage(thumbNail);
				this.setText("");
			} else {
				this.setText(chainLinkNameAbbreviation);
			}	
		}
		
		@FXML
		private void handleJumpTo() {
			((ChainPresenter) activePresenter).jumpToChainLink(id);
		}
	}
	
    public void switchToChainPresenter() {
		activePresenter = new ChainPresenter(activeProcedure.getChainHead(), this);
		addOverviewItems();
		((ChainPresenter) activePresenter).createChainView(activeProcedure);
		procedurePane.setCenter(activePresenter.getView());
    }	

	private void addOverviewItems() {
		ChainLinkPresenter chainLinkPresenter = activeProcedure.getChainHead();
		
		int i = 0;
		
		while (chainLinkPresenter != null) {
			overviewBox.getChildren().add(new OverviewItem(chainLinkPresenter, i));
			Region region = new Region();
			region.setPrefWidth(100);
			overviewBox.getChildren().add(region);
			chainLinkPresenter = chainLinkPresenter.getNext();
			i++;
		}
	}

    private class Editor extends AnchorPane {
		
		@FXML
		private VBox editItemsBox;
		
		private ChainLinkEditPresenter editPresenter;
		
		private TranslateTransition editorTranslation;
		
		Editor(ChainLinkEditPresenter editPresenter) {
			this.editPresenter = editPresenter;
			
			try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("editor.fxml"));
	            loader.setController(this);
	            loader.setRoot(this);
	            loader.load();
	        } catch (IOException exc) {
	        	System.err.println("The file editor.fxml was not found!");
	        }
		}
		
		@FXML
		private void initialize() {
		    this.setPrefWidth(200);
		    this.setPrefHeight(overlayPane.getHeight());
		    this.setTranslateX(overlayPane.getWidth());
		    
		    editorTranslation = new TranslateTransition(Duration.millis(500), this);
		    editorTranslation.setFromX(overlayPane.getWidth() + 150);
		    editorTranslation.setToX(overlayPane.getWidth() - 150);
		    
		    editItemsBox.getChildren().set(3, editPresenter.getView());
		}
		
		@FXML
		private void handleCloseEditor() {
			hideEditor();
		}
		
		public void showEditor() {
		    editorTranslation.setRate(1);
		    editorTranslation.play();
		}
		
		public void hideEditor() {
			editorTranslation.setRate(-1);
			editorTranslation.play();
		}
	}

    /**
     * This method sets the Edit-Presenter in order to show an Edit-Window.
     * @param editPresenter : The Edit-Presenter.
     */
    private void setEditPresenter(ChainLinkEditPresenter editPresenter) {
		Editor editor = new Editor(editPresenter);
		overlayPane.getChildren().add(editor);
		editor.showEditor();
    }
}