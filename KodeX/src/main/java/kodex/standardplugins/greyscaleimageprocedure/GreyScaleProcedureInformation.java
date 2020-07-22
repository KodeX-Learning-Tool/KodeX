package kodex.standardplugins.greyscaleimageprocedure;

import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import kodex.plugininterface.ProcedureInformation;

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

    /**
     * Default Constructor of class BWImageProcedureInformation
     */
    public GreyScaleProcedureInformation() {
    	icon = new Image(getClass().getResourceAsStream("img/mario_grey.png"));
    	
        labels = FXCollections.observableArrayList("7", "image", "encoding & decoding");
    	
    	description = "Dieses Plugin zeigt den Weg von einem Bild in verschieden"
                + " Graustufen bis zu der binären Zeichenkette";
    	
    	//TODO: Set image
    }

    @Override
    public String getName() {
        return "Graustufen - Bild";
    }
}