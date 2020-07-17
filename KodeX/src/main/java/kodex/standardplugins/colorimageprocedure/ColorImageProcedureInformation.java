package kodex.standardplugins.colorimageprocedure;

import java.util.List;

import javafx.scene.image.Image;
import kodex.plugininterface.ProcedureInformation;

/**
 * This class contains information about the ColorImageProcedurePlugin
 * 
 * @author Yannick Neubert
 * @version 1.0
 */
public class ColorImageProcedureInformation extends ProcedureInformation {


    /**
     * 
     */
    private Image icon;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private List<String> labels;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    public ColorImageProcedureInformation() {
        this.name = "ColorImage";
    }

    @Override
    public String getName() {
        // TODO implement here
        return "";
    }

    @Override
    public Image getIcon() {
        // TODO implement here
        return null;
    }

    @Override
    public List<String> getLabels() {
        // TODO implement here
        return null;
    }

    @Override
    public String getDescription() {
        // TODO implement here
        return "";
    }


}