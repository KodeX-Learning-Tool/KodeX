package StandardPlugins.GreyScaleImageProcedure.Presenter;

import kodex.plugininterface.ImportPresenter;

/**
 * 
 */
public class GreyScaleImageImportPresenter extends ImportPresenter {

    /**
     * Default constructor
     */
    public GreyScaleImageImportPresenter() {
    }

    /**
     * 
     */
    public void GreyScaleImageImportPresenter() {
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