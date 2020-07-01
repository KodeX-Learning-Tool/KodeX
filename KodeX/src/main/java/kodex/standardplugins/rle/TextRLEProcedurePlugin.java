package kodex.standardplugins.rle;

import java.util.*;

import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.Content;
import kodex.plugininterface.ProcedurePlugin;

/**
 * 
 */
public class TextRLEProcedurePlugin extends ProcedurePlugin {

    /**
     * Default constructor
     */
    public TextRLEProcedurePlugin() {
    }

    /**
     * 
     */
    private ChainLinkPresenter[] chainLinks; //[2..*]
    

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
    }

    /**
     * @return
     */
    public TextRLEProcedureInformation createProcedureInformation() {
        // TODO implement here
        return null;
    }

	@Override
	public String getPluginName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPluginDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}