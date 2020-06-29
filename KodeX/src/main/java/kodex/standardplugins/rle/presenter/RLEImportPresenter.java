package StandardPlugins.RLE.Presenter;

import kodex.plugininterface.ImportPresenter;

/**
 * 
 */
public class RLEImportPresenter extends ImportPresenter {

    /**
     * Default constructor
     */
    public RLEImportPresenter() {
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