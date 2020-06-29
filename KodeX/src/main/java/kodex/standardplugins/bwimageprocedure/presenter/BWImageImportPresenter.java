package StandardPlugins.BWImageProcedure.Presenter;

import java.util.*;

/**
 * 
 */
public class BWImageImportPresenter extends ImportPresenter {

    /**
     * Default constructor
     */
    public BWImageImportPresenter() {
    }

    /**
     * 
     */
    public void BWImageImportPresenter() {
        // TODO implement here
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

}