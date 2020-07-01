package kodex.standardplugins.colorimageprocedure;


import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.Content;
import kodex.plugininterface.ProcedurePlugin;
import kodex.standardplugins.colorimageprocedure.presenter.ColorImageImportPresenter;

/**
 * 
 */
public class ColorImageProcedurePlugin extends ProcedurePlugin {

    /**
     * Default constructor
     */
    public ColorImageProcedurePlugin() {
    }

    /**
     * 
     */
    private ChainLinkPresenter[] chainLinks; // [2..*]


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
    public ColorImageImportPresenter createImportPresenter() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public ColorImageProcedureInformation createProcedureInformation() {
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