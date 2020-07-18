package kodex.plugininterface;

import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import kodex.pluginutils.model.content.AbstractImage;

/**
 * This abstract class specifies which information about a procedure should
 * be known and manages it.
 * Every plugin must extend this class. However, the description, the labels
 * and the icon do not have to be implemented (standard values).
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 *
 */
public abstract class ProcedureInformation {
	
	protected StringProperty procedureName;
    protected StringProperty shortDescription;
    protected StringProperty tags;
	
    

    /* 
     * Image that is displayed for the procedure
     */
    protected Image icon;
	protected String description;
    
    /**
     * ProcedureInformation class constructor
     */
    public ProcedureInformation() {
        this.procedureName = new SimpleStringProperty("Debug-Verfahren");
        this.shortDescription = new SimpleStringProperty("Das Kodierungsverfahren zum Debuggen.");
        this.tags = new SimpleStringProperty("Klasse ?, Verfahren");
    }
    
    /**
     * Returns the icon that should be used for the indexpage
     * @return icon for indexpage
     */
    public Image getIcon() {
    	return icon;
    }

    /**
     * Returns a list of the process labels. At the top of the list
     * must be the class level, from which class level the procedure is suitable.
	 * If no class level applies, please enter 0.
     * @return List of labels
     */
    public List<String> getLabels() {
        List<String> labels = new LinkedList<>();
        return labels;
    }

    /**
     * Returns a brief description of the procedure
     * @return description of procedure
     * Note: The description is limited to a string
     */
    public String getDescription() {
        return description; //Standard Beschreibung
        //TODO: Language.getMessage("noDescription"); 
    }
    
    public String getShortDescription() {
        return shortDescription.get();
    }
    
    public StringProperty shortDescriptionProperty() {
        return shortDescription;
    }

    /**
     * Returns name of procedure
     * @return name of procedure
     */
    public String getName() {
        return procedureName.get();
    }
    
    public StringProperty nameProperty() {
        return procedureName;
    }
    
    /**
     * @return
     */
    public String getTags() {
        return tags.get();
    }
    
    public StringProperty tagProperty() {
        return tags;
    }  
}