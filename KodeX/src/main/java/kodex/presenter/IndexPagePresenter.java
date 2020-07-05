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
 * This presenter is responsible for the index page. The view displays the all available coding procedures,
 * which can be adjusted by the search or filter/sort function. If a procedure is selected,
 * the ProcedureLayoutPresenter will display its view next.
 * 
 * @author Yannick Neubert, Raimon Gramlich
 * 
 * @version 1.0
 */
public class IndexPagePresenter extends Presenter {

    /** The List of procedures which will be displayed on the index page.*/
    private List<ProcedurePlugin> selectedProcedures;
    
    /** A TextField which allows the user to input search terms. */
	@FXML
	private TextField searchTextField;
	
	/** A ComboBox for choosing categories for sorting or filtering the diplayed procedures. */
	@FXML
	private ComboBox<Filter> filterComboBox;
	
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
    	
    	selectedProcedures = pluginLoader.getEnabledProcedurePlugins();
    }
    
    /**
     * Initializes the view-object created by the FXMLLoader.
     * 
     * @throws IOException is thrown when the fxml file couldn't get loaded properly.
     */
	@FXML
	private void initialize() throws IOException {
		
		// adds the ProcedureButtons for each enabled procedure plugin
		for (ProcedurePlugin procedure: selectedProcedures) {
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
			
			/** The Label for displaying the procedure name.*/
			@FXML
			private Label procedureLabel;
			
			/** The ImageView for displaying the procedure icon.*/
			@FXML
			private ImageView procedureIcon;
			
			/** The procedure which this button represents.*/
			private ProcedurePlugin procedure;
			
			/** The information regarding the procedure.*/
			private ProcedureInformation procedureInformation;
			
		    /**
		     * Creates a new ProcedureButton with a reference to a ProcedurePlugin.
		     * 
		     * @param procedurePlugin : The reference to the ProcedurePlugin.
		     */
			ProcedureButton(ProcedurePlugin procedurePlugin) throws IOException {
				
				this.procedure = procedurePlugin;
				this.procedureInformation = procedurePlugin.createProcedureInformation();
				
				// loads the template
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
		     * Gets called when the user clicks on a ProcedureButton. Switches the active presenter and 
		     * gives the reference of the procedure it represents to the PresenterManager. 
		     */
			@FXML
			private void handleProcedureSelected() {
				presenterManager.updatePresenter(presenterFactory.createProcedureLayoutPresenter(procedure));
			}
	 }

    /**
     * Gets called when the user writes something in the search text field. 
     */
	@FXML
	private void handleSearch() {
        searchProcedures(searchTextField.getText());
    }

    /**
     * Gets called when the user selects an item in the combo box. 
     */
	@FXML
	private void handleFilterSelected() {
        filterProcedures(filterComboBox.getSelectionModel().getSelectedItem());
    }

    /**
     * Searches in the list of enabled procedure plugins for a procedure name
     * which matches the given term.
     * 
     * @param searchTerm : The search term
     */
    private void searchProcedures(String searchTerm) {
        // TODO implement here
    }

    /**
     * Filters or sorts the list of enabled procedure plugins
     * according to the selected filter.
     * @param filter : The selected filter.
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