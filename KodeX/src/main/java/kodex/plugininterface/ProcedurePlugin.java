package kodex.plugininterface;

import java.util.*;

/**
 * 
 */
public abstract class ProcedurePlugin extends Pluginable {

    /**
     * Default constructor
     */
    public ProcedurePlugin() {
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