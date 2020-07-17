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

   @Override
    public ChainLinkPresenter getChainHead() {
        // TODO implement here
        return null;
    }

    @Override
    public void initEncodeProcedure(Content content) {
        // TODO implement here
    }

    @Override
    public void initDecodeProcedure(Content content) {
        // TODO implement here
    }

    @Override
    public ColorImageImportPresenter createImportPresenter() {
        // TODO implement here
        return null;
    }

   @Override
    public ColorImageProcedureInformation createProcedureInformation() {
        return new ColorImageProcedureInformation();
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