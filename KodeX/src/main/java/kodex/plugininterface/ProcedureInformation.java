package kodex.plugininterface;

import java.util.LinkedList;
import java.util.List;

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

    /**
     * ProcedureInformation class constructor
     */
    public ProcedureInformation() {}
    

    /* 
     * Image that is displayed for the procedure
     */
    protected Image defaultIcon;

    /**
     * Returns name of procedure
     * @return name of procedure
     */
    public abstract String getName();

    /**
     * Returns the icon that should be used for the indexpage
     * @return icon for indexpage
     */
    public Image getIcon() {
    	if (defaultIcon != null) {
    		return defaultIcon; 
    	}
    	else {
    		return null; 
    		//TODO: Stanard-Bild zurück geben
    	}
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
        return ""; //Standard Beschreibung
        //TODO: Language.getMessage("noDescription"); 
    }

}