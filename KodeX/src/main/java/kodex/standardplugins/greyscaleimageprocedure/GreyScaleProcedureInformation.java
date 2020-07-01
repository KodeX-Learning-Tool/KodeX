package kodex.standardplugins.greyscaleimageprocedure;

import java.util.List;

import javafx.scene.image.Image;
import kodex.plugininterface.ProcedureInformation;
import kodex.pluginutils.model.content.AbstractImage;

/**
 * 
 */
public class GreyScaleProcedureInformation extends ProcedureInformation {

    /**
     * Default constructor
     */
    public GreyScaleProcedureInformation() {
    }

    /**
     * 
     */
    private AbstractImage icon;

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
    public void GreyScaleImageProcedureInfromation() {
        // TODO implement here
    }

    /**
     * @return
     */
    public String getName() {
        // TODO implement here
        return "";
    }

    /**
     * @return
     */
    public Image getIcon() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public List<String> getLabels() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public String getDescription() {
        // TODO implement here
        return "";
    }

}