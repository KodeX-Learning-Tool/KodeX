package kodex.presenter;

import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import kodex.model.Filter;
import kodex.model.PluginLoader;
import kodex.plugininterface.ProcedureInformation;
import kodex.plugininterface.ProcedurePlugin;

/**
 * Dieser Presenter ist für die Startseite zuständig. Die View der Startseite zeigt 
 * die verfügbaren Kodierungsverfahren an, wobei diese Auswahl durch Filter und Sucheingabe 
 * angepasst werden kann. Wird ein Verfahren ausgewählt so wird zum ProcedureLayoutPresenter gewechselt.
 * 
 * @author Yannick Neubert, Raimon Gramlich
 * 
 * @version 1.0
 */
public class IndexPagePresenter extends Presenter {

    /** The List of procedures which will be displayed on the index page.*/
    private List<ProcedurePlugin> shownProcedures;
    
    /** A TextField which allows the user to input search terms. */
	@FXML
	private TextField searchTextField;
	
	/** A ComboBox for choosing categories for sorting or filtering the diplayed procedures. */
	@FXML
	private ComboBox<String> filterComboBox;
	
	/** ProcedureButtons, to be shown on the index page, will be dynamically added to this TilePane. */
	@FXML
	private TilePane procedureButtonPane;
	
	/** Allows the creation of other presenter to which the IndexPagePresenter can switch to. */
	private PresenterFactory presenterFactory;

    /**
     * Creates a new IndexPagePresenter with references to a PresenterManger 
     * and a PresenterFactory.
     * @param pm : The reference to the PresenterManger.
     * @param pf : The reference to the PresenterFactory.
     */
    public IndexPagePresenter(PresenterManager pm, PresenterFactory pf) {
    	this.presenterManager = pm;
    	this.presenterFactory = pf;
    	
    	PluginLoader pluginLoader = PluginLoader.getInstance();
    	
    	shownProcedures = pluginLoader.getEnabledProcedurePlugins();
    }
    
    /**
     * Initializes the view-object created by the FXMLLoader.
     * 
     * @throws IOException is thrown when the fxml file couldn't get loaded properly.
     */
	@FXML
	private void initialize() throws IOException {
		
		// adds the ProcedureButtons for each enabled procedure plugin
		for (ProcedurePlugin procedure: shownProcedures) {
			procedureButtonPane.getChildren().add(new ProcedureButton(procedure));
		}	
	}
    
	/**
	 * This class represents a single procedure button. It loads a template via fxml
	 * file and fills it with the information it has gotten from an instance of the 
	 * ProcedureInformation class.
	 * 
	 * @author Raimon Gramlich
	 * 
	 * @version 1.0
	 */
	private class ProcedureButton extends VBox {
			
			@FXML
			private Label procedureLabel;
			
			@FXML
			private ImageView procedureIcon;
			
			
			private ProcedurePlugin procedure;
			private ProcedureInformation procedureInformation;
			
			ProcedureButton(ProcedurePlugin procedurePlugin) throws IOException {
				
				this.procedure = procedurePlugin;
				
				this.procedureInformation = procedurePlugin.createProcedureInformation();
				
				try {
		            FXMLLoader loader = new FXMLLoader(getClass().getResource("procedurebutton.fxml"));
		            loader.setController(this);
		            loader.setRoot(this);
		            loader.load();
		        } catch (IOException exc) {
		            throw new IOException(exc);
		        }
				
				
				procedureIcon.managedProperty().bind(procedureIcon.visibleProperty());
				
				procedureLabel.setText(procedureInformation.getName());		
				procedureIcon.setImage(procedureInformation.getIcon());
			}
			
		    /**
		     * Die Methode wird ausgeführt, wenn der Benutzer auf ein Kodierungsverfahren klickt. 
		     * Wechselt den Presenter zum ProcedureLayoutPresenter, übergibt diesem das gewählte ProcedurePlugin
		     * und benachrichtigt den PresenterManger.
		     */
			@FXML
			private void handleProcedureSelected() {
				presenterManager.updatePresenter(presenterFactory.createProcedureLayoutPresenter(procedure));
			}
	 }

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer die Suche nach Kodierungsverfahren bestätigt. 
     * Sie sorgt dafür das die zutreffenden Kodierungsverfahren ausgewählt werden.
     */
    public void handleSearch() {
        // TODO implement here
    }

    /**
     * Die Methode wird ausgeführt, wenn der Benutzer die Kodierungsverfahren filtert. 
     * Sie sorgt dafür das die zutreffenden Kodierungsverfahren ausgewählt werden.
     */
    public void handleFilter() {
        // TODO implement here
    }

    /**
     * Durchsucht die Liste der Kodierungsverfahren
     * @param searchTerm : Das Suchwort
     */
    private void searchProcedures(String searchTerm) {
        // TODO implement here
    }

    /**
     * Filtert die Liste der kodierungsverfahren
     * @param filter : Der ausgewählte Filter
     */
    private void filterProcedures(Filter filter) {
        // TODO implement here
    }

    /**
     * Erstellt die Schaltflächen für die Kodierungsverfahren
     */
    private void createProcedureButtons() {
        // TODO implement here
    }

    /**
     * Fügt die Schaltflächen für die Kodierungsverfahren in die View ein
     * @param procedure : Eine konkrete Schaltfläche
     */
    private void addProcedureButton(Button procedure) {
        // TODO implement here
    }

	@Override
	public AnchorPane getView() {
		// TODO Auto-generated method stub
		return null;
	}

}