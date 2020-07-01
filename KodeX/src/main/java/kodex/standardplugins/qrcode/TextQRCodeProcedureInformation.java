package kodex.standardplugins.qrcode;

import java.util.*;

import kodex.plugininterface.ProcedureInformation;
import kodex.pluginutils.model.content.AbstractImage;

/**
 * 
 */
public class TextQRCodeProcedureInformation extends ProcedureInformation {

    /**
     * Default constructor
     */
    public TextQRCodeProcedureInformation() {
    }

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private Image icon;

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
    public void TextQRCodeProcedureInformation() {
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