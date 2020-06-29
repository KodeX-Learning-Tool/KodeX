package StandardPlugins.GreyScaleImageProcedure;

import java.util.*;

/**
 * 
 */
private class GreyScaleImageProcedurePlugin extends ProcedurePlugin {

    /**
     * Default constructor
     */
    private GreyScaleImageProcedurePlugin() {
    }

    /**
     * 
     */
    private void chainLinks[2..*]; ChainLinkPresenter;



    /**
     * 
     */
    public void GreyScaleImageProcedurePlugin() {
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
    public GreyScaleImportPresenter createImportPresenter() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public GreyScaleProcedureInformation createProcedureInformation() {
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