package kodex.plugininterface;

import java.util.LinkedList;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 * This abstract class specifies which information about a procedure should
 * be known and manages it.
 * Every plugin must extend this class. However, the description, the labels
 * and the icon do not have to be implemented (standard values).
 * 
 * @author Patrick Spiesberger
 * @author Raimon Gramlich
 * 
 * @version 1.0
 *
 */
public abstract class ProcedureInformation {
	
	/** The StringProperty of the procedure name. */
	protected StringProperty procedureName;
    
    /** The ListProperty tags stores all the tags which describe the procedure. */
    protected ListProperty<String> tags;
	
    /** The Image that is displayed as the icon of the procedure. */
    protected Image icon;
    
    /** The StringProperty that contains a description of the procedure. */
	protected StringProperty description;
    
    /**
     * ProcedureInformation class constructor.
     */
    public ProcedureInformation() {
        this.procedureName = new SimpleStringProperty("Debug-Procedure");
        this.tags = new SimpleListProperty<>(FXCollections.observableArrayList("Grade ?", "Procedure", "encoding & decoding"));
        this.description = new SimpleStringProperty("Missing_Detailed_Description");
    }
    
    /**
     * Returns the icon that should be used for the indexpage.
     *
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
     * Returns the detailed description of the procedure.
     *
     * @return description of the procedure
     * Note: The description is limited to a string
     */
    public String getDescription() {
        return description.get(); //Standard Beschreibung
        //TODO: Language.getMessage("noDescription"); 
    }
    
    /**
     * Returns the detailed description property.
     *
     * @return description property of the procedure
     */
    public StringProperty descriptionProperty() {
        return description; //Standard Beschreibung
        //TODO: Language.getMessage("noDescription"); 
    }

    /**
     * Gets the procedure name.
     *
     * @return the name of the procedure
     */
    public String getName() {
        return procedureName.get();
    }
    
    /**
     * Returns the Name property.
     *
     * @return the string property procedureName
     */
    public StringProperty nameProperty() {
        return procedureName;
    }
    

    /**
     * Gets the list with the tags.
     *
     * @return the ObservableList with containing the tags
     */
    public ObservableList<String> getTags() {
        return tags.getValue();
    }
    
    /**
     * Gets the ListProperty tags.
     *
     * @return the list property tags
     */
    public ListProperty<String> tagProperty() {
        return tags;
    }  
}