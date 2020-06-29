package StandardPlugins.QR-Code.Presenter;

import java.util.*;

/**
 * 
 */
public class QRCodeImportPresenter extends ImportPresenter {

    /**
     * Default constructor
     */
    public QRCodeImportPresenter() {
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