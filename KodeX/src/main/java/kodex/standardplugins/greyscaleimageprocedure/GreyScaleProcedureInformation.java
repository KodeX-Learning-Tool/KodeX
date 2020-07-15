package kodex.standardplugins.greyscaleimageprocedure;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import kodex.plugininterface.ProcedureInformation;
import kodex.pluginutils.model.content.AbstractImage;

/**
 * This class represents the information about the "grey-scale image
 * to binary sequence" procedure
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 * 
 */
public class GreyScaleProcedureInformation extends ProcedureInformation {

	// icon of this plugin
    private Image icon = null;

    // name of this plugin
    private String name = "Graustufen - Bild";

    // labels of this plugin
    private List<String> labels = new ArrayList<String>();

    // description of this plugin
    private String description = "Dieses Plugin zeigt den Weg von einem Bild in verschieden"
    		+ " Graustufen bis zu der binären Zeichenkette";

    /**
     * Default Constructor of class BWImageProcedureInformation
     */
    public GreyScaleProcedureInformation() {
    	labels.add("7");
    	labels.add("image");
    	labels.add("encoding & decoding");
    	//TODO: Set image
    }

    /**
     * Returns name of this plugin
     * @return plugin name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns icon of this plugin
     * @return pluginIcon
     */
    public Image getIcon() {
        return icon;
    }

    /**
     * Returns labels of this plugin
     * index 0: class if avaliable, otherweise 0
     * index 1: input
     * index 2: encoding or decoding
     * @return labels of this plugin
     */
    public List<String> getLabels() {
        return labels;
    }
    

    /**
     * Returns description of this Plugin as String
     * @return : plugin description
     */
    public String getDescription() {
        return description;
    }

}