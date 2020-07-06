package kodex.standardplugins.bwimageprocedure;

import java.util.*;

import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.Content;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedurePlugin;

/**
 * 
 */
public class BWImageProcedurePlugin extends ProcedurePlugin {

    /**
     * 
     */
    private ChainLinkPresenter[] chainLinks; // [2..*]

    /**
     * 
     */
    public  BWImageProcedurePlugin() {
        // TODO implement here
    }

    /**
     * @param content
     */
    public void initEncodeProcedure(Content content) {
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
    public void initDecodeProcedure(Content content) {
        // TODO implement here
    }

    /**
     * @return
     */
    public BWImageProcedureInformation createProcedureInformation() {
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

	@Override
	public ImportPresenter createImportPresenter() {
		// TODO Auto-generated method stub
		return null;
	}

}