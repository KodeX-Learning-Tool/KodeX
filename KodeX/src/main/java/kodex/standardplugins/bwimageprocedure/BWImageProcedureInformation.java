package StandardPlugins.BWImageProcedure;

import java.util.List;

import kodex.plugininterface.ProcedureInformation;
import kodex.pluginutils.model.content.AbstractImage;

/**
 * 
 */
public class BWImageProcedureInformation extends ProcedureInformation {

    /**
     * Default constructor
     */
    public BWImageProcedureInformation() {
    }

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
    public void BWImageProcedureInformation() {
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
    public AbstractImage getIcon() {
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

    /**
     * @return
     */
    public abstract String getName();

}