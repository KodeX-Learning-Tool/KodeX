package kodex.plugininterface;

import java.util.*;

/**
 * 
 */
public abstract class ImportPresenter implements IPresenter {

    /**
     * Default constructor
     */
    public ImportPresenter() {
    }



    /**
     * @return
     */
    public abstract boolean validateEncodeImport();

    /**
     * @return
     */
    public abstract boolean validateDecodeImport();

    /**
     * 
     */
    public abstract void handleEncodeImport();

    /**
     * 
     */
    public abstract void handleDecodeImport();

    /**
     * @param plugin
     */
    public void ImportPresenter(ProcedurePlugin plugin) {
        // TODO implement here
    }

    /**
     * @param plp
     */
    public void setLayoutPresenter(ProcedureLayoutPresenter plp) {
        // TODO implement here
    }

    /**
     * @return
     */
    public AnchorPane getView() {
        // TODO implement here
        return null;
    }

}