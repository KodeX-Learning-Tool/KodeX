package StandardPlugins.QR-Code;

import java.util.*;

/**
 * 
 */
public class TextQRCodeProcedurePlugin extends ProcedurePlugin {

    /**
     * Default constructor
     */
    public TextQRCodeProcedurePlugin() {
    }

    /**
     * 
     */
    private void chainLinks[2..*]; ChainLinkPresenter;



    /**
     * 
     */
    public void TextQRCodeProcedurePlugin() {
        // TODO implement here
    }

    /**
     * @return
     */
    public ChainLinkPresenter getChainHead() {
        // TODO implement here
        return null;
    }

    /**
     * @param content
     */
    public void initEncodeProcedure(Content content) {
        // TODO implement here
    }

    /**
     * @param content
     */
    public void initDecodeProcedure(Content content) {
        // TODO implement here
    }

    /**
     * @return
     */
    public void createImportPresenter() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public TextQRCodeProcedureInformation createProcedureInformation() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public abstract ChainLinkPresenter getChainHead();

    /**
     * @param content
     */
    public abstract void initEncodeProcedure(Content content);

    /**
     * @param content
     */
    public abstract void initDecodeProcedure(Content content);

    /**
     * @return
     */
    public abstract ProcedureInformation createProcedureInformation();

    /**
     * @return
     */
    public abstract ImportPresenter createImportPresenter();

}