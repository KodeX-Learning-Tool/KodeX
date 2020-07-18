package kodex.standardplugins.colorimageprocedure;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import kodex.plugininterface.ProcedureInformation;

/**
 * 
 */
public class ColorImageProcedureInformation extends ProcedureInformation {
    /**
     * 
     */
    public ColorImageProcedureInformation() {
        this.name = new SimpleStringProperty("Farbbildverfahren");
        this.icon = new Image(getClass().getResourceAsStream("img/mario_color.png"));
        this.description = "Das Kodierungsverfahren Bild-zu-Bitfolge.";
        this.shortDescription = new SimpleStringProperty("Es wird zunächst das Bild in eine Matrix umgewandelt." +
            	" Daraufhin wandelt man diese in eine Liste um. Am Ende erhält man eine Binärfolge.");
        this.tags = new SimpleStringProperty("Klasse 7, Verfahren");
    }
}