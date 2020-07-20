package kodex.standardplugins.bwimageprocedure;

import javafx.collections.FXCollections;
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
    
    /**
     * Default Constructor of class BWImageProcedureInformation
     */
    public BWImageProcedureInformation() {
        icon = new Image(getClass().getResourceAsStream("img/mario_bw.png"));
        labels = FXCollections.observableArrayList("7", "image", "encoding & decoding");
        
        description = "Dieses Plugin zeigt den Weg von einem Bild in Schwarz/Weiß"
                + " bis zu der binären Zeichenkette";
        
        //TODO: Set image
    }

    @Override
    public String getName() {
        return "Graustufen - Bild";
    }
}