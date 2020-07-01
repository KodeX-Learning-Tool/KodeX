package kodex.standardplugins.greyscaleimageprocedure;


import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.Content;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedureInformation;
import kodex.plugininterface.ProcedurePlugin;

/**
 * 
 */
public class GreyScaleImageProcedurePlugin extends ProcedurePlugin {

    /**
     * Default constructor
     */
    private GreyScaleImageProcedurePlugin() {
    }

    /**
     * 
     */
    private ChainLinkPresenter[] chainLinks; // [2..*]

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
	public ChainLinkPresenter getChainHead() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initEncodeProcedure(Content content) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initDecodeProcedure(Content content) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProcedureInformation createProcedureInformation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImportPresenter createImportPresenter() {
		// TODO Auto-generated method stub
		return null;
	}


}