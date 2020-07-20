package kodex.standardplugins.rle;

import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import kodex.plugininterface.ProcedureInformation;

/**
 * This class contains information about the run length encoding (rle) procedure plugin.
 * 
 * @author Leonhard Kraft
 * @version 1.0
 */
public class TextRLEProcedureInformation extends ProcedureInformation {

    /**
     * Creates a new instance of the rle class and sets the fields accordingly.
     */
    public TextRLEProcedureInformation() {
        this.icon = new Image(getClass().getResourceAsStream("img/hello_world.png"));
        this.description = "In diesem Verfahren kann ein Text mit der Lauflängencodierung komprimiert oder dekomprimiert werden.";
        this.tags = FXCollections.observableArrayList("Klasse 7", "Komprimierungsverfahren", "Kodieren und Dekodieren");
    }

    @Override
    public String getName() {
        return "Lauflängencodierung";
    }
}