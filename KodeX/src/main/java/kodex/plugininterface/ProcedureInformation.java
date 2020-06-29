package kodex.plugininterface;

import java.util.*;

/**
 * 
 */
public abstract class ProcedureInformation {

    /**
     * Default constructor
     */
    public ProcedureInformation() {
    }

    /**
     * 
     */
    protected Image defaultIcon;

    /**
     * @return
     */
    public abstract String getName();

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