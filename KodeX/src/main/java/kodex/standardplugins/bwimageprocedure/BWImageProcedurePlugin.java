package kodex.standardplugins.bwimageprocedure;

import java.util.*;

import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.Content;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedureInformation;
import kodex.plugininterface.ProcedurePlugin;
import kodex.pluginutils.model.content.BlackWhiteImage;
import kodex.pluginutils.model.content.RGBMatrix;
import kodex.pluginutils.model.steps.ColorImageToRGBMatrix;
import kodex.standardplugins.bwimageprocedure.presenter.BWImageImportPresenter;

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
    	
    }

    @Override
	public String getPluginName() {
		return "Black & White Image";
	}

	@Override
	public String getPluginDescription() {
		return "Verfahren";
	}

	@Override
	public ChainLinkPresenter getChainHead() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initEncodeProcedure(Content content) {
	}

	@Override
	public void initDecodeProcedure(Content content) {
		
	}

	@Override
	public ProcedureInformation createProcedureInformation() {
		return new BWImageProcedureInformation();
	}

	@Override
	public ImportPresenter createImportPresenter() {
		return new BWImageImportPresenter(this);
	}
}