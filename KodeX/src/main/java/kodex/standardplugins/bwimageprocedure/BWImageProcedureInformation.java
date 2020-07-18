package kodex.standardplugins.bwimageprocedure;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import kodex.plugininterface.ProcedureInformation;

/**
 * This class represents the information about the "black and white image
 * to binary sequence" procedure
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 * 
 */
public class BWImageProcedureInformation extends ProcedureInformation {

    // icon of this plugin
    private Image icon;

    // name of this plugin
    private String name = "Schwarz/Weiß - Bild";

    // labels of this plugin
    private List<String> labels = new ArrayList<String>();

    // description of this plugin
    private String description = "Dieses Plugin zeigt den Weg von einem Bild in Schwarz/Weiß"
    		+ " bis zu der binären Zeichenkette";

    /**
     * Default Constructor of class BWImageProcedureInformation
     */
    public BWImageProcedureInformation() {
    	labels.add("7");
    	labels.add("image");
    	labels.add("encoding & decoding");
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