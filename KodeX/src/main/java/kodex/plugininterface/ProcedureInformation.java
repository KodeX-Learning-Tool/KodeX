package kodex.plugininterface;

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
 * @author Leonhard Kraft
 * 
 * @version 1.0
 *
 */
public abstract class ProcedureInformation {
    
    /** The ListProperty tags stores all the labels which describe the procedure. */
    protected ObservableList<String> labels;
	
    /** The Image that is displayed as the icon of the procedure. */
    protected Image icon;
    
    /** The StringProperty that contains a description of the procedure. */
	protected String description;
    
    /**
     * Creates an instance of the ProcedureInformation class.
     */
    public ProcedureInformation() {
        this.labels = FXCollections.observableArrayList("Grade ?", "Procedure", "encoding & decoding");
        this.description = "Missing_Detailed_Description";
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
     * Returns the detailed description of the procedure.
     *
     * @return description of the procedure
     * Note: The description is limited to a string
     */
    public String getDescription() {
        return description; //Standard Beschreibung
        //TODO: Language.getMessage("noDescription"); 
    }

    /**
     * Gets the procedure name.
     *
     * @return the name of the procedure
     */
    public abstract String getName();
    

    /**
     * Gets the list with the tags.
     *
     * @return the ObservableList with containing the tags
     */
    public ObservableList<String> getLabels() {
        return labels;
    }
}